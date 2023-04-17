package kr.or.connect.reservation.dao;

public class DetailDaoSqls {

	 //전시정보id 별 Comment 리스트
	 //RUC 에서는 reservation_user_comment 테이블의 약자. 테이블로부터 comment, id(primary key), create_date, modify_date, product_id, reservation_info_id, score 를 SELECT
	 //RI 에서는 reservation_info 테이블의 약자. 테이블로부터  reservation_date, reservation_email, reservation_name, reservation_tel 을 SELECT
	 //테이블 JOIN : DI(display_info 테이블)와 RUC의 JOIN에는 product_id 를 기준삼음. RUC와 RI의 JOIN 시에는 RI의 id(primary key) 와 RUC의 reservation_info_id로 비교함
	 //조건절 WHERE : DI.id = :displayInfoId 을 조건으로 하여 밖에서 인자로 받은 displayInfoId를 기준으로 필터링함 
	 //ORDER BY절 : RUC.id 기준으로 내림차순(DESC) 정렬
	 public static final String SELECT_COMMENT_LIST_BY_DISPLAY_INFO_ID = "select RUC.comment as comment, RUC.id as commentId, RUC.create_date as createDate, RUC.modify_date as modifyDate, RUC.product_id as productId, RI.reservation_date as reservationDate, RI.reservation_email as reservationEmail, RUC.reservation_info_id as reservationInfoId, RI.reservation_name as reservationName, RI.reservation_tel as reservationTelephone, RUC.score as score from display_info DI join reservation_user_comment RUC on (DI.product_id = RUC.product_id) join reservation_info RI on (RI.id = RUC.reservation_info_id) where DI.id = :displayInfoId order by RUC.id desc";
	 
	 //전시정보id 별 CommentImage 리스트
	 public static final String SELECT_COMMENT_IMAGE_LIST_BY_DISPLAY_INFO_ID = "select FI.content_type as contentType, FI.create_date as createDate, FI.delete_flag as deleteFlag , FI.id as fileId, FI.file_name as fileName, RUCI.id as imageId, FI.modify_date as modifyDate, RUCI.reservation_info_id as reservationInfoId, RUCI.reservation_user_comment_id as reservationUserCommentId, FI.save_file_name as saveFileName from display_info DI join reservation_user_comment RUC on (DI.product_id = RUC.product_id) join reservation_user_comment_image RUCI on (RUC.id = RUCI.reservation_user_comment_id) join file_info FI on (FI.id = RUCI.file_id) where DI.id = :displayInfoId";
	 
	 //전시정보id 별 Display_Info 리스트
	 public static final String SELECT_DISPLAY_INFO_LIST_BY_DISPLAY_INFO_ID = "select C.id as categoryId, C.name as categoryName, DI.create_date as createDate , DI.id as displayInfoId, DI.email as email, DI.homepage as homepage, DI.modify_date as modifyDate, DI.opening_hours as openingHours, DI.place_lot as placeLot, DI.place_name as placeName, DI.place_street as placeStreet, P.content as productContent, P.description as productDescription, P.event as productEvent, P.id as productId, DI.tel as telephone from display_info DI join product P on (P.id = DI.product_id) join category C on (C.id = P.category_id) where DI.id = :displayInfoId";
	 
	 //전시정보id 별 Display_Info_Image 리스트
	 public static final String SELECT_DISPLAY_INFO_IMAGE_LIST_BY_DISPLAY_INFO_ID = "select FI.content_type as contentType, FI.create_date as createDate, FI.delete_flag as deleteFlag , DI.id as displayInfoId, DII.id as displayInfoImageId, FI.id as fileId, FI.file_name as fileName, FI.modify_date as modifyDate, FI.save_file_name as saveFileName from display_info DI join display_info_image DII on (DI.id = DII.display_info_id) join file_info FI on (FI.id = DII.file_id) where DI.id = :displayInfoId";
	 
	 //전시정보id 별 Product_Image 리스트
	 public static final String SELECT_PRODUCT_IMAGE_LIST_BY_DISPLAY_INFO_ID = "select FI.content_type as contentType, FI.create_date as createDate, FI.delete_flag as deleteFlag, FI.id as fileInfoId, FI.file_name as fileName, FI.modify_date as modifyDate, PI.product_id as productId, PI.id as productImageId, FI.save_file_name as saveFileName, PI.type as type from display_info DI join product_image PI on (DI.product_id = PI.product_id) join file_info FI on (FI.id = PI.file_id) where DI.id = :displayInfoId";
	 
	 //전시정보id 별 Product_Price 리스트
	 public static final String SELECT_PRODUCT_PRICE_LIST_BY_DISPLAY_INFO_ID = "select PP.create_date as createDate, PP.discount_rate as discountRate, PP.modify_date as modifyDate, PP.price as price, PP.price_type_name as priceTypeName, PP.product_id as productId, PP.id as productPriceId from display_info DI join product_price PP on (PP.product_id = DI.product_id) where DI.id = :displayInfoId";

}
