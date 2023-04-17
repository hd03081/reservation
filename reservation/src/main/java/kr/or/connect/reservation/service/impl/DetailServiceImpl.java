package kr.or.connect.reservation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.DetailDao;
import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImage;
import kr.or.connect.reservation.dto.DisplayInfoResponse;
import kr.or.connect.reservation.dto.ProductImage;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.Promotion;
import kr.or.connect.reservation.dto.PromotionResponse;
import kr.or.connect.reservation.service.DetailService;

@Service //서비스클래스이므로 서비스 어노테이션 붙임
public class DetailServiceImpl implements DetailService{
	
	@Autowired
	DetailDao detailDao;

	@Override //트랜잭셔녈 매서드를 생성
	@Transactional //트랜잭션, 아래의 과정들이 모두 성공하지않는다면 하나도 성공한것으로 치지않고 실제로 데이터가 변화하지도않음
	public DisplayInfoResponse getDisplayInfoResponse(Integer displayInfoId) {
		DisplayInfoResponse displayInfoResponse = new DisplayInfoResponse(); //displayInfoResponse의 여러 매서드, 리스트등을 다루기위한 displayInfoResponse 객체 생성
		
		Map<String, Double> countAndSum = new HashMap<>(); //평점 합, 갯수 저장을 위한 맵객체 생성. dao의 평점합평균 구하기 메서드를 위한 변수임
		
		DisplayInfo displayInfo = detailDao.getDisplayInfoList(displayInfoId); //displayInfoId에 따른 displayInfo리스트 를 담아둘 객체 생성
		DisplayInfoImage displayInfoImage = detailDao.getDisplayInfoImageList(displayInfoId); //displayInfoId에 따른 displayInfoImage리스트 를 담아둘 객체 생성
		List<ProductImage> productImages = detailDao.getProductImageList(displayInfoId); //displayInfoId에 따른 상품이미지 리스트를 담아둘 리스트형 객체 생성
		List<ProductPrice> productPrices = detailDao.getProductPriceList(displayInfoId); //displayInfoId에 따른 상품가격 리스트를 담아둘 리스트형 객체 생성
		
		Double averageScore = new Double(0); //평균평점을 담아둘 double 객체 생성
		List<CommentImage> commentImages = detailDao.getCommentImageList(displayInfoId); //displayInfoId에 따른 코멘트이미지 리스트를 담아둘 리스트형 객체 생성
		List<Comment> comments = detailDao.getCommentList(displayInfoId); //displayInfoId에 따른 코멘트 리스트를 담아둘 리스트형 객체 생성
		comments = detailDao.setCommentAsCommentImages(comments, commentImages); //준비된 리스트형 객체에 코멘트와 코멘트이미지 리스트를 매개변수로 갖는 dao 매서드 호출
		countAndSum = detailDao.getCountAndSumOfScoresFromcomments(comments); //해쉬맵 객체에 평점 갯수와 합을 구하는 dao 매서드를 호출하여 정보 담기
		averageScore = detailDao.getAverageScore(countAndSum); //위에서 만든 해쉬맵객체를 매개변수로 하여 평점구하는 dao 매서드를 호출
		List<ProductImage> extractproductImages = detailDao.extractProductEtcImage(productImages); //상품이미지 추출을 위한 dao 매서드 호출하여 새로운 상품이미지 리스트에 담기
		
		displayInfoResponse.setAverageScore(averageScore);//위에서 만든 displayInfoResponse 객체는 아직 데이터베이스로부터 아무런 데이터를 받은게 없음. 이 라인에서 displayInfoResponse의
														  //setter메서드를 이용하여 위의 averageScore객체(얘는 데이터베이스로부터 자료를받은상태임)를 비어있던 displayInfoResponse 객체에 붙여넣음
		displayInfoResponse.setComments(comments); 		  //이하 같음
		displayInfoResponse.setDisplayInfo(displayInfo);
		displayInfoResponse.setDisplayInfoImage(displayInfoImage);
		displayInfoResponse.setProductImages(extractproductImages);
		displayInfoResponse.setProductPrices(productPrices);
		
		return displayInfoResponse;//이제 displayInfoResponse는 데이터베이스로부터 받은 자료를 포함하고있음. 그대로 return
	}

}
