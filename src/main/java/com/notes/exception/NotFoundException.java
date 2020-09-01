package com.notes.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5949394867526077384L;

	public NotFoundException(String msg) {
		super(msg);
	}
	
}
