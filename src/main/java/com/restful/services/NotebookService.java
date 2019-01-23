package com.restful.services;

import java.util.List;
import java.util.Optional;

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
		this.notebookRepository.deleteById(notebookId);
	}
	
	// post
	public Notebook saveNotebook(Notebook notebook) {
		return this.notebookRepository.save(notebook);
	}

	public Optional<Notebook> findNote(Long notebookId) {
		return this.notebookRepository.findById(notebookId);
	}
	
}
