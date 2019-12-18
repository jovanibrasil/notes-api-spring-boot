package com.notes.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.notes.enums.ProfileTypeEnum;

@Getter @Setter
@NoArgsConstructor
@ToString
@Document(collection = "users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String userId;
	private String userName;
	@Transient
	private String password;
	@Transient
	private ProfileTypeEnum profileType;

	public User(String userName, ProfileTypeEnum profileType) {
		this.userName = userName;
		this.profileType = profileType;
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
