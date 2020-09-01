package com.notes.service.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.notes.controller.dto.NotebookDTO;
import com.notes.exception.ExceptionMessages;
import com.notes.exception.NotFoundException;
import com.notes.mapper.NotebookMapper;
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
	private final NotebookMapper notebookMapper;
	
	/**
	 * Returns a collection of notebooks for the current authenticated user.
	 * 
	 */
	@Override
	public Page<NotebookDTO> findAllByUserName(Pageable pageable){
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return notebookRepository.findByUserName(userName, pageable)
				.map(notebookMapper::notebookToNotebookDto);
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
			() -> { throw new NotFoundException(ExceptionMessages.NOTEBOOK_NOT_FOUND); }
		);
	}

	/**
	 * Saves a notebook.
	 * 
	 */
	@Override
	public NotebookDTO saveNotebook(NotebookDTO notebookDTO) {
		Notebook notebook = notebookMapper.notebookDtoToNotebook(notebookDTO);
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		notebook.setLastModifiedOn(LocalDateTime.now());
		notebook.setUserName(userName);
		return notebookMapper.notebookToNotebookDto(notebookRepository.save(notebook));
	}

	/**
	 * Returns a notebook with specified id.
	 * 
	 */
	@Override
	public NotebookDTO findById(String notebookId) {
		return this.notebookRepository.findById(notebookId)
				.map(notebookMapper::notebookToNotebookDto)
				.orElseThrow(() -> new NotFoundException(ExceptionMessages.NOTEBOOK_NOT_FOUND));
	}

	/**
	 * Updates a notebook if it does exist, otherwise it will be created
	 * 
	 */
	@Override
	public NotebookDTO updateNotebook(NotebookDTO notebookDTO) {
		return this.saveNotebook(notebookDTO);
	}

}
