package kr.or.connect.reservation.security.jwt.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import kr.or.connect.reservation.constants.SecurityConstants;
import kr.or.connect.reservation.dao.MemberDao;
import kr.or.connect.reservation.dto.CustomUser;
import kr.or.connect.reservation.dto.Member;
import kr.or.connect.reservation.prop.ShopProperties;

public class JwtRequestFilter extends OncePerRequestFilter {
	
	private ShopProperties shopProperties;
	
	private final MemberDao memberDao;
	
	public JwtRequestFilter(ShopProperties shopProperties, MemberDao memberDao) {
		this.memberDao = memberDao;
		this.shopProperties=shopProperties;
		System.out.println("스프링 시큐리티 발현");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader(SecurityConstants.TOKEN_HEADER);
		/*
		if(isEmpty(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			if(request.getCookies()!=null) {
				Cookie[] cookies = request.getCookies();
				for(int i=0;i<cookies.length;i++) {
					if(cookies[i].getName().equals("token")) {
						logger.info("찾은 토큰 : "+cookies[1].toString());
						
						header = "{ \"Authorization\" : \""+cookies[i].getValue()+"\" }";
						
						//header = cookies[i].getValue();
					}
				}
			}
		}
		*/
		
		logger.info("최종판단 전 헤더 값 : "+header);
		//토큰이 헤더에 안담긴 경우
		if(isEmpty(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			if(request.getCookies()!=null){//헤더에 토큰은 없지만 쿠키에는 토큰이 있을 때
				Cookie[] cookies = request.getCookies();
				for(int i=0;i<cookies.length;i++) {
					if(cookies[i].getName().equals("token")) {//일반토큰 찾았을때
						logger.info("찾은 일반 토큰 : "+cookies[i].toString());
						
						//header = "{ \"Authorization\" : \""+cookies[i].getValue()+"\" }";
						
						header = cookies[i].getValue();
					}else if(cookies[i].getName().equals("kakao_token")) {//카카오토큰 찾았을때
						logger.info("찾은 카카오 토큰 : "+cookies[i].toString());
						
						//header = "{ \"Authorization\" : \""+cookies[i].getValue()+"\" }";
						
						header = "kakao"+cookies[i].getValue();
					}else if(cookies[i].getName().equals("naver_token")) {//네이버토큰 찾았을때
						logger.info("찾은 네이버 토큰 : "+cookies[i].toString());
						
						//header = "{ \"Authorization\" : \""+cookies[i].getValue()+"\" }";
						
						header = "naver"+cookies[i].getValue();
					}
				}
			}else {//헤더에도 토큰이없고 쿠키에도 토큰이 없을 때
				filterChain.doFilter(request, response);
				logger.info("토큰이 헤더에 제대로 안담김");
				return;
			}
		}else {//헤더에 토큰이 담긴경우는 그냥 통과
			logger.info("토큰이 헤더에 제대로 담김");
		}
		Authentication authentication = this.getAuthentication(header);
		
		//log.info("authentication : "+authentication);
		//log.info("authentication.getPrincipal() : "+authentication.getPrincipal());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}
	
	
	
	public HashMap<String, Object> getUserInfo (String access_Token) {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            
            userInfo.put("accessToken", access_Token);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userInfo;
    }
	
	public HashMap<String, Object> getUserInfoNaver (String access_Token) {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://openapi.naver.com/v1/nid/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();

            String nickname = response.getAsJsonObject().get("nickname").getAsString();
            String email = response.getAsJsonObject().get("email").getAsString();
            
            userInfo.put("accessToken", access_Token);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userInfo;
    }
	
	private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
		
		if(isNotEmpty(tokenHeader)) {
			if(tokenHeader.contains("kakao")) {//카카오인경우 토큰검사
				
				HashMap<String, Object> userInfo = getUserInfo(tokenHeader.replace("kakao", ""));
				
				String userId = "kakao_"+(String)userInfo.get("nickname");
				int userNo = memberDao.readByUserId(userId).getUserNo();
				
				List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
				
				if(isNotEmpty(userId)) {
					/*
					UserDetails userDetails = new CustomUser(userId,"",authorities); //CustomUser?
					*/
					
					//CustomUser 객체 생성
					Member member = new Member();
					member.setUserNo(userNo);
					member.setUserId(userId);
					member.setUserPw("");
					
					UserDetails userDetails = new CustomUser(member,authorities);
					
					return new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
				}
			}else if(tokenHeader.contains("naver")) {
				HashMap<String, Object> userInfo = getUserInfoNaver(tokenHeader.replace("naver", ""));
				
				String userId = "naver_"+(String)userInfo.get("nickname");
				int userNo = memberDao.readByUserId(userId).getUserNo();
				
				List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
				
				if(isNotEmpty(userId)) {
					/*
					UserDetails userDetails = new CustomUser(userId,"",authorities); //CustomUser?
					*/
					
					//CustomUser 객체 생성
					Member member = new Member();
					member.setUserNo(userNo);
					member.setUserId(userId);
					member.setUserPw("");
					
					UserDetails userDetails = new CustomUser(member,authorities);
					
					return new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
				}
			}
			
			try {//일반토큰검사
				byte[] signingKey = this.shopProperties.getSecretKey().getBytes();
				logger.info(tokenHeader.replace("Bearer0", ""));
				Jws<Claims> parsedToken = Jwts.parser()
						.setSigningKey(signingKey)
						.parseClaimsJws(tokenHeader.replace("Bearer0", ""));
				
				Claims claims = parsedToken.getBody();
				
				String userNo = claims.get("uno").toString();
				String userId = (String)claims.get("uid");
				
				List<SimpleGrantedAuthority> authorities = ((List<?>)claims.get("rol"))
						.stream()
						.map(authority -> new SimpleGrantedAuthority((String)authority))
						.collect(Collectors.toList());
				
				if(isNotEmpty(userId)) {
					/*
					UserDetails userDetails = new CustomUser(userId,"",authorities); //CustomUser?
					*/
					
					//CustomUser 객체 생성
					Member member = new Member();
					member.setUserNo(Integer.parseInt(userNo));
					member.setUserId(userId);
					member.setUserPw("");
					
					UserDetails userDetails = new CustomUser(member,authorities);
					
					return new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
				}
			} catch (ExpiredJwtException exception) {
				logger.info("Request to parse expired JWT : {} failed : {}"+tokenHeader+exception.getMessage());
				exception.printStackTrace();
			} catch (UnsupportedJwtException exception) {
				logger.info("Request to parse unsupported JWT : {} failed : {}"+tokenHeader+exception.getMessage());
				exception.printStackTrace();
			} catch (MalformedJwtException exception) {
				logger.info("Request to parse invalid JWT : {} failed : {}"+tokenHeader+exception.getMessage());
				exception.printStackTrace();
				logger.info(shopProperties.getSecretKey());
			} catch (IllegalArgumentException exception) {
				logger.info("Request to parse empty or null JWT : {} failed : {}"+tokenHeader+exception.getMessage());
				exception.printStackTrace();
			}
		}
		return null;
	}
	
	private boolean isEmpty(final CharSequence cs) {
		
		return cs==null||cs.length()==0;
		
	}
	
	private boolean isNotEmpty(final CharSequence cs) {
		
		return !isEmpty(cs);
		
	}
}
