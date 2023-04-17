package kr.or.connect.reservation.security.handler;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListeners {
	
	@EventListener
	public void handleAuthenticationEvent(AbstractAuthenticationEvent event) {
		//log.info("handleAuthenticationEvent " + event);
	}
	
	@EventListener
	public void handleBadCredentials(AuthenticationFailureBadCredentialsEvent event) {
		//log.info("handleBadCredentials");
	}
	
	@EventListener
	public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
		//log.info("handleAuthenticationSuccess");
	}
}
