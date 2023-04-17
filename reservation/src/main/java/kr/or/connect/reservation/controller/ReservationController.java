package kr.or.connect.reservation.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoResponse;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoResponse;
import kr.or.connect.reservation.dto.ReservationParam;
import kr.or.connect.reservation.dto.ReservationResponse;
import kr.or.connect.reservation.service.DetailService;
import kr.or.connect.reservation.service.ReservationService;

@Controller
public class ReservationController {

	@Autowired
	ReservationService reservationService;
	
	@Autowired
	DetailService detailService;
	
	@PutMapping(value = "/cancleReservation/{reservationInfoId}") //PutMapping 어노테이션의 {reservationInfoId}값을 해당 매서드의 PathVariable 어노테이션으로 받는다
	@ResponseBody
	public ReservationInfo cancleReservation(@PathVariable(name = "reservationInfoId") Integer reservationInfoId) { //PathVariable 어노테이션으로 reservationInfoId를 받아서 인자로 삼는다. 해당하는 id의 예약을 취소하는 매서드
		System.out.println("예약 : "+reservationInfoId); //취소할 id 콘솔출력
		ReservationInfo reservationInfo = reservationService.cancelReservation(reservationInfoId); //예약정보 객체에 reservationInfoId를 인자로 취소한것으로 처리된 예약정보 데이터를 집어넣는다
		System.out.println("취소 여부 : " + reservationInfo.isCancelYn()); //취소여부 콘솔출력
		System.out.println("예약 아이디 : " + reservationInfo.getReservationInfoId()); //예약아이디 콘솔출력
		System.out.println("예약 이메일 : " + reservationInfo.getReservationEmail()); //예약이메일 콘솔출력
		return reservationInfo; //처리 완료 후 reservationInfo 객체 반환
	}
	
	@PostMapping(path="/checkReservation") //클라이언트가 입력한 이메일로된 예약이 있는지 데이터베이서에서 확인하는 매서드
	@ResponseBody
	public String checkReservation(@RequestBody String reservationEmail) { //@RequestBody : http요청의 body 부분을 java 객체로 받을 수 있게 해주는 어노테이션. 주로 json을 받을 때 활용한다.
		ReservationInfoResponse reservationInfoResponse = reservationService.getReservationInfo(reservationEmail); //예약정보 체크를 위해 예약자 이메일을 얻어 새로만든 reservationResponse객체에 담는다
		Map<String, Object> map = new HashMap<>(); //이 후 데이터 맵핑을 위한 해쉬맵생성
		
		if(reservationInfoResponse.getSize() == 0) { //reservationInfoResponse 객체의 사이즈가 0인경우, 즉 위에서 reservationEmail을 못집어넣어서 객체가 비어있는경우
			map.put("size", 0); //해쉬맵 객체에 size 키값으로 0 밸류를 삽입한다
			return "0"; //문자열 0 반환
		}else {
			map.put("size", reservationInfoResponse.getSize()); //해쉬맵 객체에 size 키값으로 reservationInfoResponse의 사이즈를 추출하여 밸류값으로 삽입한다
			map.put("reservations",reservationInfoResponse.getReservations()); //해쉬맵 객체에 reservations 키값으로 현재 이메일에 맞는 예약정보 리스트를 벨류값으로 삽입한다
		}
		
		return "1"; //문자열 1 반환
	}
	
	@PostMapping(path="/getReservationForTransaction",produces="application/json; charset=utf-8") //reservationId로 예약이 있는지 데이터베이서에서 확인하는 매서드
	@ResponseBody
	public String getReservationForTransaction(@RequestParam(value = "reserveId", required = true) String reserveId) { 
		System.out.println("transacajaxxxxx");
		ReservationInfo reservationInfo = reservationService.getReservationInfo(Integer.parseInt(reserveId));
		Product product = reservationService.getProductByDisplayInfoId(reservationInfo.getDisplayInfoId());
		Map<String, Object> map = new HashMap<>(); //이 후 데이터 맵핑을 위한 해쉬맵생성
		
		if(reservationInfo == null) { //reservationInfoResponse 객체의 사이즈가 0인경우, 즉 위에서 reservationEmail을 못집어넣어서 객체가 비어있는경우
			map.put("size", 0); //해쉬맵 객체에 size 키값으로 0 밸류를 삽입한다
			System.out.println("null");
			return "null"; //문자열 null 반환
		}else {
			map.put("amount", reservationInfo.getTotalPrice());//총금액
			map.put("orderId",reservationInfo.getReservationInfoId()+UUID.randomUUID().toString()); //reserveId
			map.put("orderName",product.getDescription()); //상품이름
			map.put("customerName",reservationInfo.getReservationName()); //상품 산사람의 별명
			Gson gson = new Gson();
			Type gsonType = new TypeToken<HashMap>(){}.getType();
			String gsonString = gson.toJson(map,gsonType);
			System.out.println(gsonString);
			return gsonString;
		}
	}
	
	@GetMapping("/myreservation") // /myreservation 경로일때의 컨트롤러
	public String myreservePage(@RequestParam String reservationId, ModelMap model, HttpSession session) { //RequestParam 어노테이션으로 reservationEmail값을 요청한다, 모델맵사용, 세션생성을 위한 HttpSession 매개변수
		
		ReservationInfoResponse reservationInfoResponse = reservationService.getReservationInfo(reservationId); //서비스클래스를 이용하여 이메일에 따른 reservationResponse 객체를 통째로 받아온다
		List<ReservationInfo> reservations = new ArrayList<>(); //ReservationInfo 리스트 객체 reservations을 ArrayList로 생성 
		List<ReservationInfo> cancelReservations = new ArrayList<>(); //ReservationInfo 리스트 객체 cancelReservations을 ArrayList로 생성 (삭제된 예약들을 view에 랜더링하기위함)
		List<ReservationInfo> completeReservations = new ArrayList<>(); //ReservationInfo 리스트 객체 completeReservations을 ArrayList로 생성 (아마도 완료된 예약???)
		Map<String, Object> map = new HashMap<>(); //해쉬맵객체 생성
		Date currentDate = new Date(); //현재시간을 currentDate 변수에 저장시켜놓기
		for(int i = 0; i < reservationInfoResponse.getReservations().size(); i++) { //reservationInfoResponse에 대한 iteration
			Date getDate = reservationInfoResponse.getReservations().get(i).getReservationDate(); //현재 인덱스의 ReservationDate를 추출하여 getDate 변수에 임시저장
			int result = currentDate.compareTo(getDate); //현재시간(currentDate)를 현재 인덱스의 예약시산(getDate) 와 비교한 값을 result 변수에 임시저장
			if((reservationInfoResponse.getReservations().get(i).isCancelYn() == true)
					|| ((reservationInfoResponse.getReservations().get(i).getReservationDate().before(currentDate)&&(reservationInfoResponse.getReservations().get(i).getTransactionFlag()==0)))
				) { //만약 현재인덱스의 예약정보(Reservations().get(i))의 취소여부(isCancelYn)가 true 인 경우
				cancelReservations.add(reservationInfoResponse.getReservations().get(i)); //cancelReservation 리스트에 현재 인덱스의 예약정보를 add 한다. 즉 현재 예약정보를 "취소된 예약" 상태로 처리한다
			}else { //true가 아닌 그 외에(즉 false인 경우)
				if(result < 0) { //만약 result가 0보다 작은경우, 즉 아직 예약한 기간이 오기전인경우
					reservations.add(reservationInfoResponse.getReservations().get(i)); //reservation리스트에 현재 인덱스의 예약정보를 add한다. 즉 현재 예약정보를 "예약됨"상태로 처리한다
				}else { //result값이 0보다 작지않은경우, 즉 같거나 큰경우
					completeReservations.add(reservationInfoResponse.getReservations().get(i)); //completeReservation리스트에 현재 인덱스의 예약정보를 add한다. 즉 현재 예약정보를 "만료된 예약"상태로 처리한다
				} 
			}
		}
		
		//위에서 처리가 다 끝난 데이터들을 이제 반화용 맵객체에 넣고 이메일 유지를 위해 세션에 이메일값을 넣는다
		map.put("size", reservationInfoResponse.getSize()); //해쉬맵에 reservationInfoResponse 사이즈값 맵핑
		map.put("reservations", reservations); //예약정보들 맵핑
		map.put("completeReservations", completeReservations); //만료된 예약정보들 맵핑
		map.put("cancelReservations", cancelReservations); //취소된 예약정보들 맵핑
		map.put("reservationId", reservationId); //이메일 맵핑
		//map.put("transactionFlag", reservationService.getTransactionFlag(reservationId));
		model.addAllAttributes(map); //모델맵에 모든 해쉬맵 요소들을 전부 맵핑
		return "myreservation"; //myreservation 응답 반환
	}

	@GetMapping("/reservePage") // /myreservation 경로일때의 컨트롤러
	public String reservePage(@RequestParam(name="id", required=true) Integer displayInfoId, ModelMap model) {  //RequestParam 어노테이션으로 displayInfoId값을 요청한다, 모델맵사용
		Map<String, Object> map = new HashMap<>(); // 해쉬맵 생성
		DisplayInfoResponse displayInfoResponse = detailService.getDisplayInfoResponse(displayInfoId); //서비스클래스를 이용하여 displayInfoId에 따른 reservationResponse 객체를 통째로 받아온다
		List<ProductPrice> productPrices = reservationService.getProductPrices(displayInfoId); //서비스클래스를 이용하여 displayInfoId에 따른 productPrice 리스트 객체를 통째로 받아온다
		String productId = String.valueOf((displayInfoResponse.getDisplayInfo().getProductId())); //productId 추출하여 변수에 저장
		String reserveStartDate = reservationService.getReserveStartDate(); //reserveStartDate 추출하여 변수에 저장
		String reserveEndDate = reservationService.getReserveEndDate(); //reserveEndDate 추출하여 변수에 저장
		String reserveRandomDate = reservationService.getReserveRandomDate(); //reserveRandomDate 추출하여 변수에 저장
		
		map.put("displayInfoId", displayInfoId); //맵객체에 displayInfoId 맵핑
		map.put("productId", productId); //맵객체에 productId 맵핑
		map.put("productImages", displayInfoResponse.getProductImages()); //맵객체에 productImage리스트 displayInfoResponse로부터 추출하여 맵핑
		map.put("displayInfo", displayInfoResponse.getDisplayInfo()); //맵객체에 displayInfo리스트 displayInfoResponse로부터 추출하여 맵핑
		map.put("productPrices", productPrices); //맵객체에 productPrice리스트 맵핑
		map.put("reserveStartDate", reserveStartDate); //맵객체에 reserveStartDate 맵핑
		map.put("reserveEndDate", reserveEndDate); //맵객체에 reserveEndDate 맵핑
		map.put("reserveRandomDate", reserveRandomDate); //맵객체에 reserveRandomDate 맵핑

		model.addAllAttributes(map); //모델맵에 맵객체 추가
		return "reserve"; //reserve 응답 반환
	}
	
	@PostMapping(path = "/reserve")
	public ReservationResponse setReservations(@RequestBody ReservationParam param){ //@RequestBody : http요청의 body 부분을 java 객체로 받을 수 있게 해주는 어노테이션. 주로 json을 받을 때 활용한다. //ReservationParam 객체를 외부로부터 요소로받아서 하여 /reserve로 post요청이 왔을때의 처리를 수행하는 매서드
		 return reservationService.setReservation(param); //서비스클래스를 이용하여 인자로받은 Param 객체를 등록(?)한다. 아마도?
	}
	
}
