package com.notes.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.notes.exceptions.UnauthorizedUserException;
import com.notes.integrations.AuthClient;
import com.notes.models.User;

/**
 * 
 * Validated the received requests.
 * 
 * @author jovani
 *
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private static final String AUTH_HEADER = "Authorization";

	@Autowired
	private AuthClient authClient;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, UnauthorizedUserException {
		
		String token = request.getHeader(AUTH_HEADER);
		if (token == null) throw new UnauthorizedUserException("Unauthorized user.");
		try {
			if(token != null) {
				TempUser tempUser = authClient.checkToken(token);
				
				if (tempUser == null) throw new UnauthorizedUserException("Unauthorized user.");	
				UserDetails userDetails = new User(tempUser.getName(), tempUser.getRole());
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			throw new UnauthorizedUserException("It was not posssible to validate the user.");
		}

		filterChain.doFilter(request, response);
	}

}
