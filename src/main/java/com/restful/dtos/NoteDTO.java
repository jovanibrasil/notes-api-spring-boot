package com.restful.dtos;

import java.util.Date;

public class NoteDTO {

	private Long noteId;
	private String title;
	private String text;
	private Long notebookId;
	private Date lastModifiedOn;
	
	public Long getNoteId() {
		return noteId;
	}
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	public Long getNotebookId() {
		return notebookId;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setNotebookId(Long notebookId) {
		this.notebookId = notebookId;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	@Override
	public String toString() {
		return "NoteDTO [noteId=" + noteId + ", title=" + title + ", text=" + text + ", notebookId=" + notebookId
				+ ", lastModifiedOn=" + lastModifiedOn + "]";
	}
	
}
