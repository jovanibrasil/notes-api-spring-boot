package com.restful.models;

import java.util.List;

public class Notebook {

	private Long id;
	private String name;
	private List<Note> notes;
	
	public Notebook() {};
	
	public Notebook(Long id, String name) {
		this.id = id;
		this.name = name;
	};
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
}
