package com.restful.dtos;

public class NotebookDTO {

	private Long notebookId;
	private String name;
	
	public Long getNotebookId() {
		return notebookId;
	}
	
	public String getName() {
		return name;
	}
	public void setNotebookId(Long notebookId) {
		this.notebookId = notebookId;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "NotebookDTO [notebookId=" + notebookId + ", name=" + name + "]";
	}
	
}
