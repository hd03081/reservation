package kr.or.connect.reservation.dao;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.CommentResponse;

@Repository
public class CommentImageDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	private RowMapper<CommentImage> commentImage_rowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class); //CommentImage 로우맵
	private RowMapper<CommentResponse> commentResponse_rowMapper = BeanPropertyRowMapper.newInstance(CommentResponse.class); //CommentResponse 로우맵
	
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	} //이거 왜쓴거지?
	
	public CommentImageDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        					.withTableName("reservation_user_comment_image") //.withTableName("reservation_user_comment_image")
        					.usingGeneratedKeyColumns("id");
    }
	
	public Integer insertCommentImage(CommentResponse commentResponse, CommentImage commentImage) { //클라이언트가 주문한 상품의 갯수, 종류, 가격을 확인하여 담고. 예약정보 데이터베이스에 insert하기위한 매서드
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("reservation_info_id", commentResponse.getReservationInfoId());
		parameters.put("reservation_user_comment_id", commentResponse.getCommentId());
		parameters.put("file_id", commentImage.getFileId());
		return insertAction.executeAndReturnKey(parameters).intValue();
	}

}
