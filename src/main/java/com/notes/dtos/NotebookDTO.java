package com.notes.dtos;

import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class NotebookDTO {

	private String id;
	private String name;
	private String userName;
	private LocalDateTime lastModifiedOn;

}
