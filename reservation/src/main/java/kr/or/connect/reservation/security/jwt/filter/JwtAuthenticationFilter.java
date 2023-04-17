package kr.or.connect.reservation.security.jwt.filter;



import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.or.connect.reservation.constants.SecurityConstants;
import kr.or.connect.reservation.dto.CustomUser;
import kr.or.connect.reservation.prop.ShopProperties;

//사용자 정의 인증 필터 클래스
//사용자 인증에 직접 사용되며 URL에서 사용자 아이디와 비밀번호 매개변수를 파악하고 이를 확인하기 위해 스프링의 인증관리자를 호출한다
//사용자 아이디와 비밀번호가 올바르면 필터는 JWT토큰을 만들고 HTTP Authorization 헤더에 반환한다

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	private ObjectMapper objectMapper = new ObjectMapper();
	private ShopProperties shopProperties;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ShopProperties shopProperties) {
		this.authenticationManager = authenticationManager;
		this.shopProperties = shopProperties;
		setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
		System.out.println("스프링 시큐리티 발현");
	}
	
	//전달받은 사용자 아이디와 비밀번호를 가지고 인증을 시도
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String userId = "init-userId";
		String password = "init-password";
		
		userId = request.getParameter("userId");
		password = request.getParameter("password");
		System.out.println("userId="+userId);
		System.out.println("password="+password);
		
		/*
		ServletInputStream inputStream = null;
		try {
			inputStream = request.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String messageBody = null;
		try {
			messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("messageBody = " + messageBody);
		
        try {
			JsonAjaxUser user = objectMapper.readValue(messageBody, JsonAjaxUser.class);
			
			username = user.getUsername();
			password = user.getPassword();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//null이라서 리턴이 안됨, 왜 필터로 값을 못읽지?
		*/
		
		//log.info("username = " + username + " password = " + password);
		
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userId,password);
		
		return authenticationManager.authenticate(authenticationToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
		logger.info("successfulAuthentication authentication = " + authentication);
		logger.info("successfulAuthentication authentication.getPrincipal() = " + authentication.getPrincipal());
		
		/*
		User user = (User)authentication.getPrincipal();
		*/
		
		CustomUser user = ((CustomUser)authentication.getPrincipal());
		
		/*
		String username = user.getUsername();
		*/
		
		int userNo = user.getUserNo();
		String userId = user.getUserId();
		
		List<String> roles = user.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	
		logger.info("userId = " + userId + " roles = " + roles);
		
		//String token = username + "_" + roles;
		
		//회원번호를 포함한 jwt 생성으로 업그레이드
		String token = createToken(userNo,userId,roles);
		logger.info("token : "+token);
		response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token.toString());
		logger.info("COMPLETE-TOKEN = "+response.getHeader(SecurityConstants.TOKEN_HEADER));
	}
	
	private String createToken(int userNo,String userId,List<String> roles) {
		byte[] signingKey = shopProperties.getSecretKey().getBytes();
		
		String token = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(signingKey),SignatureAlgorithm.HS512)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
				.setExpiration(new Date(System.currentTimeMillis()+864000000))
				.claim("uno", userNo)
				.claim("uid", userId)
				.claim("rol", roles)
				.compact();
				
		return token;
	}
	
}
