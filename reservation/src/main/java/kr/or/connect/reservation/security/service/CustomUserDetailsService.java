package kr.or.connect.reservation.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.or.connect.reservation.dao.MemberDao;
import kr.or.connect.reservation.dto.CustomUser;
import kr.or.connect.reservation.dto.Member;

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberDao memberDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//log.info("Load User By Username : " + username);
		
		//username 은 사용자명이 아니라 사용자 아이디를 의미한다.
		Member member = memberDao.readByUserId(username);
		System.out.println(member.toString());
		//log.info("queried by member mapper:"+member);
		return member == null ? null : new CustomUser(member);
	}
	
	
}
