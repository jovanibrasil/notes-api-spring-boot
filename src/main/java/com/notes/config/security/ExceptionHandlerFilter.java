package com.notes.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.exceptions.UnauthorizedUserException;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (UnauthorizedUserException e) {
			log.info("UnauthorizedUserException was thrown. {}", e.getMessage());
			ObjectMapper mapper = new ObjectMapper();
			PrintWriter out = response.getWriter();
			out.print(mapper.writeValueAsString(""));
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} catch (Exception e) {
			log.info("Exception. {}", e.getMessage());
		}
	}

}
