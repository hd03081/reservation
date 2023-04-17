package kr.or.connect.reservation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Configuration
@ComponentScan(basePackages = { "kr.or.connect.reservation.dao",  "kr.or.connect.reservation.service", "kr.or.connect.reservation.prop" })
@Import({ DBConfig.class,SecurityConfig.class })
public class ApplicationConfig extends AbstractSecurityWebApplicationInitializer {
	 
}
