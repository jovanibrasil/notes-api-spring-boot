package com.notes.controllers.dto;

import javax.validation.constraints.NotNull;

import com.notes.model.enums.ApplicationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class JwtAuthenticationDTO {

	@NotNull
	private String userName;
	@NotNull
	private String password;
	private ApplicationType application;

	public JwtAuthenticationDTO(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

}
