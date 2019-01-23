package com.restful;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityAuthConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user")
			.password("password")
			.roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		
//		.antMatchers("/", "/users/login", "/users/signup", "/users/logout")
//		.permitAll()
//		.antMatchers("/users/*", "/posts/view/**", "/posts/management/**")
//		.fullyAuthenticated();
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/hello").fullyAuthenticated();
	}
	
}
