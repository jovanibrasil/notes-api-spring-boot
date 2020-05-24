package com.notes.service.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.notes.exception.NotFoundException;
import com.notes.model.Notebook;
import com.notes.repository.NotebookRepository;
import com.notes.service.NoteService;
import com.notes.service.NotebookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Primary
public class NotebookServiceImpl implements NotebookService {

	private final NotebookRepository notebookRepository;
	private final NoteService noteService;
	
	/**
	 * Returns a collection of notebooks for the current authenticated user.
	 * 
	 */
	@Override
	public Page<Notebook> findAllByUserName(Pageable pageable){
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return notebookRepository.findByUserName(userName, pageable);
	}
	
	/**
	 * Removes a notebook with the specified id.
	 */
	@Override
	public void deleteById(String notebookId) {
		this.notebookRepository.findById(notebookId).ifPresentOrElse(
			notebook -> {
				noteService.deleteNotesByNotebookId(notebookId);
				notebookRepository.deleteById(notebookId);	
			},
			() -> { throw new NotFoundException("error.notebook.find"); }
		);
	}

	/**
	 * Saves a notebook.
	 * 
	 */
	@Override
	public Notebook saveNotebook(Notebook notebook) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		notebook.setLastModifiedOn(LocalDateTime.now());
		notebook.setUserName(userName);
		return this.notebookRepository.save(notebook);
	}

	/**
	 * Returns a notebook with specified id.
	 * 
	 */
	@Override
	public Notebook findById(String notebookId) {
		return this.notebookRepository.findById(notebookId)
				.orElseThrow(() -> new NotFoundException("error.notebook.find"));
	}

	/**
	 * Updates a notebook if it does exist, otherwise it will be created
	 * 
	 */
	@Override
	public Notebook updateNotebook(Notebook notebook) {
		this.saveNotebook(notebook);
		return notebook;
	}

}
