package kr.or.connect.reservation.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.Promotion;
import kr.or.connect.reservation.service.ReservationMainService;

public class PromoDaoTest {
	
	private static ApplicationContext ac;

	public static void main(String[] args) {
		ac = new AnnotationConfigApplicationContext(ApplicationConfig.class); 
		ReservationMainDao reservationMainpageDao = ac.getBean(ReservationMainDao.class);
		ReservationMainService serviceTest = ac.getBean(ReservationMainService.class);
		
		//Promotion 데이터베이스 테스트
		/*
		List<Promotion> promotionTestList = new ArrayList<Promotion>();
		promotionTestList = reservationMainpageDao.selectPromotionList();
		System.out.println(promotionTestList);
		*/
		
		//Category 데이터베이스 테스트
		/*
		List<Category> categoryTestList = new ArrayList<Category>();
		categoryTestList = reservationMainpageDao.selectCategoryList();
		System.out.println(categoryTestList);
		*/
		
		//Product 데이터 전체 갯수 출력 테스트
		Integer countTest;
		countTest = reservationMainpageDao.countAllProduct();
		System.out.println(countTest);
		
		
		//Product 의 Category 별 데이터 갯수 출력 테스트
		Integer countTest2;
		countTest2 = reservationMainpageDao.countAllProductSortbyCategory(1);
		System.out.println(countTest2);
	}

}
