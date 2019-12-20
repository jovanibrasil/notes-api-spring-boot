package com.notes.dtos;

import lombok.*;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class NoteDTO {

	private String id;
	@NotBlank(message = "{error.note.title.notblank}")
	@Size(min=1, max=20, message="{error.note.title.size}")
	private String title;
	@Size(max=200, message="{error.note.text.size}")
	private String text;
	@NotBlank(message = "{error.note.notebookid.notblank}")
	private String notebookId;
	@NotBlank(message = "{error.note.backgroundcolor.notblank}")
	private String backgroundColor; // Example: rgba(251, 243, 129, 0.74)
	private LocalDateTime lastModifiedOn;

}
