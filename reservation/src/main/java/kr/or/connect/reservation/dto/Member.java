package kr.or.connect.reservation.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Member {
	private int userNo;
	
	private String userId;
	
	private String userPw;
	
	private String userName;
	
	private String job;
	private int coin;
	
	private boolean enabled;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime regDate;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime updDate;
	
	private int tuCount;
	
	private Timestamp tuDate;	
	
	private String authority;//memberAuth의 auth이자 권한이 1개일때의 전용 변수
	
	private List<MemberAuth> authList; //자바컬렉션 사용	

	private String email;
	
	private String zipcode;
	
	private String address01;
	
	private String address02;
	
	public Member() {
		
	}
	
	public Member(String userId, String userPw, String email, String zipcode, String address01, String address02) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.email = email;
		this.zipcode = zipcode;
		this.address01 = address01;
		this.address02 = address02;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress01() {
		return address01;
	}

	public void setAddress01(String address01) {
		this.address01 = address01;
	}

	public String getAddress02() {
		return address02;
	}

	public void setAddress02(String address02) {
		this.address02 = address02;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
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

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	public LocalDateTime getUpdDate() {
		return updDate;
	}

	public void setUpdDate(LocalDateTime updDate) {
		this.updDate = updDate;
	}

	public int getTuCount() {
		return tuCount;
	}

	public void setTuCount(int tuCount) {
		this.tuCount = tuCount;
	}

	public Timestamp getTuDate() {
		return tuDate;
	}

	public void setTuDate(Timestamp tuDate) {
		this.tuDate = tuDate;
	}

	public List<MemberAuth> getAuthList() {
		return authList;
	}

	public void setAuthList(List<MemberAuth> authList) {
		this.authList = authList;
	}

	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userId=" + userId + ", userPw=" + userPw + ", userName=" + userName
				+ ", job=" + job + ", coin=" + coin + ", enabled=" + enabled + ", regDate=" + regDate + ", updDate="
				+ updDate + ", tuCount=" + tuCount + ", tuDate=" + tuDate + ", authority=" + authority + ", authList=" + authList
				+ ", email=" + email + ", zipcode=" + zipcode + ", address01=" + address01 + ", address02=" + address02
				+ "]";
	}



	
	
}
