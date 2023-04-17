package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_RESERVATION_INFO_LIST_BY_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_RESERVATION_RESPONSE_BY_RESERVATION_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.UPDATE_RESERVATION_INFO_BY_RESERVATION_INFO_ID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.OrderDataDTO;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationResponse;

@Repository
public class OrderDAO {
	final static Integer CANCLE_FLAG = 1; //취소상태를 표현하기위한 전역변수
	
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	private RowMapper<ReservationInfo> reservationInfo_rowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class); //ReservationInfo 로우맵
	private RowMapper<DisplayInfo> displayInfo_rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class); //DisplayInfo 로우맵
	private RowMapper<ProductPrice> productPrice_rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class); //ProductPrice 로우맵
	private RowMapper<ReservationResponse> reservationResponse_rowMapper = BeanPropertyRowMapper.newInstance(ReservationResponse.class); //ReservationResponse 로우맵
	private RowMapper<OrderDataDTO> orderData_rowMapper = BeanPropertyRowMapper.newInstance(OrderDataDTO.class);
	
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public OrderDAO(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        					.withTableName("reservation_info") //.withTableName("DisplayInfo")
        					.usingGeneratedKeyColumns("id");
    }
	
	public boolean clearOrderData(Integer orderNo) { //데이터베이스의 예약 업데이트를 위한 매서드
		int flag = 0;
		Map<String, Integer> params = new HashMap<>();
		params.put("orderNo", orderNo); //정합성 확인을 위한 id 값 추가
		try {
			jdbc.update("DELETE FROM order_data WHERE orderNo = :orderNo", params);
			flag = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag!=0;
	}
	
	public boolean insertOrderData(OrderDataDTO dto) {
		int flag = 0;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("orderNo", dto.getOrderNo());
			parameters.put("cardId", dto.getCartId());
			parameters.put("bookId", dto.getBookId());
			parameters.put("bookName", dto.getBookName());
			parameters.put("unitPrice", dto.getUnitPrice());
			parameters.put("cnt", dto.getCnt());
			parameters.put("sumPrice", dto.getSumPrice());
			insertAction.executeAndReturnKey(parameters).intValue();
			flag = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag!=0;
	}
	
	public List<OrderDataDTO> selectAllOrderData(String orderNo) {
		try {
			Map<String,String> params = Collections.singletonMap("orderNo", orderNo); //키값 String, 밸류값 String를 가지는 맵 params 생성.
																								   //해당 싱글톤맵"params"는 {"reservationEmail"=reservationEmail} 와 같음 
			return jdbc.query("SELECT * FROM order_data WHERE orderNo = :orderNo", params, orderData_rowMapper); //params 에는 sql문의 where절에서 =:"reservationEmail" 해당 구문이 받아먹을 reservationEmail가 밸류값으로 존재함
		}catch (EmptyResultDataAccessException e){ //예외처리
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer getTotalPrice(String orderNo) {
		try {
			RowMapper<Integer> sum = BeanPropertyRowMapper.newInstance(Integer.class);
			Map<String,String> params = Collections.singletonMap("orderNo", orderNo);
			return jdbc.queryForObject("SELECT SUM(sumPrice) as totalPrice FROM order_data WHERE orderNo=?", params, sum);
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	
	public void clearOrderInfo(String orderNo) {
		System.out.println("오더노"+orderNo);
		//주문번호 기준으로 주문 정보 데이터 삭제, 중복 등록 방지
		String sql = "DELETE FROM order_info WHERE orderNo=?";
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1,orderNo);
			pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("clearOrderInfo() 에러:"+e);
		}
	}
	public boolean insertOrderInfo(OrderInfoDTO dto) {
		int flag = 0;
		String sql = "INSERT INTO order_info VALUES (?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?)";
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1,dto.getOrderNo());
				pstmt.setString(2,dto.getMemberId());
				pstmt.setString(3, dto.getOrderName());
				pstmt.setString(4, dto.getOrderTel());
				pstmt.setString(5, dto.getOrderEmail());

				pstmt.setString(6, dto.getReceiveName());
				pstmt.setString(7, dto.getReceiveTel());
				pstmt.setString(8, dto.getReceiveAddress());
				pstmt.setInt(9, dto.getPayAmount());
				pstmt.setString(10, dto.getPayMethod());
				
				pstmt.setString(11, dto.getCarryNo());;
				pstmt.setString(12, "orderFail");
				pstmt.setString(13, dto.getDatePay());
				pstmt.setString(14, dto.getDateCarry());
				
				pstmt.setString(15,dto.getDateDone());
				
				flag = pstmt.executeUpdate();
				
		}catch(Exception e) {
			System.out.println("clearOrderInfo() 에러:"+e);
		}
		return flag!=0;
	}
	
	public OrderInfoDTO selectOrderInfo(String orderNo) {
		String sql = "SELECT * FROM order_info WHERE orderNo=?";
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
				pstmt.setString(1, orderNo);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					OrderInfoDTO dto = new OrderInfoDTO();
					dto.setOrderNo(rs.getString(1));
					dto.setMemberId(rs.getString(2));
					dto.setOrderName(rs.getString(3));
					dto.setOrderTel(rs.getString(4));
					dto.setOrderEmail(rs.getString(5));

					dto.setReceiveName(rs.getString(6));
					dto.setReceiveTel(rs.getString(7));
					dto.setReceiveAddress(rs.getString(8));
					dto.setPayAmount(rs.getInt(9));
					dto.setPayMethod(rs.getString(10));
					
					dto.setCarryNo(rs.getString(11));
					dto.setOrderStep(rs.getString(12));
					dto.setDateOrder(rs.getString(13));
					dto.setDatePay(rs.getString(14));
					dto.setDateCarry(rs.getString(15));
					
					dto.setDateDone(rs.getString(16));
					return dto;
				}
				
		}catch(Exception e) {
			System.out.println("getOrderinfo() 에러:"+e);
		}
		return null;
	}
	
	public String getOrderProductName(String orderNo) {
		String orderProductName = null;
		int orderProductCnt = 0;
		String sql = "SELECT bookName FROM order_data WHERE orderNo='"+orderNo+"'";
		System.out.println(sql);
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {
			while(rs.next()) {	
				if(orderProductCnt==0) {
					orderProductName = rs.getString("bookName");
					System.out.println(orderProductName);
				}
				orderProductCnt++;
			}
			orderProductName += "외 "+(orderProductCnt-1)+"건";
		}catch(Exception ex) {
			System.out.println("selectAllOrderData()에러: "+ex);
		}
		return orderProductName;
	}
	public boolean updateOrderInfoWhenProcessSuccess(OrderInfoDTO dto) {
		int flag =0;
		String sql = "UPDATE order_info SET payMethod = ?, orderStep = ?, datePay = now() WHERE orderNo = ?";
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1,dto.getPayMethod());
			pstmt.setString(2,dto.getOrderStep());
			pstmt.setString(3,dto.getOrderNo());
			flag = pstmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("updateOrderInfoWhenProcessSuccess()에러: "+ex);
		}
		return flag==1;
	}
	
	*/

}
