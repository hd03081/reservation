package kr.or.connect.reservation.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomUser extends User {

	private static final long serialVersionUID = 1L;
	
	private Member member;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	//Member와 권한 컬렉션 객체를 매개변수로 가진 CustomUser 생성자
	public CustomUser(Member member, Collection<? extends GrantedAuthority> authorities) {
		super(member.getUserId(),member.getUserPw(),authorities);
		
		this.member = member;
	}
	
	//Member 매개변수를 가진 CustomUser 생성자
	public CustomUser(Member member) {
		//java 스트림을 사용한 경우
		super(member.getUserId(),member.getUserPw(),member.getAuthList().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuthority())).collect(Collectors.toList()));
		
		/*
		//java 스트림을 사용하지 않은 경우
		List authorities = new ArrayList();
		
		for(MemberAuth auth : member.getAuthList()) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(auth.getAuth());
			authorities.add(authority);
		}
		*/
		
		this.member=member;
	}
	
	public Member getMember() {
		return member;
	}
	
	public int getUserNo() {
		return member.getUserNo();
	}
	
	public String getUserId() {
		return member.getUserId();
	}
	
}
