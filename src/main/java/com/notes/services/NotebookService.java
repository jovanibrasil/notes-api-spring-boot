package com.notes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.notes.models.Notebook;
import com.notes.repositories.NotebookRepository;

@Service
@Primary
public class NotebookService {

	private NotebookRepository notebookRepository;

	public NotebookService(NotebookRepository notebookRepository) {
		this.notebookRepository = notebookRepository;
	}

	public List<Notebook> findAllByUserId(String userName){
		return this.notebookRepository.findByUserName(userName);
	}
	
	public void deleteNotebookById(String notebookId) {
		this.notebookRepository.deleteById(notebookId);
	}

	public Optional<Notebook> saveNotebook(Notebook notebook) {
		return Optional.of(this.notebookRepository.save(notebook));
	}

	public Optional<Notebook> findNotebookById(String notebookId) {
		return this.notebookRepository.findById(notebookId);
	}
	
}
