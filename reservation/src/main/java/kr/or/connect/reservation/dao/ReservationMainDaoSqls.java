package kr.or.connect.reservation.dao;

public class ReservationMainDaoSqls {
	
	 //Promotion 데이터 받아오기 (프로모션 프론트단을 위한것임)
	 public static final String SELECT_PROMOTION_ITEMS = "select PROMO.id, PROMO.product_id, FILEINFO.id as promotionFileId FROM file_info FILEINFO left outer join product_image PRODUCTIMG on FILEINFO.id = PRODUCTIMG.file_id and PRODUCTIMG.type = 'th' join promotion PROMO on PROMO.product_id = PRODUCTIMG.product_id";
	 
	 //Product 의 Categㅐry 별 갯수 리스트 (카테고리 탭 프론트단을 위한것임)
	 public static final String SELECT_ITEMS_SORT_BY_CATEGORY = "select CAT.id, CAT.name, count(PROD.id) as count from category CAT join product PROD on CAT.id = PROD.category_id group by PROD.category_id";
	 
	 //전체 Product 의 총 갯수 계산 (전체리스트 총갯수를 표기하는 프론트단을 위한것임)
	 public static final String COUNT_ALL_ITEMS = "select count(*) from product";
	 
	 //매개변수를 받아서 Product 의 Category 별 갯수 계산 (프론트에서 매개변수 받아서 데이터베이스로부터 꺼내옴)
	 // =:는 함수의 매개변수로부터 받은값을 대입함
	 public static final String COUNT_ALL_ITEMS_SORT_BY_CATEGORY = "select count(*) from category join product on category.id = product.category_id where category.id=:category_id group by category_id";

	 //전체 Product start/limit 끊어서 출력
	 //DISP로 display_info 테이블 명명, displayinfoid와 placename을 가져옴, PROD로 product테이블 명명, content,description,id(product_id)가져옴, file_info테이블은 FILEINFO로 명명, save_file_nama(이미지url)을 가져옴, //from절에선 주축이될 product 테이블을 MP로 명명, 거기에다 서브로 붙일 product인 SMP(카테고리id 무결성확인) 붙이고 product_img테이블인 PIMG를 PIMG에 있는 product_id를 기준삼에 join함. 추가로 display_info테이블 DISP도 똑같이 product_id기준으로 join, fileinfo는 기준으로 할게 이미지파일이니 이미지의 id를 기준으로 join, where절에서는 썸네일임을 알리느 th 문자열을 기준으로 PIMG.type에서 한번 거르고 Product테이블의 id를 기준으로 group by절을 구성하며 마무리. 마지막에는 일정 갯수만큼을 출력하기위한 limit를 추가함.
	 public static final String SELECT_PAGING_ALLLIST = "select DISP.id as displayInfoId, DISP.place_name as placeName, MP.content as content, MP.description as description, MP.id as id, FILEINFO.id as productFileId FROM product MP join product SMP on (MP.category_id = SMP.category_id) join product_image PIMG on (MP.id = PIMG.product_id) join display_info DISP on (MP.id = DISP.product_id) join file_info FILEINFO on (PIMG.file_id = FILEINFO.id) where PIMG.type = 'th' group by MP.id limit :start, :limit";
	 
	 //Category 별 Product start/limit 끊어서 출력
	 public static final String SELECT_PAGING_DIVIDELIST = "select DISP.id as displayInfoId, DISP.place_name as placeName, MP.content as content, MP.description as description, MP.id as id, FILEINFO.id as productFileId FROM product MP join product SMP on (MP.category_id = SMP.category_id) join product_image PIMG on (MP.id = PIMG.product_id) join display_info DISP on (MP.id = DISP.product_id) join file_info FILEINFO on (PIMG.file_id = FILEINFO.id) where PIMG.type = 'th' and MP.category_id = :categoryId group by MP.id limit :start, :limit";
	 
}
