package com.notes.service.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.notes.controller.dto.NoteDTO;
import com.notes.exception.ExceptionMessages;
import com.notes.exception.NotFoundException;
import com.notes.mapper.NoteMapper;
import com.notes.model.Note;
import com.notes.repository.NoteRepository;
import com.notes.service.NoteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Primary
public class NoteServiceImpl implements NoteService {

	private final NoteRepository noteRepository;
	private final NoteMapper noteMapper;

	/**
	 * Returns the notes for the current authenticated user. 
	 * 
	 */
	@Override
	public Page<NoteDTO> findNotesByUserName(Pageable pageable){
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return noteRepository.findByUserName(userName, pageable)
				.map(noteMapper::noteToNoteDto);
	}
	
	/**
	 * Deletes a note by a specified id.
	 * 
	 */
	@Override
	public void deleteNote(String noteId) {
		noteRepository.findById(noteId)
		.ifPresentOrElse(noteRepository::delete, 
				() -> new NotFoundException(ExceptionMessages.NOTE_NOT_FOUND));
	}
	
	/**
	 * Saves a note.
	 * 
	 */
	@Override
	public NoteDTO saveNote(NoteDTO noteDTO) {
		Note note = noteMapper.noteDtoToNote(noteDTO);
		note.setLastModifiedOn(LocalDateTime.now());
		return noteMapper.noteToNoteDto(noteRepository.save(note));
	}
	
	/**
	 * Returns a note with specified id.
	 * 
	 */
	@Override
	public NoteDTO findNoteById(String noteId) {
		return noteRepository.findById(noteId)
				.map(noteMapper::noteToNoteDto)
				.orElseThrow(() -> new NotFoundException(""));
	}
	
	/**
	 * Returns a list of notes of a specified notebook.
	 * 
	 */
	@Override
	public Page<NoteDTO> findNotesByNotebookId(String notebookId, Pageable pageable){
		return this.noteRepository.findAllByNotebookId(notebookId, pageable)
				.map(noteMapper::noteToNoteDto);
	}

	/**
	 * Remove all notes from a specified notebook.
	 * 
	 */
	@Override
	public void deleteNotesByNotebookId(String notebookId) {
		noteRepository.findAllByNotebookId(notebookId, Pageable.unpaged())
			.forEach(noteRepository::delete);
	}
	
}
