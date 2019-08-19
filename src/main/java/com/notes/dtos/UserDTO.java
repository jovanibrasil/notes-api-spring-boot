package com.notes.dtos;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

public class UserDTO {
	
	@NonNull
	private String userId;
	@Length(min=4, max=30, message="The user name must contains between 4 and 30 characters.")
    @NotBlank(message="The user name must not be empty.")
	private String userName;
    
    public UserDTO() {}
    
    public String getUserId() {
		return userId;
	}
    
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
		    	
}
