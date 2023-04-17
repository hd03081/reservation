package kr.or.connect.reservation.dao;

public class ReviewWriteDaoSqls {
	public static final String SELECT_RESERVATION_INFO_LIST_BY_RESERVATION_INFO_ID_FOR_REVIEW = "SELECT RI.id as reservationInfoId, RUC.id as commentId from reservation_info RI join reservation_user_comment RUC on (RUC.reservation_info_id = RI.id) where RI.id = :reservationInfoId";
	public static final String SELECT_FILE_INFO_LIST_BY_FILE_NAME = "SELECT FI.file_name as fileName from file_info FI where FI.file_name = :fileName";
}
