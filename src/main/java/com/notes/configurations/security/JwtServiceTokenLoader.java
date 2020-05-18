package com.notes.configurations.security;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.notes.services.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * An ApplicationPreparedEvent is sent before the refresh is started and
 * after bean definitions have been loaded.
 * 
 * @author Jovani Brasil
 *
 */
@Profile({ "dev", "prod", "stage" })
@Component
@RequiredArgsConstructor
public class JwtServiceTokenLoader implements ApplicationListener<ApplicationPreparedEvent> {

	private final AuthService authService;
	
	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		getServiceToken();
	}
	
	/**
	 * Loads the service token that is necessary for authentication.
	 * 
	 * @return
	 */
	@Bean(name = "serviceToken")
	public Token getServiceToken() {
		return new Token(authService.getServiceToken());
	}

}
