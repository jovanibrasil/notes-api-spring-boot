package com.restful.dtos;

import java.util.Date;

public class NoteDTO {

	private String id;
	private String title;
	private String text;
	private String notebookId;
	private Date lastModifiedOn;
	
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
	
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	@Override
	public String toString() {
		return "NoteDTO [noteId=" + id + ", title=" + title + ", text=" + text + ", notebookId=" + notebookId
				+ ", lastModifiedOn=" + lastModifiedOn + "]";
	}
	
}
