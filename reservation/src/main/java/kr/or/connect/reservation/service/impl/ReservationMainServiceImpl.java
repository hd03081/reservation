package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ReservationMainDao;
import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.CategoryResponse;
import kr.or.connect.reservation.dto.Member;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductResponse;
import kr.or.connect.reservation.dto.Promotion;
import kr.or.connect.reservation.dto.PromotionResponse;
import kr.or.connect.reservation.service.ReservationMainService;

@Service //서비스클래스이므로 서비스 어노테이션 붙임
public class ReservationMainServiceImpl implements ReservationMainService { //service를 implements하여 원본 서비스클래스의 모든 내용 활용할수있도록함
	@Autowired
	ReservationMainDao reservationMainDao; //dao를 쓰기위해 dao 객체 선언 및 autowired 어노테이션을 붙임
	
	//promotion 목록 가져오는 transactional한 일련의 과정
		@Override //메인페이지서비스의 getPromotionItems()를 오버라이드하여 새로이 트랜잭셔녈 매서드를 생성
		@Transactional //트랜잭션, 아래의 과정들이 모두 성공하지않는다면 하나도 성공한것으로 치지않고 실제로 데이터가 변화하지도않음
		public PromotionResponse getPromotionItems() { //PromotionResponse 안에는 promotion모델객체를 다루기위한 여러 매서드, 변수가 존재함
			PromotionResponse promotionItem = new PromotionResponse(); //PromotionResponse의 여러 매서드, 리스트등을 다루기위한 PromotionResponse 객체 생성
			List<Promotion> promotionItems =  reservationMainDao.selectPromotionList(); //Promotion에 대한, 데이터베이스로부터 받아온 정보를 dao의 매서드를 활용하여
																						//promotionItems 라는 리스트에 집어넣음
			System.out.println(promotionItems); //데이터베이스에서 처음으로 가져온 초벌 리스트에 데이터가 잘들어있나 콘솔출력
			promotionItem.setItems(promotionItems);//위에서 만든 PromotionResponse 객체는 아직 데이터베이스로부터 아무런 데이터를 받은게 없음. 이 라인에서 PromotionResponse의
												//setter메서드를 이용하여 바로윗줄의 promotionItems객체(얘는 데이터베이스로부터 자료를받은상태임)를 비어있던 PromotionItem에 붙여넣음
			System.out.println(promotionItem);  //초벌 리스트에서 옮겨담은 PromotionResponse객체 promotionItem에 데이터가 잘들어있나 콘솔출력
			
			//FileController를 import하여 PromotionResponse에서 사용할 이미지 파일들 다운받기
			
			return promotionItem;//이제 promotionItem은 데이터베이스로부터 받은 자료를 포함하고있음. 그대로 return
		}

		@Override
		@Transactional
		public CategoryResponse getCategoryItems() {
			CategoryResponse categoryItem = new CategoryResponse();
			List<Category> categoryItems =  reservationMainDao.selectCategoryList();
			System.out.println(categoryItems);
			categoryItem.setItems(categoryItems);
			System.out.println(categoryItem); 
			return categoryItem;
		}

		@Override
		@Transactional
		public ProductResponse getProductItems(Integer start) {
			ProductResponse productItem = new ProductResponse();
			List<Product> productItems = reservationMainDao.selectAllProductList(start, ReservationMainService.LIMIT);
			int count = reservationMainDao.countAllProduct();
			productItem.setItems(productItems);
			productItem.setCount(count);
			return productItem;
		}

		@Override
		@Transactional
		public ProductResponse getProductItems(Integer categoryId, Integer start) {
			ProductResponse productItem = new ProductResponse();
			List<Product> productItems = reservationMainDao.selectDivideProductList(categoryId,start,ReservationMainService.LIMIT);
			int count = reservationMainDao.countAllProductSortbyCategory(categoryId);
			productItem.setItems(productItems);
			productItem.setCount(count);
			return productItem;
		}

		@Override
		public void setTransactionSuccess(String substring) {
			reservationMainDao.updateTransactionSuccess(substring);
		}

		

}
