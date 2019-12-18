package com.notes.dtos;

import javax.validation.constraints.NotNull;

import com.notes.enums.ApplicationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class JwtAuthenticationDto {

	@NotNull
	private String userName;
	@NotNull
	private String password;
	private ApplicationType application;

	public JwtAuthenticationDto(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

}
