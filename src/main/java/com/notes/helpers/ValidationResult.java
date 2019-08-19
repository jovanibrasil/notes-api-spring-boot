package com.notes.helpers;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

	private List<String> errors; // map of field name and error
	
	public ValidationResult() {
		this.errors = new ArrayList<String>();
	}
	
	public ValidationResult(List<String> list) {
		this.errors = list;
	}
	
	public void addError(String error) {
		this.errors.add(error);
	}
	
	public List<String> getErrors() {
		return this.errors;
	}
	
	public boolean hasErrors() {
		return !this.errors.isEmpty();
	}

}
