package com.restful.models;

import java.util.ArrayList;
import java.util.List;

public class Notebook {

	private Long id;
	private String name;
	private List<Note> notes;
	
	public Notebook() {}
	
	public Notebook(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<Note> getNotes() {
		if(this.notes == null)
			this.notes = new ArrayList<Note>();
		return this.notes;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addNote(Note note) {
		if(this.notes == null)
			this.notes = new ArrayList<Note>();
		this.notes.add(note);
	}
	
	public void deleteNode() {
		// TODO
	}
	
}
