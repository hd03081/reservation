package kr.or.connect.reservation.dao;

public class MemberDaoSqls {
	//user_id 별 member 정보
	public static final String SELECT_MEMBER_BY_USERID = "SELECT mem.user_no,"
			+ "	mem.user_id as userId,"
			+ "	user_pw as userPw,"
			+ "	user_name as userName,"
			+ "	job ,"
			+ "	coin,"
			+ "	enabled,"
			+ "	reg_date as regDate,"
			+ "	upd_date as updDate,"
			+ " email,"
			+ "	authority"
			+ "	FROM member mem LEFT OUTER JOIN authorities authority ON mem.user_no = authority.user_no"
			+ "	WHERE mem.user_id = :userId";
	
	public static final String SELECT_MEMBER_BY_USERNO = "SELECT mem.user_no,"
			+ "	mem.user_id as userId,"
			+ "	user_pw as userPw,"
			+ "	user_name as userName,"
			+ "	job ,"
			+ "	coin,"
			+ "	enabled,"
			+ "	reg_date as regDate,"
			+ "	upd_date as updDate,"
			+ " email,"
			+ "	authority"
			+ "	FROM member mem LEFT OUTER JOIN authorities authority ON mem.user_no = authority.user_no"
			+ "	WHERE mem.user_no = :userNo";
}
