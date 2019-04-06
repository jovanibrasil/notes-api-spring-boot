package com.notes.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.notes.enums.ProfileTypeEnum;

public class User implements UserDetails {

	@Id
	private String userId;
	private String userName;
	private String password;
	
	private ProfileTypeEnum profileType;
	
	public User() {}
	
	public User(String userName, ProfileTypeEnum profileType) {
		this.userName = userName;
		this.profileType = profileType;
	}
	
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>(Arrays.asList(this.profileType));
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
	
}
