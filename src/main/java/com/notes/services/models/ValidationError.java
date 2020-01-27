package com.notes.services.models;

/**
 * 
 * @author Jovani Brasil
 *
 */
public class ValidationError {

	private final String message;
	private final String field;
	private final Object parameter;
	
	public ValidationError(String message, String field, Object parameter) {
		super();
		this.message = message;
		this.field = field;
		this.parameter = parameter;
	}

}
