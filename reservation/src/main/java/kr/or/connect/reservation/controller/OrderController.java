package kr.or.connect.reservation.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.connect.reservation.dao.MemberDao;
import kr.or.connect.reservation.dao.OrderDAO;
import kr.or.connect.reservation.dto.OrderDataDTO;
import kr.or.connect.reservation.dto.OrderInfoDTO;
import kr.or.connect.reservation.security.encoder.CustomNoOpPasswordEncoder;
import kr.or.connect.reservation.service.OrderStep;
import kr.or.connect.reservation.service.ReservationMainService;

@Controller
public class OrderController {
	@Autowired
	ReservationMainService mainService;
	@Autowired
	MemberDao memberDao;
	@Autowired
	OrderDAO dao;
	private CustomNoOpPasswordEncoder encoder = new CustomNoOpPasswordEncoder();
	
	@GetMapping(path = "/order/transactionSuccess")
	public void transactionSuccess(HttpSession tempSession, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		/*
		 * 새로 고침 시에결제 승인 api를 재승인 요청을 해서 오류 메시지가 나올수 있은니
		 * 처리된 후에는 sendRedirect
		 */
		System.out.println("여기로 석세스");
		try {
			processSuccess(req);//처리
			resp.sendRedirect("/ ");//여기서부터하면됨.
			//이제 결제정보는 리턴받았으니 데이터베이스에서 해당 구매상품의 결제가 완료되었음을 나타낼수있는 필드 업데이트
			//그 필드의값이 결제완료인 경우엔 결제하기 버튼 대신 결제완료라는 표시만 해주기
			String str = req.getParameter("orderId");
			mainService.setTransactionSuccess(str.substring(0, str.length() - 36));
			//해당상품의 시작시간에 도달했음에도 결제완료를 하지않은경우엔 주문취소랑 똑같이 보이도록하기
			//기존엔 취소를 누른경우 그 상품만 안보이게 했으나 취소를 누른경우 + 취소는 안눌렀지만 결제기간이 지났는데 결제안한 경우로 두가지 WHERE절을 유지해야할것
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void processSuccess(HttpServletRequest req) throws Exception {
		//결제가 정상적으로 끝난 경우 호출
		//결제 시도 페이지의 paymentData 객체의 successUrl 속성으로 접근
		//접근 시에 orderId, paymentKey, amount파라미터 만으로 접근(주의 : 접근 url에는 성공여부들의 정보는 포함되어 있지 않음)
		
		//1.파라미터 정리
		//paymentKey : 결제의 키값
		//orderId : 주문id입니다, 결제창을 열 때 requestPayment()에 담아 보낸 값
		//amount:실제로 결제된 금액
		String orderId = req.getParameter("orderId");
		System.out.println("orderId:"+orderId);
		String paymentKey = req.getParameter("paymentKey");
		System.out.println("paymentKey:"+paymentKey);
		String amount = req.getParameter("amount");
		System.out.println("amount:"+amount);
	
		//2.토스에서 미리 받은 상점의 secretKey를 사용, 토스쪽에서는 해당 값으로 상점을 구분
	    String secretKey = "";
	  
	    //secretKey를 인코딩
	    Base64.Encoder encoder = Base64.getEncoder(); 
	    byte[] encodedBytes = encoder.encode(secretKey.getBytes("UTF-8"));
	    String authorizations = "Basic "+ new String(encodedBytes, 0, encodedBytes.length);
	    
	    //3.토스 결제 승인 api호출
	    //rest api 방식으로 처리
	    
	    //접근 url 에 paymentKey 포함
	    URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey);
	  
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    //secret키를 포함
	    connection.setRequestProperty("Authorization", authorizations);
	    connection.setRequestProperty("Content-Type", "application/json");
	    connection.setRequestMethod("POST");
	    connection.setDoOutput(true);
	
	    JSONObject obj = new JSONObject();
	    obj.put("orderId", orderId);
	    obj.put("amount", amount);
	  
	    OutputStream outputStream = connection.getOutputStream();
	    outputStream.write(obj.toString().getBytes("UTF-8"));
	    //호출 후 결과코드 가져오기
	    int code = connection.getResponseCode();
	    
	    //4.결과 코드로 이후 결과 처리
	    //code가 200번대이면 성공으로 처리
	    boolean isSuccess = code >= 200 && code < 300 ? true : false;
	    System.out.println("isSuccess : "+isSuccess);
	    
	    InputStream responseStream = isSuccess? connection.getInputStream(): connection.getErrorStream();
	    Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
	    JSONParser parser = new JSONParser();
	    //response 값을 jsonObject로 저장
	    JSONObject jsonObject = (JSONObject) parser.parse(reader);
	    System.out.println("결과 데이터:"+jsonObject.toJSONString());
	    responseStream.close();
	    
	    if(isSuccess) {//성공시 처리
	    	System.out.println("주문번호 orderId:"+jsonObject.get("orderId"));
	    	System.out.println("결제방법 method:"+jsonObject.get("method"));
	    	System.out.println("결제승인일시 approvedAt:"+jsonObject.get("approvedAt"));
	    	//각각의 결제방법에 따라 처리, 보통은 가상계좌와 기타 결제 수단으로 나누어짐
	    	String method = (String)jsonObject.get("method");
	    	
	    	OrderInfoDTO dto = new OrderInfoDTO();
	    	dto.setOrderNo((String)jsonObject.get("orderId"));//주문번호
	    	dto.setPayMethod(method);
	    	
	    	if(method.equals("가상계좌")) {
	    		dto.setOrderStep(String.valueOf(OrderStep.orderReceive));
	    	}else {
	    		dto.setOrderStep(String.valueOf(OrderStep.payReceive));
	    		dto.setDatePay((String)jsonObject.get("approvedAt"));//입금일시
	    	}
	    	processSuccessUpdate(dto);
	    }
	    
	    //추가개발사항
	    //결제금액 0일경우 경고창 출력후 장바구니로
	    //동일한 주문번호로 실패하지않은 주문이 있는경우 경고창출력후 장바구니로
	    //결제 성공후 리퀘스트로 전달한 금액과 결제되어야하라 금액을 비교해서 다른경우 승인 api작업을 하지않음>>주문실패처리
	    //결제 완료 후 주문번호 바꾸기(추가주문을 위하여)>>장바구니 번호체계를 세션아이디 말고 딴거로 해야함
	    //결제 완료 후에 데이터베이스에 paymentkey저장>>주문 취소시 사용,가상계좌의 경우 은행 계좌번호 입금자명 저장>>문자메시지 및 마이페이지에 출력
	    //결제 대행사 마다 규격이 다르기 때문에 order_info에 저장하지않고 따로 toss관련 테이블 생성해서 작업
	}
	
	private boolean processSuccessUpdate(OrderInfoDTO dto) {
		//dto기준으로 주문정보 업데이트 실행
		//return dao.updateOrderInfoWhenProcessSuccess(dto);
		return true;
	}
}
