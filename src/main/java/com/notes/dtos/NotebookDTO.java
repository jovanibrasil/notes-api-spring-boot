package com.notes.dtos;

public class NotebookDTO {

	private String id;
	private String name;
	private String userName;
	
	public NotebookDTO() {}
	
	public NotebookDTO(String id, String notebookName, String userName) {
		this.id = id;
		this.name = notebookName;
		this.userName = userName;
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
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "NotebookDTO [notebookId=" + id + ", name=" + name + "]";
	}
	
}
