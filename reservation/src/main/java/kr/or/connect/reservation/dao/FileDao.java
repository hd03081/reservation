package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.FileDaoSqls.SELECT_FILE_INFO_LIST_BY_FILE_INFO_ID;

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

import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.CommentResponse;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.dto.ReservationPrice;

@Repository
public class FileDao {
	
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	private RowMapper<FileInfo> fileInfo_rowMapper = BeanPropertyRowMapper.newInstance(FileInfo.class); //FileInfo 로우맵
	
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	} //이거 왜쓴거지?
	
	public FileDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        					.withTableName("file_info") //.withTableName("reservation_user_comment_image")
        					.usingGeneratedKeyColumns("id");
    }
	
	public Integer insertFileInfo(CommentImage commentImage) { //file_info 테이블에 데이터 추가하는 dao
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("file_name", commentImage.getFileName());
		parameters.put("save_file_name", commentImage.getSaveFileName());
		parameters.put("content_type", commentImage.getContentType());
		parameters.put("delete_flag", commentImage.isDeleteFlag());
		parameters.put("create_date", commentImage.getCreateDate());
		parameters.put("modify_date", commentImage.getModifyDate());
		return insertAction.executeAndReturnKey(parameters).intValue();
	}
	
	public FileInfo getFileInfo(int fileId){ //fileInfo를 얻는 매서드
		Map<String,Integer> params = Collections.singletonMap("fileId", fileId);
		return jdbc.queryForObject(SELECT_FILE_INFO_LIST_BY_FILE_INFO_ID, params, fileInfo_rowMapper);
	}

}
