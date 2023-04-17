package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.DetailDaoSqls.SELECT_DISPLAY_INFO_LIST_BY_DISPLAY_INFO_ID;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_RESERVATION_INFO_LIST_BY_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_PRODUCT_PRICE_LIST_BY_DISPLAY_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.UPDATE_RESERVATION_INFO_BY_RESERVATION_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_RESERVATION_RESPONSE_BY_RESERVATION_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_RESERVATION_INFO_BY_RESERVATION_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_PRODUCT_BY_DISPLAY_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_TRANSACTION_FLAG_BY_RESERVATION_INFO_ID;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationResponse;
import kr.or.connect.reservation.dto.TransactionResponse;

@Repository
public class ReservationDao {
	final static Integer CANCLE_FLAG = 1; //취소상태를 표현하기위한 전역변수
	
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	private RowMapper<ReservationInfo> reservationInfo_rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class); //ReservationInfo 로우맵
	private RowMapper<DisplayInfo> displayInfo_rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class); //DisplayInfo 로우맵
	private RowMapper<ProductPrice> productPrice_rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class); //ProductPrice 로우맵
	private RowMapper<ReservationResponse> reservationResponse_rowMapper = BeanPropertyRowMapper.newInstance(ReservationResponse.class); //ReservationResponse 로우맵
	private RowMapper<Product> product_rowMapper = BeanPropertyRowMapper.newInstance(Product.class);
	RowMapper<TransactionResponse> transaction_rowMapper = BeanPropertyRowMapper.newInstance(TransactionResponse.class);
	
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	} //이거 왜쓴거지?
	
	public ReservationDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        					.withTableName("reservation_info") //.withTableName("DisplayInfo")
        					.usingGeneratedKeyColumns("id");
        
        //이건뭘까??? id를 새로 생성하는 뭔가같음
        /*SimpleJdbcInsertOperations insertion = new SimpleJdbcInsert(dataSource)
		.withTableName("reservation_info_price").usingGeneratedKeyColumns("id");*/
    }
	
	public List<ReservationInfo> getReservationInfoList(String reservationId) { //reservationEmail 에 따른 예약정보를 맵에 담는 매서드
		try {
			Map<String,String> params = Collections.singletonMap("reservationId", reservationId); //키값 String, 밸류값 String를 가지는 맵 params 생성.
																								   //해당 싱글톤맵"params"는 {"reservationEmail"=reservationEmail} 와 같음 
			return jdbc.query(SELECT_RESERVATION_INFO_LIST_BY_ID, params, reservationInfo_rowMapper); //params 에는 sql문의 where절에서 =:"reservationEmail" 해당 구문이 받아먹을 reservationEmail가 밸류값으로 존재함
		}catch (EmptyResultDataAccessException e){ //예외처리
			e.printStackTrace();
			return null;
		}
	}
	
	public List<DisplayInfo> getDisplayInfoList(Integer displayInfoId) { //전시정보 리스트를 받아오는 get 메서드
		try {
			Map<String,Integer> params = Collections.singletonMap("displayInfoId", displayInfoId);
			return jdbc.query(SELECT_DISPLAY_INFO_LIST_BY_DISPLAY_INFO_ID, params, displayInfo_rowMapper);
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ReservationInfo> setReservationInfoListAsDisplayInfo(List<ReservationInfo> reservations){ //예약정보리스트를 인자로 받으면, 그 리스트를 돌면서(전시정보id를 이용하여) 알맞은 전시정보리스틀 집어넣는 매서드
		for(int i = 0; i < reservations.size(); i++)
		{
			int currentDisplayInfoId = reservations.get(i).getDisplayInfoId(); //예약정보리스트를 iterate하며 id를 순서대로 하나 꺼냄
			List<DisplayInfo> multiple_displayInfo = this.getDisplayInfoList(currentDisplayInfoId); //꺼낸 전시정보id로 알맞은 전시정보리스트를 획득한뒤 확인하여 multiple_displayInfo변수에 대입함
			reservations.get(i).setDisplayInfo(multiple_displayInfo); //예약정보리스트를 iterate하며 '현재 전시정보id에 맞는 전시정보리스트'를 대입하고 이를 모든 예약정보리스트를 돌면서 반복
		}
		return reservations; //반복문이 다 끝나서 reservations 예약정보 리스트의 모든 요소에 알맞은 '현재 전시정보id에 맞는 전시정보리스트'가 들어갔다면 리턴함
	}
	
	public List<ProductPrice> getProductPriceList(Integer displayInfoId) { //상품가격정보 리스트를 가져오는 매서드
		try {
			Map<String,Integer> params = Collections.singletonMap("displayInfoId", displayInfoId);
			return jdbc.query(SELECT_PRODUCT_PRICE_LIST_BY_DISPLAY_INFO_ID, params, productPrice_rowMapper);
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer insertReservationResponse(ReservationResponse reservationResponse) { //클라이언트가 작성한 예약정보를 데이터베이스에 삽입할때 사용할 매서드, 인자에따라 새로운 id값을 생성하여 리턴함
		Map<String, Object> parameters = new HashMap<String, Object>(); //요소들 담기위한 해시맵 생성
		parameters.put("product_id", reservationResponse.getProductId());
		parameters.put("display_info_id", reservationResponse.getDisplayInfoId());
		parameters.put("reservation_name", reservationResponse.getReservationName());
		parameters.put("reservation_tel", reservationResponse.getReservationTelephone());
		parameters.put("reservation_email", reservationResponse.getReservationEmail());
		parameters.put("reservation_date", reservationResponse.getReservationDate());
		parameters.put("cancel_flag", reservationResponse.isCancelYn());
		parameters.put("create_date", reservationResponse.getCreateDate());
		parameters.put("modify_date", reservationResponse.getModifyDate());
		parameters.put("transaction_flag", 0);
		return insertAction.executeAndReturnKey(parameters).intValue();
	}
	
	public int updateReservation(Integer reservationInfoId) { //데이터베이스의 예약 업데이트를 위한 매서드
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId); //정합성 확인을 위한 id 값 추가
		params.put("cancel_flag", CANCLE_FLAG); //취소유무 업데이트를 위해 맵에 관련 변수 추가
		return jdbc.update(UPDATE_RESERVATION_INFO_BY_RESERVATION_INFO_ID, params);
	}
	
	public ReservationResponse getReservationResponse(Integer reservationInfoId) { //예약정보id로 예약정보(클라이언트가 작성해서 데이터베이스에까지 들어간)를 얻는 매서드
		try {
			Map<String,Integer> params = Collections.singletonMap("reservationInfoId", reservationInfoId);
			return jdbc.queryForObject(SELECT_RESERVATION_RESPONSE_BY_RESERVATION_INFO_ID, params, reservationResponse_rowMapper);
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ReservationInfo getReservationInfo(Integer reservationInfoId) { //예약정보id로 예약정보 얻는 매서드. 이건 데이터 조작할때 쓰는게 아니라 get용도로 쓰는거기때문에 자료형이 단순히 ReservationInfo형임
		try {
			Map<String, Integer> params = Collections.singletonMap("reservationInfoId", reservationInfoId);
			return jdbc.queryForObject(SELECT_RESERVATION_INFO_BY_RESERVATION_INFO_ID, params, reservationInfo_rowMapper);
		}catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Product getProductByDisplayInfoId(int displayInfoId) {
		try {
			Map<String, Integer> params = Collections.singletonMap("displayInfoId", displayInfoId);
			return jdbc.queryForObject(SELECT_PRODUCT_BY_DISPLAY_INFO_ID, params, product_rowMapper);
		}catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public TransactionResponse selectTransactionFlagByReservationId(String reservationId) {
		try {
			Map<String, Integer> params = Collections.singletonMap("id", Integer.parseInt(reservationId));
			return jdbc.queryForObject(SELECT_TRANSACTION_FLAG_BY_RESERVATION_INFO_ID, params,transaction_rowMapper);
		}catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
