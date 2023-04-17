package kr.or.connect.reservation.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ReservationDao;
import kr.or.connect.reservation.dao.ReservationPriceDao;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoResponse;
import kr.or.connect.reservation.dto.ReservationParam;
import kr.or.connect.reservation.dto.ReservationPrice;
import kr.or.connect.reservation.dto.ReservationResponse;
import kr.or.connect.reservation.service.ReservationService;

@Service //서비스클래스이므로 서비스 어노테이션 붙임
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	ReservationDao reservationDao;
	
	@Autowired
	ReservationPriceDao reservationPriceDao;
	
	@Transactional
	@Override
	public ReservationInfoResponse getReservationInfo(String reservationEmail) {
		
		/*
		reservationEmail 기반으로 reservation 정보를 받아온다.
		reservationinfo에는 예약 정보들과 displayinfo
		param 값으로 reservationinfo 받아온다.
		현재 size도 맞지 않고 reservationInfoResponse
		response만 transactional 처리
		 */
		
		ReservationInfoResponse reservationInfoResponse = new ReservationInfoResponse(); //요청에 대한 반응을 위한 reservationInfoResponse 객체 인스턴스 생성
		List<ReservationInfo> reservationInfoList = reservationDao.getReservationInfoList(reservationEmail); //매서드의 인자로받은 이메일에 따른 예약정보리스트를 특정, 해당하는 예약정보리스트를 담아둘 예약정보리스트 객체를 생성
		reservationInfoList = reservationDao.setReservationInfoListAsDisplayInfo(reservationInfoList); //예약정보리스트 객체에 Dao의 "예약정보리스트를 인자로 받으면, 그 리스트를 돌면서(전시정보id를 이용하여) 알맞은 전시정보리스틀 데이터베이스 로부터 집어넣는" 매서드를 이용하여 현재 선택된 예약정보리스트에 정보들을 집어넣음 
		
		int size = 0; //예약 수
		size = reservationInfoList.size(); //reservationInfoList 객체엔 이제 데이터베이스로부터 가져온 정보들이 들어있음. 예약수를 구해서 size 변수에 저장
		System.out.println(size); //예약수를 콘솔에 출력
		reservationInfoResponse.setReservations(reservationInfoList); //reservationInfoList 는 현재 잠시 정보들을 담아놓는 그릇임. 실제 Response를 하는것은 reservationInfoResponse 객체이므로 setReservations 매서드를 이용하여 reservationInfoList 의 내용을 Response 객채에 대입함
		reservationInfoResponse.setSize(size); //마찬가지로 예약수도 Response 객체에 대입함

		return reservationInfoResponse; //Response 객체에 알맞은 데이터를 모두 넣었으면 리턴함
	}

	@Override
	public String getReserveStartDate() {
		Calendar cal = Calendar.getInstance(); //예약 시작일을 위한 calendar형 객체 인스턴스 생성
		Date date = new Date(); //캘린더 인스턴스에 날짜를 셋팅하기위한 date 객체 생성
		cal.setTime(date); //캘린더 객체의 시간을 date 변수로 셋팅함
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN); //시간 표기의 포맷을 지정
		String reserveStartDate = formatTime.format(cal.getTime()); //reserveStartDate는 만들어놓은 시간표기 포맷에 캘린더객체의 시간을 읽어들여서 문자열의 형태로 저장
		return reserveStartDate; //reserveStarDate 반환
	}

	@Override
	public String getReserveEndDate() {
		Calendar cal = Calendar.getInstance();//예약 종료일을 위한 calendar형 객체 인스턴스 생성
		Date date = new Date(); //캘린더 인스턴스에 날짜를 셋팅하기위한 date 객체 생성
		cal.setTime(date); //캘린더 객체의 시간을 date 변수로 셋팅함
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN); //"yyyy.MM.dd.(EEE)" -> 연.월.일.(요일), //시간 표기의 포맷을 지정
		cal.add(Calendar.DATE, 4); //cal의 날짜로부터 4 days later에 해당하는 날로 cal 시간 변경
		String reserveEndDate = formatTime.format(cal.getTime()); //바뀐 4일후의 시간을 만들어둔 포맷형태대로 문자열로 저장
		return reserveEndDate; //reserveEndDate 반환
	}

	@Override
	public String getReserveRandomDate() { //따로 상품의 예약시간을 지정하지는 않고, 랜덤으로 생성한 값으로 처리한다
		Calendar cal = Calendar.getInstance(); //랜덤 예약날짜를 위한 calendar형 객체 인스턴스 생성
		Date date = new Date(); //캘린더 인스턴스에 날짜를 셋팅하기위한 date 객체 생성
		cal.setTime(date);//캘린더 객체의 시간을 date 변수로 셋팅함
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN); //"yyyy.MM.dd.(EEE)" -> 연.월.일.(요일)
		cal.add(Calendar.DATE, (int)((Math.random() * 5)+1)); //cal의 날짜로부터 (int)((Math.random() * 5)+1) days later 에 해당하는 날로 cal 시간 변경
		String reserveEndDate = formatTime.format(cal.getTime()); //바뀐 랜덤한 시간 이후의 날짜를 만들어둔 포맷형태대로 문자열로 저장
		return reserveEndDate; //reserveEndDate 반환
	}
	
	@Override
	public List<ProductPrice> getProductPrices(Integer displayInfoId) { //인자로받은 전시정보 id로 상품가격 리스트 가져오는 매서드
		List<ProductPrice> productPrice = reservationDao.getProductPriceList(displayInfoId); //전시정보id로 예약에쓰는 상품가격 리스트 받아서 리스트 객체에 집어넣기 
		return productPrice; //집어넣었으면 리스트객체 반환
	}

	@Override
	public String getReserveDate(Date date) { //받은 date 인자대로 formatDate 형태에 담은 후 리턴하는 매서드
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy.MM.dd.(EEE)", Locale.KOREAN); //"yyyy.MM.dd.(EEE)" -> 연.월.일.(요일)
		String formatDate = formatTime.format(date); //인자로 받은 날짜를 날짜를 만들어둔 포맷형태대로 문자열로 저장
		return formatDate; //포맷데이터 반환
	}
	
	@Override
	public int deleteReservationInfo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public ReservationResponse setReservation(ReservationParam param) { //예약자 이메일, 아이디, 이름 등이 있는 ReservationParam을 인자로 받아 매서드 안의 Response용 객체에 담는 매서드, 예약정보를 데이터베이스에도 담기
		ReservationResponse reservationResponse = new ReservationResponse(); //Response처리를 위한 응답용 객체 생성
		reservationResponse.setCreateDate(new Date()); //현재시간을 새로운 CreateDate로 reservationResponse에 셋팅
		reservationResponse.setCancelYn(false); //취소여부를 false로 reservationResponse에 셋팅
		reservationResponse.setDisplayInfoId(param.getDisplayInfoId()); //param 인자로부터 DisplayInfoId를 추출하여 reservationResponse에 셋팅
		reservationResponse.setModifyDate(new Date()); //현재시간을 새로운 ModifyDate로 reservationResponse에 셋팅
		reservationResponse.setProductId(param.getProductId()); //param 인자로부터 ProductId를 추출하여 reservationResponse에 셋팅
		
		SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyy.mm.dd"); //date형식의 객체를 원하는 포멧으로 출력할수있게 해주는 SimpleDateFormat객체
		SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd"); 
		java.util.Date tempDate = null; //tempDate = null로 초기화
		try {
			tempDate = beforeFormat.parse(param.getReservationYearMonthDay()); //임시 tempDate에 beforFormat의 형태로, param 인자로부터 예약 연월일을 받아서 변형한다
		} catch (ParseException e) { //어느걸 import해야하나? 현재는 java.text //예외처리
			e.printStackTrace();
		}

		// java.util.Date를 yyyy-mm-dd 형식으로 변경하여 String로 반환한다.
		String transDate = afterFormat.format(tempDate); //이제 tempDate를 afterFormat의 형태로 변환한다. 이는 스트링 자료형을 갖는다

		// 반환된 String 값을 Date로 변경한다.
		java.sql.Date reserveDate = java.sql.Date.valueOf(transDate);  //String 으로 변환한 transDate는 다시 Date형으로 변환하여 reserveDate 변수에 대입한다
		reservationResponse.setReservationDate(reserveDate); //반응용 reservationResponse에 reserveDate를 ReservationDate로써 셋팅한다

		reservationResponse.setReservationEmail(param.getReservationEmail()); //param 인자로부터 ReservationEmail를 추출하여 reservationResponse에 셋팅
		reservationResponse.setReservationName(param.getReservationName()); //param 인자로부터 ReservationName를 추출하여 reservationResponse에 셋팅
		reservationResponse.setReservationTelephone(param.getReservationTelephone()); //param 인자로부터 ReservationTelephone를 추출하여 reservationResponse에 셋팅
		Integer reservationInfoId = reservationDao.insertReservationResponse(reservationResponse); //reservationInfoId 변수생성, '클라이언트가 작성한 예약정보를 데이터베이스에 삽입할때 사용할 매서드'(insertReservationResponse) 를 사용할 reservationResponse가 완성되었음으로 해당매서드를 이용한다. 반환값으로는 새로이 생성된 id값을 받는다
		System.out.println("예약 아이디 : "+reservationInfoId); //생성된 예약id 콘솔에 출력
		
		List<ReservationPrice> reservationPrices = new ArrayList<ReservationPrice>(); //예약자가 구매한 상품가격, 갯수 등을 위한 reservationPrice 리스트 객체 생성
		for(int i = 0; i < param.getPrices().size(); i++) { //리스트를 대상으로 반복문
			ReservationPrice reservationPrice = new ReservationPrice(); //관련정보를 담을 ReservationPrice 객체 생성
			reservationPrice.setCount(param.getPrices().get(i).getCount()); //reservationPrice 객체에 인자로부터 추출한 상품갯수정보를 셋팅
			reservationPrice.setProductPriceId(param.getPrices().get(i).getProductPriceId()); //reservationPrice 객체에 인자로부터 추출한 ProductPriceId를 셋팅
			reservationPrice.setReservationInfoId(reservationInfoId.intValue()); //reservationPrice 객체에 인자로부터 추출한 ProductPriceId를 셋팅
			Integer reservationInfoPriceId = reservationPriceDao.insertReservationPrice(reservationPrice); //reservationInfoPriceId도 생성하기위해 insertReservationPrice 매서드를 이용하여 reservationPrice를 인자로 넣고 id를 생성받는다
			reservationPrice.setReservationInfoPriceId(reservationInfoPriceId.intValue());//생성받은 id값을 setReservationInfoPriceId매서드를 통해 reservationPrice객체에 집어넣는다
			reservationPrices.add(reservationPrice); //reservationPrices 리스트 객체에 완성된 reservationPrice 단일객체를 add하여 밀어넣는다
		}
		
		reservationResponse.setPrices(reservationPrices); //처리가끝난 reservationPrices를 인자로 reservationResponse 객체에 상품갯수,가격,id등를 갱신한다
		return reservationResponse; //처리가 끝낫으면 reservationResponse 객체를 반환한다
		
	}

	@Override
	@Transactional
	public ReservationResponse apicancelReservation(Integer reservationInfoId) { //reservationInfoId를 인자로 받아 예약을 취소하는 매서드(api)
		ReservationResponse reservationResponse = new ReservationResponse(); //처리하기전의 새 reservationResponse 객체 생성
		int updateResult = reservationDao.updateReservation(reservationInfoId); //캔슬한결과를 업데이트하기위한 객체생성, 인자로받은 reservationInfoId로 업데이트할 예약을 정하고 그 값을 객체에 저장
		System.out.println("업데이트 결과 : "+updateResult); //updateResult 값 확인
		if(updateResult == 1) { //결과가 1이라면
			reservationResponse = reservationDao.getReservationResponse(reservationInfoId);  //reservationResponse 객체에 reservationInfoId에 알맞는 데이터를 넣는다
			reservationResponse.setPrices(reservationPriceDao.getReservationPrice(reservationInfoId)); //인자로 받은 reservationInfoId에 알맞는 ReservationPrice를 추출한뒤 그 값으로 reservationResponse의 Prices 객체(예약한 상품갯수, 가격, id등이 적혀있음)를 셋팅한다
		}else {//결과가 1이 아니라면
			return null;//널값을 반환한다
		}
		return reservationResponse; //처리가 끝난 reservationResponse객체를 반환한다
	}

	@Override
	public ReservationInfo cancelReservation(Integer reservationInfoId) { //예약 취소 구현 매서드
		ReservationInfo reservationInfo = new ReservationInfo(); //예약정보 담을 객체
		int updateResult = reservationDao.updateReservation(reservationInfoId); //캔슬한결과를 업데이트하기위한 객체생성, 인자로받은 reservationInfoId로 업데이트할 예약을 정하고 그 값을 객체에 저장
		System.out.println("업데이트 결과 : "+updateResult); //업데이트결과 콘솔 출력
		if(updateResult == 1) { //만약 updateResult가 1일 경우
			reservationInfo = reservationDao.getReservationInfo(reservationInfoId); //예약정보객체에 reservationInfoId에 알맞는 데이터를 넣는다
			reservationInfo.setDisplayInfo(reservationDao.getDisplayInfoList(reservationInfo.getDisplayInfoId())); //그리고 거기에서 DisplayInfoId를 추출하여 전시정보리스트를 손에 넣는다. 그리고 setDisplayInfo 매서드로 현재(this)객체에 추출한 전시정보를 담는다
		}else {
			return null; //updateResult가 1이 아닐경우 null 반환
		}
		return reservationInfo; //예약정보 반환
	}

	@Override
	public ReservationInfo getReservationInfo(Integer reservationInfoId) {
		ReservationInfo reservationInfo = new ReservationInfo();
		reservationInfo=reservationDao.getReservationInfo(reservationInfoId);
		return reservationInfo;
	}

	@Override
	public Product getProductByDisplayInfoId(int displayInfoId) {
		return reservationDao.getProductByDisplayInfoId(displayInfoId);
	}

	@Override
	public boolean getTransactionFlag(String reservationId) {
		if(reservationDao.selectTransactionFlagByReservationId(reservationId).getTransaction_flag()==1) {
			return true;
		}else {
			return false;
		}
	}

	

}
