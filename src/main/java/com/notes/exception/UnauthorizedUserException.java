package com.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Jovani Brasil.
 *
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserException extends RuntimeException {
	
	private static final long serialVersionUID = 6156088599016804345L;

	public UnauthorizedUserException(String message) {
		super(message);
	}
	
}
