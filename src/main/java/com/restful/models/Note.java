package com.restful.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="notes")
public class Note {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long noteId;
	@Column
	private String title;
	@Column
	private String text;
	@Column
	private Date lastModifiedOn;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(referencedColumnName="notebookId", name="notebook_id")
	private Notebook notebook;

	public Note() {}
	
	public Note(Long id, String title, String text) {
		this.noteId = id;
		this.title = title;
		this.text = text;
		this.lastModifiedOn = new Date();
	}
	
	public Long getNoteId() {
		return noteId;
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
	public void setNoteId(Long id) {
		this.noteId = id;
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
	
	public Notebook getNotebook() {
		return notebook;
	}

	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}

	@Override
	public String toString() {
		return "Note [id=" + noteId + ", title=" + title + ", text=" + text + ", lastModifiedOn=" + lastModifiedOn + "]";
	}
	
}
