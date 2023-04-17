package kr.or.connect.reservation.dao;

public class ReservationPriceDaoSqls {
	
	 //입력받은 예약정보id를 통해 상품가격id, 갯수, 예약정보가격id(primary key) , 예약정보id 를 가져온다.
	 public static final String SELECT_RESERVATION_PRICE_LIST_BY_RESERVATION_INFO_ID = "select id as reservationInfoPriceId, reservation_info_id as reservationInfoId, product_price_id as productPriceId, count from reservation_info_price where reservation_info_id = :reservationInfoId"; 


}
