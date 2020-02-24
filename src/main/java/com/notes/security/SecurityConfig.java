package com.notes.security;

import com.notes.services.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationEntryPoint unauthorizedHandler;
	private final AuthService authClient;

	public SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, AuthService authClient) {
		this.unauthorizedHandler = unauthorizedHandler;
		this.authClient = authClient;
	}

	/**
	 * Filter used when the application intercepts a requests.
	 */
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter(this.authClient);
	}
	
	@Bean
	public ExceptionHandlerFilter exceptionHandlerFilterBean() throws Exception {
		return new ExceptionHandlerFilter();
	}

	/**
	 * Configure request authorization
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
				
		http
			.csrf().disable() // disable csrf (cross-site request forgery) 
			.cors()
			.and()
			.addFilterBefore(authenticationTokenFilterBean(),  BasicAuthenticationFilter.class)
			.addFilterBefore(exceptionHandlerFilterBean(), JwtAuthenticationTokenFilter.class)
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler) // set authentication error
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // set session police stateless
			.and()
			.authorizeRequests()
			.antMatchers("/notebooks", "/notebooks/**", "/colorpallet/**", "/notes", "/notes/**").hasAnyRole("ADMIN", "USER")
			.antMatchers("/users").hasRole("SERVICE");
			http.headers().cacheControl();
		
	}

}

