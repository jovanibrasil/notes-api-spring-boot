package com.notes.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@NoArgsConstructor
@ToString
@Document(collection = "notes")
public class Note {

	@Id
	private String id;
	private String title;
	private String text;
	private LocalDateTime lastModifiedOn;
	private String notebookId;
	private String userName;
	private String backgroundColor;

	public Note(String id, String title, String text, String notebookId, 
			String userName, String backgroundColor) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.lastModifiedOn = LocalDateTime.now();
		this.notebookId = notebookId;
		this.userName = userName;
		this.backgroundColor = backgroundColor;
	}

}
