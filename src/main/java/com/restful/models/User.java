package com.restful.models;

import org.springframework.data.annotation.Id;

public class User {

	@Id
	private String userId;
	private String userName;
	private String password;
	
	public User() {}
	
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public void setUserName(String name) {
		this.userName = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
