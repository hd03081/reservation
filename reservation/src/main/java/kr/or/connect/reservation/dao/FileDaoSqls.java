package kr.or.connect.reservation.dao;

public class FileDaoSqls {
	public static final String SELECT_FILE_INFO_LIST_BY_FILE_INFO_ID = "select id as fileId, file_name as fileName, save_file_name as saveFileName, content_type as contentType, delete_flag as deleteFlag, create_date as createDate, modify_date as modifyDate from file_info where id = :fileId";
}
