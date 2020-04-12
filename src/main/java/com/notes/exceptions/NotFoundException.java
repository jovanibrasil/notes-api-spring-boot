package com.notes.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5949394867526077384L;

	public NotFoundException(String msg) {
		super(msg);
	}
	
}
