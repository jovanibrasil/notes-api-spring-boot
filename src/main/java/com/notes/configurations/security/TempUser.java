package com.notes.configurations.security;

import com.notes.model.enums.ProfileTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TempUser {

	private String name;
	private ProfileTypeEnum role;

}
