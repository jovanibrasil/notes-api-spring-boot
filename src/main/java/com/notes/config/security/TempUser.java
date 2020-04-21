package com.notes.config.security;

import com.notes.enums.ProfileTypeEnum;
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
