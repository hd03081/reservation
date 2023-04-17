package kr.or.connect.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ReservationPrice;

import static kr.or.connect.reservation.dao.ReservationPriceDaoSqls.SELECT_RESERVATION_PRICE_LIST_BY_RESERVATION_INFO_ID;

@Repository
public class ReservationPriceDao {
	
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	private RowMapper<ReservationPrice> reservationPrice_rowMapper = BeanPropertyRowMapper.newInstance(ReservationPrice.class); //ReservationPrice 로우맵
	
	public ReservationPriceDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        					.withTableName("reservation_info_price")
        					.usingGeneratedKeyColumns("id");
    }
	
	public Integer insertReservationPrice(ReservationPrice reservationPrice) { //클라이언트가 주문한 상품의 갯수, 종류, 가격을 확인하여 담고. 예약정보 데이터베이스에 insert하기위한 매서드
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("reservation_info_id", reservationPrice.getReservationInfoId());
		parameters.put("product_price_id", reservationPrice.getProductPriceId());
		parameters.put("count", reservationPrice.getCount());
		return insertAction.executeAndReturnKey(parameters).intValue();
	}
	
	public List<ReservationPrice> getReservationPrice(int reservationInfoId){ //주문내역(상품갯수,종류,가격) 이라 부를수 있을만한걸 얻는 매서드
		Map<String,Integer> params = Collections.singletonMap("reservationInfoId", reservationInfoId);
		return jdbc.query(SELECT_RESERVATION_PRICE_LIST_BY_RESERVATION_INFO_ID, params, reservationPrice_rowMapper);
	}
	
}
