package com.notes.services.impl;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.notes.exceptions.NotFoundException;
import com.notes.models.Notebook;
import com.notes.repositories.NotebookRepository;
import com.notes.services.NoteService;
import com.notes.services.NotebookService;
import com.notes.services.models.ErrorDetail;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Primary
public class NotebookServiceImpl implements NotebookService {

	private final NotebookRepository notebookRepository;
	private final NoteService noteService;
	
	/**
	 * Busca todos os notebooks de um determinado usuário.
	 * 
	 */
	@Override
	public Page<Notebook> findAllByUserName(String userName, Pageable pageable){
		return notebookRepository.findByUserName(userName, pageable);
	}
	
	/**
	 * Remove um notebook e suas respectivas notas.
	 * 
	 */
	@Override
	public void deleteById(String notebookId) {
		findById(notebookId); // verifica se onotebook existe		
		noteService.deleteNotesByNotebookId(notebookId);
		notebookRepository.deleteById(notebookId);		
	}

	/**
	 * Persiste um notebook no banco de dados.
	 * 
	 */
	@Override
	public Notebook saveNotebook(Notebook notebook) {
		return this.notebookRepository.save(notebook);
	}

	/**
	 * Busca um notebook por por ID especificado.
	 * 
	 */
	@Override
	public Notebook findById(String notebookId) {
		Optional<Notebook> optNotebook = this.notebookRepository.findById(notebookId);
		if(optNotebook.isPresent()) {
			return optNotebook.get();
		}
		throw new NotFoundException("error.notebook.find");
	}

	/**
	 * Atualiza um notebook existente, ou cria um novo caso não exista.
	 * 
	 */
	@Override
	public Notebook updateNotebook(Notebook notebook) {
		this.saveNotebook(notebook);
		return notebook;
	}

}
