package kr.or.connect.reservation.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import kr.or.connect.reservation.dao.MemberDao;
import kr.or.connect.reservation.prop.ShopProperties;
import kr.or.connect.reservation.security.handler.CustomAccessDeniedHandler;
import kr.or.connect.reservation.security.handler.CustomAuthenticationEntryPoint;
import kr.or.connect.reservation.security.jwt.filter.JwtAuthenticationFilter;
import kr.or.connect.reservation.security.jwt.filter.JwtRequestFilter;
import kr.or.connect.reservation.security.service.CustomUserDetailsService;

/* 스프링 시큐리티 최신버전 들어서는 WebSecurityConfigurerAdapter를 extends 하여 사용하는것을 지원하지 않는다. 대신 SecurityFilterChain 빈을 생성한다 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true) //메서드 보안용 어노테이션,메서드 실행전의 접근정책 설정용 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	ShopProperties shopProperties;
	
	@Autowired
	MemberDao memberDao;
	
	/*
	//JDBC 기반 인증 스프링 시큐리티
	@Autowired
	public void initialize(AuthenticationManagerBuilder auth,DataSource dataSource) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder());
	}
	*/
	
	
	@Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
        .antMatchers("/img/**", "/js/**", "/css/**");
    }
    
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//CustomUserDetailsService 빈을 인증 제공자에 지정한다
		auth.userDetailsService(customUserDetailsService())
		.passwordEncoder(passwordEncoder());
		
		
		/*
		//스프링 시큐리티가 원하는 결과를 반환하는 쿼리 작성
		String query1 = "SELECT user_id, user_pw, enabled FROM member WHERE user_id = ?";
		String query2 = "SELECT b.user_id, a.auth FROM member_auth a, member b WHERE a.user_no = b.user_no AND b.user_id = ?";
		
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(query1)
		.authoritiesByUsernameQuery(query2)
		.passwordEncoder(passwordEncoder());
		*/
		
		/*
		auth.inMemoryAuthentication()
		.passwordEncoder(passwordEncoder())
		.withUser("alex").password("$2a$12$6FOiv9dZY05vhTR2a9x4zO6IMFsFhWLG085AxYZSExuYHGMsAEHJe").roles("MEMBER")
		.and()
		.withUser("jade").password("$2a$12$6FOiv9dZY05vhTR2a9x4zO6IMFsFhWLG085AxYZSExuYHGMsAEHJe").roles("MEMBER","ADMIN")
		.and()
		.withUser("admin").password("$2a$12$6FOiv9dZY05vhTR2a9x4zO6IMFsFhWLG085AxYZSExuYHGMsAEHJe").roles("ADMIN");
		*/
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("스프링 시큐리티 발현");
		http.formLogin().disable()
		.httpBasic().disable();
		
		http.csrf().disable();
		
		http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterAt(new JwtAuthenticationFilter(authenticationManager(),shopProperties),
				UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(jwtRequestFilter(shopProperties), UsernamePasswordAuthenticationFilter.class);
		
		
		//접근 제한 설정 - URI 패턴으로 접근 제한
		//접근 제한 설정을 한 경우 ajax 등으로 요청이 올때 그 요청의 헤더에 있는 JWT토큰을 판독하여 검증을 하게된다
		//그러므로 요청시 요청헤더에 토큰을 포함시켜야만 한다
		//웹 경로 보안 설정 1
		// We don't need CSRF for this example
		http
        .authorizeRequests()
        .antMatchers("/reservePage").hasRole("MEMBER")
        .anyRequest().permitAll();
		/*
		http.authorizeRequests()
		.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		.antMatchers("/").permitAll()
		.antMatchers("/searchers").permitAll()
		.antMatchers("/searchers/**").access("request.method == 'GET' ? permitAll : hasAnyRole('MEMBER','ADMIN')")
		.antMatchers("/favorites").permitAll()
		.antMatchers("/favorites/**").access("request.method == 'GET' ? permitAll : hasAnyRole('MEMBER','ADMIN')")
		.antMatchers("/codes/**").permitAll()
		.antMatchers("/users/**").permitAll()
		.antMatchers("/codegroups/**").hasRole("ADMIN")
		.antMatchers("/codedetails/**").hasRole("ADMIN")
		//GET요청인 경우엔 permitAll 이외의 요청(POST) 등일 경우 has(Any)Role 조건을 따라간다
		.antMatchers("/boards/**").access("request.method == 'GET' ? permitAll : hasAnyRole('MEMBER','ADMIN')")
		.antMatchers("/notices/**").access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
		//상품 관리 웹 경로 보안 지정
		.antMatchers("/items/**").access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
		//공개 자료실실 웹 경로 보안 지정
		.antMatchers("/pds/**").access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
		.anyRequest().authenticated();
		*/
	    
		//접근 거부 처리자 등록 + 사용자 정의 엔트리 포인트트객체를 지정
		http.exceptionHandling()
		.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
		.accessDeniedHandler(accessDeniedHandler());
		
		// CORS 지원 기능 활성화 방법
		// 1. WebMvcConfigurer 인터페이스 활용 - SecurityConfig 클래스에서 http.cors(); 매서드 필요 , WebMvcConfigurer 를 구현한 클래스 필요 (글로벌 설정)
		// 2. @CrossOrigin 어노테이션 활용 - SecurityConfig 클래스에서 http.cors(); 매서드 필요 , @CrossOrigin 어노테이션을 클래스 또는 매서드 단위에 설정 필요
		// 2a. @CrossOrigin 어노테이션은 클래스, 매서드 단위에서밖에 못 쓰기 때문에 ajax login은 컨트롤러로 구현한 경우에만 CORS를 지원한다
		// 3. CorsConfigurationSource 타입 빈 정의 - SecurityConfig 클래스에서 http.cors(); 매서드 필요 , CorsConfigurationSource 빈을 SecurityConfig 에 등록 필요
		http.cors();
		
		/*
		//접근 제한 설정 - access() 메서드와 SpEL을 사용하여 접근 정책을 기술한다
		//웹 경로 보안 설정 2
		http.authorizeRequests()
		.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		.antMatchers("/").access("permitAll")
		.antMatchers("/codes/**").access("permitAll")
		.antMatchers("/users/**").access("permitAll")
		.antMatchers("/codegroups/**").access("hasRole('ADMIN')")
		.antMatchers("/codedetails/**").access("hasRole('ADMIN')")
		.antMatchers("/boards/**").access("hasRole('MEMBER')")
		.antMatchers("/notices/**").access("hasRole('ADMIN')")
		.anyRequest().authenticated();
		*/
	}
	/*
	@Bean	
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	*/
	
	private JwtRequestFilter jwtRequestFilter(ShopProperties shopProperties) 
    {
        return new JwtRequestFilter(shopProperties,memberDao);
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:8080");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		config.setExposedHeaders(Arrays.asList("Authorization","Content-Disposition"));
		
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	
}
	