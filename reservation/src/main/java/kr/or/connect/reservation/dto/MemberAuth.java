package kr.or.connect.reservation.dto;

public class MemberAuth {
	private int userNo;
	private String userId;
	private String authority;
	
	
	public MemberAuth(int userNo, String userId, String authority) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.authority = authority;
	}
	public MemberAuth(int userNo, String userId) {
		super();
		this.userNo = userNo;
		this.userId = userId;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public String toString() {
		return "MemberAuth [userNo=" + userNo + ", userId=" + userId + ", authority=" + authority + "]";
	}
	
	
	
	
}
