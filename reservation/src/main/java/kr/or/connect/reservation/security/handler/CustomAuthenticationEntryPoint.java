package kr.or.connect.reservation.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.connect.reservation.dto.ApiErrorInfo;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("Application/json:charset=UTF-8");
		
		System.out.println("뭐가 들어오긴하나?");
		
		ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
		if(InsufficientAuthenticationException.class == authException.getClass()) {
			apiErrorInfo.setMessage("Not Logined!!!");
			System.out.println("Not Logined!!!");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인을 해주세요'); location.href='./bookinglogin';</script>");
			out.flush();
		} else {
			apiErrorInfo.setMessage("Bad Request!!!");
			System.out.println("Bad Request!!!");
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		
		String jsonString = objectMapper.writeValueAsString(apiErrorInfo);
		response.getWriter().write(jsonString);
	}
	
}
