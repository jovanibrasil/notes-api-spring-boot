package com.notes.dtos;

import javax.validation.constraints.NotBlank;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class UserDTO {
	
	@NonNull
	private String userId;
	@Length(min=4, max=30, message="{error.user.name.size}")
    @NotBlank(message="{error.user.name.notblank}")
	private String userName;

}
