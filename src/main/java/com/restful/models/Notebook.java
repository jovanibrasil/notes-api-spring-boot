package com.restful.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notebooks")
public class Notebook {

	@Id
	private String id;
	private String name;
	private String userId;  
	
	public Notebook() {}
	
	public Notebook(String id, String name) {
		this.id = id;
		this.name = name;
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

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}	

	@Override
	public String toString() {
		return "Notebook [id=" + id + ", name=" + name + "]";
	}
		
}
