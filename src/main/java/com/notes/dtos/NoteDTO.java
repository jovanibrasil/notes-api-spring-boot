package com.notes.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class NoteDTO {

	private String id;
	@NotBlank(message = "The message must not be null or empty.")
	@Size(min=1, max=20, message="The title must contains between 1 and 20 characters.")
	private String title;
	@Size(max=200, message="The text must contains between 0 and 200 characters.")
	private String text;
	@NotBlank(message = "The notebookId must not be null or empty.")
	private String notebookId;
	@NotBlank(message = "The color must not be null or empty")
	private String backgroundColor; // Example: rgba(251, 243, 129, 0.74)
	private LocalDateTime lastModifiedOn;

}
