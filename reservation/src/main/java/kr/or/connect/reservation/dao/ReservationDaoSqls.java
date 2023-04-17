package kr.or.connect.reservation.dao;

public class ReservationDaoSqls {
	
	 //RI = ReservationInfo
	 //RIP = reservation_info_price
	 //PP = product_price
	 //SUM((PP.price - (PP.price * (PP.discount_rate / 100))) * RIP.count) as totalPrice : 원래의 상품가격(PP.price) 에서 할인율 (PP.discount_rate / 100) 을 곱한값을 뺀다. 그러면 할인된 상품의 가격이된다. 이를 예약상품 수만큼 곱하여 전체 주문한 상품의 가격을 구한다
	 //reservationInfo 들은 reservationInfoId를 기준으로 join 한다.
	 //productPrice와 연관있는 것들은 productPriceId를 기준으로 join 한다.
	 //조건절 WHERE : RI.reservation_email = :reservationEmail 을 조건으로 하여 밖에서 인자로 받은 reservationEmail를 기준으로 필터링함 
	 //group by RI.id order by RI.id DESC : RI.id 기준으로 그룹화 하고 RI.id 기준으로 내림차순(DESC) 정렬
	 //reservationName 별 ReservationInfo 리스트
	 public static final String SELECT_RESERVATION_INFO_LIST_BY_ID = "SELECT RI.id as reservationInfoId, RI.product_id as productId, RI.display_info_id as displayInfoId, RI.reservation_name as reservationName, RI.reservation_email as reservationEmail, RI.reservation_date as reservationDate, RI.cancel_flag as cancelYn, RI.create_date as createDate, RI.modify_date as modifyDate, SUM((PP.price - (PP.price * (PP.discount_rate / 100))) * RIP.count) as totalPrice, transaction_flag as transactionFlag from reservation_info RI join reservation_info_price RIP on (RIP. reservation_info_id = RI.id) join product_price PP on (RIP.product_price_id = PP.id) where RI.reservation_name = :reservationId group by RI.id order by RI.id DESC";
	 
	 //전시정보id 별 ProductPrice 리스트
	 public static final String SELECT_PRODUCT_PRICE_LIST_BY_DISPLAY_INFO_ID = "SELECT PP.create_date as createDate, PP.discount_rate as discountRate, PP.modify_date as modifyDate, PP.price as price, PP.price_type_name as priceTypeName, PP.product_id as productId, PP.id as productPriceId from product_price PP join product P on (PP.product_id = P.id) join display_info DI on (P.id = DI.product_Id) where DI.id = :displayInfoId";
	 
	 //예약정보id 별 ReservationInfo 업데이트
	 //reservation_info 테이블의 cancel_flag 컬럼을 밖에서 입력받은 값으로 SET, modify_date 컬럼도 현재시간으로 now() 구문을 이용하여 리셋. 입력받은 reservationInfoId에 따른 컬럼만을 업데이트하기위해 WHERE절에서 필터링함 
	 public static final String UPDATE_RESERVATION_INFO_BY_RESERVATION_INFO_ID = "UPDATE reservation_info SET cancel_flag = :cancel_flag, modify_date = now() WHERE ID = :reservationInfoId";
	 
	 //예약정보id 별 ReservationResponse 하나
	 public static final String SELECT_RESERVATION_RESPONSE_BY_RESERVATION_INFO_ID = "SELECT RI.cancel_flag as cancelYn, RI.create_date as createDate, RI.display_info_id as displayInfoId, RI.modify_date as modifyDate, RI.product_id as productId, RI.reservation_date as reservationDate, RI.reservation_email as reservationEmail, RI.id as reservationInfoId, RI.reservation_name as reservationName, RI.reservation_tel as reservationTelephone, SUM((PP.price - (PP.price * (PP.discount_rate / 100))) * RIP.count) as totalCount from reservation_info RI join reservation_info_price RIP on (RIP.reservation_info_id = RI.id) join product_price PP on (RIP.product_price_id = PP.id) where RI.id = :reservationInfoId"; //totalPrice >>> totalCount ?????
	 
	 //예약정보id 별 ReservationInfo 하나
	 public static final String SELECT_RESERVATION_INFO_BY_RESERVATION_INFO_ID = "SELECT RI.cancel_flag as cancelYn, RI.create_date as createDate, RI.display_info_id as displayInfoId, RI.modify_date as modifyDate, RI.product_id as productId, RI.reservation_date as reservationDate, RI.reservation_email as reservationEmail, RI.id as reservationInfoId, RI.reservation_name as reservationName, RI.reservation_tel as reservationTelephone, SUM((PP.price - (PP.price * (PP.discount_rate / 100))) * RIP.count) as totalPrice from reservation_info RI join reservation_info_price RIP on (RIP.reservation_info_id = RI.id) join product_price PP on (RIP.product_price_id = PP.id) where RI.id = :reservationInfoId"; //다시 totalPrice????, select 순서?????

	//reservationInfoId를 pk로 productId 알아내는 쿼리
	 public static final String FIND_PRODUCT_ID_OF_RESERVATION_INFO_ID = "select R.id as reservationInfoId, R.product_id as productId FROM reservation_info R where R.id=:reservationInfoId";

	 public static final String SELECT_PRODUCT_BY_DISPLAY_INFO_ID = "SELECT id, category_id, description, content FROM product WHERE id=:displayInfoId";

	 public static final String UPDATE_RESERVATION_TRANSACTION_TO_COMPLETE_BY_RESERVATION_INFO_ID = "UPDATE reservation_info SET transaction_flag = :transaction_flag, modify_date = now() WHERE ID = :id";

	 public static final String SELECT_TRANSACTION_FLAG_BY_RESERVATION_INFO_ID = "SELECT transaction_flag FROM reservation_info WHERE id = :id";
}
