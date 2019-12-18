package com.notes.dtos;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


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
	
	public NoteDTO(String id, String title, String text, String notebookId,
				   LocalDateTime lastModifiedOn, String backgroundColor) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.notebookId = notebookId;
		this.lastModifiedOn = lastModifiedOn;
		this.backgroundColor = backgroundColor;
	}
	
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	
	public void setId(String noteId) {
		this.id = noteId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getNotebookId() {
		return notebookId;
	}
	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}
	
	public LocalDateTime getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	@Override
	public String toString() {
		return "NoteDTO [noteId=" + id + ", title=" + title + ", text=" + text + ", notebookId=" + notebookId
				+ ", lastModifiedOn=" + lastModifiedOn + "]";
	}
	
}
