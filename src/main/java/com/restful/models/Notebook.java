package com.restful.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="notebooks")
public class Notebook {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long notebookId;
	@Column
	private String name;
	@OneToMany(cascade = CascadeType.ALL) //orphanRemoval = true)
	private List<Note> notes;
	
	public Notebook() {}
	
	public Notebook(Long id, String name) {
		this.notebookId = id;
		this.name = name;
	}
	
	public Long getNotebookId() {
		return notebookId;
	}
	public String getName() {
		return name;
	}
	
	public void setNotebookId(Long id) {
		this.notebookId = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Notebook [id=" + notebookId + ", name=" + name + ", notes=" + notes + "]";
	}
		
}
