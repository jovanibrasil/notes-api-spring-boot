package com.notes.security;

import com.notes.enums.ProfileTypeEnum;

public class TempUser {

	private String name;
	private ProfileTypeEnum role;
	
	public TempUser() {}
	
	public TempUser(String name, ProfileTypeEnum role) {
		this.name = name;
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProfileTypeEnum getRole() {
		return role;
	}
	public void setRole(ProfileTypeEnum role) {
		this.role = role;
	}
	
}
