package com.restful.models;

import java.util.Date;

public class Note {

	private Long id;
	private String title;
	private String text;
	private Date lastModifiedOn;
	
	public Note() {}
	
	public Note(Long id, String title, String text, Date lastModifiedOn) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.lastModifiedOn = lastModifiedOn;
	}
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	
}
