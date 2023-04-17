package kr.or.connect.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.dto.Promotion;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.UPDATE_RESERVATION_TRANSACTION_TO_COMPLETE_BY_RESERVATION_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationDaoSqls.UPDATE_RESERVATION_INFO_BY_RESERVATION_INFO_ID;
import static kr.or.connect.reservation.dao.ReservationMainDaoSqls.*; //sqls는 스태틱으로 불러와야됨

@Repository
public class ReservationMainDao {
	private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<Promotion> promotion_rowMapper = BeanPropertyRowMapper.newInstance(Promotion.class); //Promotion 로우맵
    private RowMapper<Category> category_rowMapper = BeanPropertyRowMapper.newInstance(Category.class); //Category 로우맵
    private RowMapper<Product> product_rowMapper = BeanPropertyRowMapper.newInstance(Product.class); //Product 로우맵
    
    public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

    public ReservationMainDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        					.withTableName("Product")
        					.usingGeneratedKeyColumns("id");
    }
    
    public List<Product> selectAllProductList(Integer start, Integer limit) {
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);
		params.put("limit", limit);
    return jdbc.query(SELECT_PAGING_ALLLIST, params, product_rowMapper);
    }
    
    public List<Product> selectDivideProductList(Integer categoryId, Integer start, Integer limit) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("start", start);
		params.put("limit", limit);
    return jdbc.query(SELECT_PAGING_DIVIDELIST, params, product_rowMapper);
    }
    
	public List<Promotion> selectPromotionList() {
		try {
		return jdbc.query(SELECT_PROMOTION_ITEMS, Collections.emptyMap(),promotion_rowMapper);
		} catch (EmptyResultDataAccessException e2) {
			e2.printStackTrace();
			return null;
		}
	}
	
	public List<Category> selectCategoryList() {
		try {
		return jdbc.query(SELECT_ITEMS_SORT_BY_CATEGORY, Collections.emptyMap(),category_rowMapper);
		} catch (EmptyResultDataAccessException e2) {
			e2.printStackTrace();
			return null;
			
		}
	}
	
	public Integer countAllProduct() {
		try {
			return (Integer)jdbc.queryForObject(COUNT_ALL_ITEMS, Collections.emptyMap(), Integer.class); //총갯수 표기단에는 정수형 데이터만 받아오면되므로 Integer 형변환
			} catch (EmptyResultDataAccessException e2) {
				e2.printStackTrace();
				return null;
			}
		
	}
	
	public Integer countAllProductSortbyCategory(Integer categoryId) {
		try {
			Map<String, Integer> params = Collections.singletonMap("category_id",categoryId); //키값 String, 밸류값 Integer를 가지는 맵 params 생성.
																							  //해당 싱글톤맵"params"는 {"category_id"=categoryId} 와 같음
			return (Integer)jdbc.queryForObject(COUNT_ALL_ITEMS_SORT_BY_CATEGORY,params, Integer.class);//params 에는 sql문의 where절에서 =:"category_id" 해당 구문이 받아먹을 categoryId가 밸류값으로 존재함
			} catch (EmptyResultDataAccessException e2) {
				e2.printStackTrace();
				return null;
			}
		
	}

	public int updateTransactionSuccess(String reserveId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", Integer.parseInt(reserveId));
		params.put("transaction_flag", 1); //결제완료
		return jdbc.update(UPDATE_RESERVATION_TRANSACTION_TO_COMPLETE_BY_RESERVATION_INFO_ID, params);
	}
	
}
