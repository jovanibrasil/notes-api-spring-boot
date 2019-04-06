package com.notes.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notebooks")
public class Notebook {

	@Id
	private String id;
	private String name;
	private String userName;  
	
	public Notebook() {}
	
	public Notebook(String id, String name, String userName) {
		this.id = id;
		this.name = name;
		this.userName = userName;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userId) {
		this.userName = userId;
	}	

	@Override
	public String toString() {
		return "Notebook [id=" + id + ", name=" + name + "]";
	}
		
}
