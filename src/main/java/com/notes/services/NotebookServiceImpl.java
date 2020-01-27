package com.notes.services;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.notes.models.Notebook;
import com.notes.repositories.NotebookRepository;

@RequiredArgsConstructor
@Service
@Primary
public class NotebookServiceImpl implements NotebookService {

	private final NotebookRepository notebookRepository;

	public List<Notebook> findAllByUserName(String userName){
		return this.notebookRepository.findByUserName(userName);
	}
	
	public void deleteById(String notebookId) {
		this.notebookRepository.deleteById(notebookId);
	}

	public Optional<Notebook> saveNotebook(Notebook notebook) {
		return Optional.of(this.notebookRepository.save(notebook));
	}

	public Optional<Notebook> findById(String notebookId) {
		return this.notebookRepository.findById(notebookId);
	}
	
}
