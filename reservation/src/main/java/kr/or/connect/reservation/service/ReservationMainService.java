package kr.or.connect.reservation.service;

import kr.or.connect.reservation.dto.CategoryResponse;
import kr.or.connect.reservation.dto.Member;
import kr.or.connect.reservation.dto.ProductResponse;
import kr.or.connect.reservation.dto.PromotionResponse;

public interface ReservationMainService {//메인화면에 출력하기 위해 필요한 것들의 데이터베이스를 다 긁어오기위한 서비스 클래스
		public static final Integer LIMIT = 4;
		public PromotionResponse getPromotionItems();
		public CategoryResponse getCategoryItems();
		public ProductResponse getProductItems(Integer start);
		public ProductResponse getProductItems(Integer categoryId, Integer start);
		public void setTransactionSuccess(String substring);
}
