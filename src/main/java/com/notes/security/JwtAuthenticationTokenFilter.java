package com.notes.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.notes.exceptions.UnauthorizedUserException;
import com.notes.services.AuthServiceImpl;
import com.notes.models.User;

/**
 * 
 * Validate the received requests.
 * 
 * @author Jovani Brasil
 *
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private static final String AUTH_HEADER = "Authorization";
	private final AuthServiceImpl authClient;

	public JwtAuthenticationTokenFilter(AuthServiceImpl authClient) {
		this.authClient = authClient;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, UnauthorizedUserException {
		String token = request.getHeader(AUTH_HEADER);
		try {
			if(token != null) {
				log.info("Checking received token ...");
				TempUser tempUser = authClient.checkUserToken(token);
				log.info("Token checked!");
				if (tempUser == null) {
					log.info("The token is invalid.");
					throw new UnauthorizedUserException("Unauthorized user.");	
				}
				
				UserDetails userDetails = new User(tempUser.getName(), tempUser.getRole());
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}else {
				log.info("Token not found. Throw exception.");
				throw new UnauthorizedUserException("Unauthorized user.");
			}
		} catch (Exception e) {
			log.info("It was not possible to validate the user. {}", e.getMessage());
			throw new UnauthorizedUserException("It was not possible to validate the user.");
		}

		filterChain.doFilter(request, response);
	}

}
