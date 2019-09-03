package com.notes.enums;

import org.springframework.security.core.GrantedAuthority;

public enum ProfileTypeEnum implements GrantedAuthority {
	ROLE_ADMIN, ROLE_USER, ROLE_SERVICE;
	
	@Override
	public String getAuthority() {
		return name();
	}
	
}
