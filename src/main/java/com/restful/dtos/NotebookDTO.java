package com.restful.dtos;

public class NotebookDTO {

	private String id;
	private String name;
	
	public NotebookDTO() {}
	
	public NotebookDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(String notebookId) {
		this.id = notebookId;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "NotebookDTO [notebookId=" + id + ", name=" + name + "]";
	}
	
}
