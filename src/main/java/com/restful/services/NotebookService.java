package com.restful.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.restful.models.Notebook;
import com.restful.repositories.NotebookRepository;

@Service
@Primary
public class NotebookService {

	@Autowired
	private NotebookRepository notebookRepository;
	
	// findAll
	public List<Notebook> findAll(){
		return this.notebookRepository.findAll();
	}
	
	// delete
	public void deleteNotebook(Long notebookId) {
		this.notebookRepository.delete(notebookId);
	}
	
	// post
	public Notebook saveNotebook(Notebook notebook) {
		return this.notebookRepository.save(notebook);
	}

	public Notebook findNote(Long notebookId) {
		return this.notebookRepository.findOne(notebookId);
	}
	
}
