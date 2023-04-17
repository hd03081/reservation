package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReviewWriteDaoSqls.SELECT_RESERVATION_INFO_LIST_BY_RESERVATION_INFO_ID_FOR_REVIEW;
import static kr.or.connect.reservation.dao.ReviewWriteDaoSqls.SELECT_FILE_INFO_LIST_BY_FILE_NAME;

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
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentResponse;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationResponse;

@Repository
public class ReviewWriteDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	private RowMapper<CommentResponse> commentResponse_rowMapper = BeanPropertyRowMapper.newInstance(CommentResponse.class); //CommentResponse 로우맵
	private RowMapper<Comment> comment_rowMapper = BeanPropertyRowMapper.newInstance(Comment.class); //CommentResponse 로우맵
	private RowMapper<FileInfo> fileInfo_rowMapper = BeanPropertyRowMapper.newInstance(FileInfo.class); //CommentResponse 로우맵
	
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	} //이거 왜쓴거지?
	
	public ReviewWriteDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        					.withTableName("reservation_user_comment") //.withTableName("reservation_user_comment_image")
        					.usingGeneratedKeyColumns("id");
    }
	
	public Integer insertCommentResponse(CommentResponse commentResponse) { //클라이언트가 작성한 리뷰를 데이터베이스에 삽입할때 사용할 매서드, 인자에따라 새로운 id값을 생성하여 리턴함
		Map<String, Object> parameters = new HashMap<String, Object>(); //요소들 담기위한 해시맵 생성
		parameters.put("comment", commentResponse.getComment());
		parameters.put("product_id", commentResponse.getProductId());
		parameters.put("reservation_info_id", commentResponse.getReservationInfoId());
		parameters.put("score", commentResponse.getScore());
		parameters.put("create_date", commentResponse.getCreateDate());
		parameters.put("modify_date", commentResponse.getModifyDate());
		return insertAction.executeAndReturnKey(parameters).intValue();
	}
	
	public List<Comment> getReservationInfo(Integer reservationInfoId){
		try {
			Map<String,Integer> params = Collections.singletonMap("reservationInfoId", reservationInfoId);
			return jdbc.query(SELECT_RESERVATION_INFO_LIST_BY_RESERVATION_INFO_ID_FOR_REVIEW, params, comment_rowMapper);
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	}
	
	//fileName을 인자로받아서 데이터베이스의 file_info 테이블에 접근 후 일치하는게 있나 확인하는 매서드
	public List<FileInfo> getFileName(String fileName){
		try {
			Map<String, String> params = Collections.singletonMap("fileName", fileName);
			return jdbc.query(SELECT_FILE_INFO_LIST_BY_FILE_NAME, params, fileInfo_rowMapper);
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	}
	
}
