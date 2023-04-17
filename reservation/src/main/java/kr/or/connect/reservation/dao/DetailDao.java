package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.DetailDaoSqls.SELECT_COMMENT_IMAGE_LIST_BY_DISPLAY_INFO_ID;
import static kr.or.connect.reservation.dao.DetailDaoSqls.SELECT_COMMENT_LIST_BY_DISPLAY_INFO_ID;
import static kr.or.connect.reservation.dao.DetailDaoSqls.SELECT_DISPLAY_INFO_IMAGE_LIST_BY_DISPLAY_INFO_ID;
import static kr.or.connect.reservation.dao.DetailDaoSqls.SELECT_DISPLAY_INFO_LIST_BY_DISPLAY_INFO_ID;
import static kr.or.connect.reservation.dao.DetailDaoSqls.SELECT_PRODUCT_IMAGE_LIST_BY_DISPLAY_INFO_ID;
import static kr.or.connect.reservation.dao.DetailDaoSqls.SELECT_PRODUCT_PRICE_LIST_BY_DISPLAY_INFO_ID;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.DisplayInfoImage;
import kr.or.connect.reservation.dto.ProductImage;
import kr.or.connect.reservation.dto.ProductPrice;

@Repository
public class DetailDao {
	private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<Comment> comment_rowMapper = BeanPropertyRowMapper.newInstance(Comment.class); //Comment 로우맵
    private RowMapper<CommentImage> commentImage_rowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class); //CommentImage 로우맵
    private RowMapper<DisplayInfo> displayInfo_rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class); //DisplayInfo 로우맵
    private RowMapper<DisplayInfoImage> displayInfoImage_rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfoImage.class); //DisplayInfoImage 로우맵
    private RowMapper<ProductImage> productImage_rowMapper = BeanPropertyRowMapper.newInstance(ProductImage.class); //ProductImage 로우맵
    private RowMapper<ProductPrice> productPrice_rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class); //ProductPrice 로우맵
    
    public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	} //이거 왜쓴거지?

    public DetailDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        					.withTableName("DisplayInfo")
        					.usingGeneratedKeyColumns("id");
    }
    
    public Double getAverageScore(Map<String,Double> map) { //평균 점수를 구하기위한 dao. 인자로 받은 map객체에서 sum(총합)과 count(갯수)를 뽑아내어 평균을 구한 후 리턴함
		BigDecimal average = new BigDecimal(0); //math의 bigdecimal 객체를 이용한 평균값 계산.average를 빅데시멀값 0으로 초기화
		BigDecimal sum = new BigDecimal(map.get("sum")); //맵에서 sum 키워드로 값 추출
		BigDecimal count = new BigDecimal(map.get("count")); //맵에서 count 키워드로 값 추출
		if(!count.equals(average)) {average = sum.divide(count, 16, RoundingMode.DOWN);} //count즉 나눌값이 현재 초기화된 average값인 0일경우를 제외한 경우에 소수점 16자리까지 반내림 평균값을 구한다
    return average.doubleValue(); //빅데시멀 자료형의 값을 더블로 변환하여 일반적으로 사용할수 있도록하고 리턴함
    }
    
    public List<Comment> getCommentList(Integer displayInfoId) { //코멘트 리스트를 얻기위한 get 메서드
    	try {
			Map<String,Integer> params = Collections.singletonMap("displayInfoId", displayInfoId); //키값 String, 밸류값 Integer를 가지는 맵 params 생성.
																								   //해당 싱글톤맵"params"는 {"displayInfoId"=displayInfoId} 와 같음
			return jdbc.query(SELECT_COMMENT_LIST_BY_DISPLAY_INFO_ID, params, comment_rowMapper);//params 에는 sql문의 where절에서 =:"displayInfoId" 해당 구문이 받아먹을 displayInfoId가 밸류값으로 존재함
		}catch (EmptyResultDataAccessException e){ //예외처리
			e.printStackTrace();
			return null;
		}
    }
    
    public List<CommentImage> getCommentImageList(Integer displayInfoId) { //리플창에 넣을 이미지 리스트를 받아오는 get 메서드
		try {
			Map<String,Integer> params = Collections.singletonMap("displayInfoId", displayInfoId); //키값 String, 밸류값 Integer를 가지는 맵 params 생성.
																								   //해당 싱글톤맵"params"는 {"displayInfoId"=displayInfoId} 와 같음 
			return jdbc.query(SELECT_COMMENT_IMAGE_LIST_BY_DISPLAY_INFO_ID, params, commentImage_rowMapper); //params 에는 sql문의 where절에서 =:"displayInfoId" 해당 구문이 받아먹을 displayInfoId가 밸류값으로 존재함
		}catch (EmptyResultDataAccessException e){ //예외처리
			e.printStackTrace();
			return null;
		}
	}
	
	public DisplayInfo getDisplayInfoList(Integer displayInfoId) { //전시정보 리스트를 받아오는 get 메서드
		try {
			Map<String,Integer> params = Collections.singletonMap("displayInfoId", displayInfoId);
			return jdbc.queryForObject(SELECT_DISPLAY_INFO_LIST_BY_DISPLAY_INFO_ID, params, displayInfo_rowMapper);
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	}

	public DisplayInfoImage getDisplayInfoImageList(Integer displayInfoId) { //전시정보 이미지 리스트를 받아오는 get 메서드
		try {
			Map<String,Integer> params = Collections.singletonMap("displayInfoId", displayInfoId);
			return jdbc.queryForObject(SELECT_DISPLAY_INFO_IMAGE_LIST_BY_DISPLAY_INFO_ID, params, displayInfoImage_rowMapper);
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ProductImage> getProductImageList(Integer displayInfoId) { //상품 이미지 리스트를 받아오는 get 메서드
		try {
			Map<String,Integer> params = Collections.singletonMap("displayInfoId", displayInfoId);
			return jdbc.query(SELECT_PRODUCT_IMAGE_LIST_BY_DISPLAY_INFO_ID, params, productImage_rowMapper);
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	}

	public List<ProductPrice> getProductPriceList(Integer displayInfoId) { //상품 가격 리스트를 받아오는 get 메서드
		try {
			Map<String,Integer> params = Collections.singletonMap("displayInfoId", displayInfoId);
			return jdbc.query(SELECT_PRODUCT_PRICE_LIST_BY_DISPLAY_INFO_ID, params, productPrice_rowMapper);
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}

	}

	//=====================================================
	
	public List<Comment> setCommentAsCommentImages(List<Comment> comments, List<CommentImage> commentImages){ //코멘트 리스트와 코멘트_이미지리스트를 비교하여 각 id에 알맞는 이미지를 출력할수 있도록함
		for(int i = 0; i < comments.size(); i++) //코멘트의 총 숫자만큼 반복문
		{
			ArrayList<CommentImage> tempCommentImage = new ArrayList<>(); //알맞은 코멘트 이미지의 임시용 배열 객체를 새로이 만듦
			for(int j = 0; j < commentImages.size(); j++ ) //코멘트 이미지의 총 길이만큼 반복문
			{
				if(comments.get(i).getCommentId() == commentImages.get(j).getReservationUserCommentId()) //상위 반복문(코멘트의 총 숫자만큼 반복문)의 현재 인덱스를 구하여(get(i)) 그 부분의 코멘트 id를 구하고 그것을 코멘트이미지의 현재 인덱스의 유저코멘트id와 비교하여 일치할 경우(해당 코멘트가 이미지를 포함하는게 확실한 경우) 미리 만들어둔 임시배열객체에 추가함
				{
					tempCommentImage.add(commentImages.get(j)); //임시 배열객체에 조건문에서 걸러진 알맞은 코멘트이미지를 집어넣음.
				}
			} //코멘트 이미지단의 필터링을 위한 반복문이 끝남으로써 현재 tempCommentImage에는 알맞은 이미지의 reservationUserCommentId 가 들어가있는 상태임.
			comments.get(i).setCommentImages(tempCommentImage); //comments 리스트에 알맞은 이미지의 id를 삽입하고 이를 모든 코멘트리스트 를 확인할때까지 반복 
		}
		return comments; //반복문이 다 끝나서 comments의 모든 요소에 알맞은 이미지id가 들어갔다면 리턴함
	}
	
	public Map<String, Double> getCountAndSumOfScoresFromcomments (List<Comment> comments){ //점수의 갯수와 총합을 commentList에서 뽑아오는 매서드, 매개변수로 comment리스트인 comments를 받는다
		Map<String, Double> map = new HashMap<>(); //키값 String, 밸류값 Double를 가지는 해쉬맵 map 생성.
		
		Double count = 0.0; //double변수 count 선언 및 초기화
		Double sum = 0.0; //double변수 sum 선언 및 초기화
		
		for(int i = 0; i < comments.size(); i++) //comments 리스트 길이만큼 반복문
		{
			sum = sum + comments.get(i).getScore(); //코멘트 리스트 반복돌면서 점수를 전부 더함
			count = count + 1; //점수를 더할때마다 갯수를 1개씩 늘리면서 갯수도 구함
		}
		map.put("count", count); //리턴할 맵객체에 count값 삽입
		map.put("sum", sum); //리턴할 맵객체에 sum값 삽입
		return map; //다 넣었으면 리턴
	}
	
	public List<ProductImage> extractProductEtcImage(List<ProductImage> productImages){ //상품 기타이미지 추출용 매서드
		List<ProductImage> tempProductImages = new ArrayList<>(); //추출한 상품이미지를 넣어놓을 임시 상품이미지 리스트
		
	
		for(int i = 0; i < productImages.size(); i++) //프로덕트 이미지 리스트의 길이만큼 반복문(프로덕트 이미지 리스트를 돌면서 임시 상품이미지 객체에 type이 "ma"인 이미지만 걸러서 넣는 필터링 반복문
		{
			if(productImages.get(i).getType().equals("ma")) //이미지 타입이 "ma"인 경우에만 거르는 필터
			{
				ProductImage tempProductImage = new ProductImage(); //ma 이미지를 임시로 넣어둘 객체
				tempProductImage.setContentType(productImages.get(i).getContentType()); //이하, 해당 ma 이미지의 모든 요소를 임시 객체에 넣음
				tempProductImage.setCreateDate(productImages.get(i).getCreateDate());
				tempProductImage.setDeleteFlag(productImages.get(i).isDeleteFlag());
				tempProductImage.setFileInfoId(productImages.get(i).getFileInfoId());
				tempProductImage.setFileName(productImages.get(i).getFileName());
				tempProductImage.setModifyDate(productImages.get(i).getModifyDate());
				tempProductImage.setProductId(productImages.get(i).getProductId());
				tempProductImage.setProductImageId(productImages.get(i).getProductImageId());
				tempProductImage.setSaveFileName(productImages.get(i).getSaveFileName());
				tempProductImage.setType(productImages.get(i).getType());
				tempProductImages.add(tempProductImage); //임시 이미지에 정보를 다 넣었으면 임시 이미지리스트에 차곡차곡 넣음
				System.out.println(productImages.get(i).getSaveFileName()); //콘솔에 반복문의 현재 인덱스의 savefilename을 출력하기
				break; //반복문 탈출
			}
		}
		
		for(int i = 0; i < productImages.size(); i++) //프로덕트 이미지 리스트의 길이만큼 반복문(프로덕트 이미지 리스트를 돌면서 임시 상품이미지 객체에 type이 "et"인 이미지만 걸러서 넣는 필터링 반복문
		{
			if(productImages.get(i).getType().equals("et"))//이미지 타입이 "et"인 경우에만 거르는 필터
			{
				ProductImage tempProductImage = new ProductImage(); //et 이미지를 임시로 넣어둘 객체
				tempProductImage.setContentType(productImages.get(i).getContentType()); //이하, 해당 et 이미지의 모든 요소를 임시 객체에 넣음
				tempProductImage.setCreateDate(productImages.get(i).getCreateDate());
				tempProductImage.setDeleteFlag(productImages.get(i).isDeleteFlag());
				tempProductImage.setFileInfoId(productImages.get(i).getFileInfoId());
				tempProductImage.setFileName(productImages.get(i).getFileName());
				tempProductImage.setModifyDate(productImages.get(i).getModifyDate());
				tempProductImage.setProductId(productImages.get(i).getProductId());
				tempProductImage.setProductImageId(productImages.get(i).getProductImageId());
				tempProductImage.setSaveFileName(productImages.get(i).getSaveFileName());
				tempProductImage.setType(productImages.get(i).getType());
				tempProductImages.add(tempProductImage); //임시 이미지에 정보를 다 넣었으면 임시 이미지리스트에 차곡차곡 넣음
				break;//반복문 탈출
			}
			
		}
		return tempProductImages; //임시 상품리스트객체 리턴
	}
	
}
