package com.notes.service.impl;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.notes.exception.NotFoundException;
import com.notes.model.Note;
import com.notes.repository.NoteRepository;
import com.notes.service.NoteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Primary
public class NoteServiceImpl implements NoteService {

	private final NoteRepository noteRepository;

	/**
	 * Returns the notes for the current authenticated user. 
	 * 
	 */
	@Override
	public Page<Note> findNotesByUserName(Pageable pageable){
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return noteRepository.findByUserName(userName, pageable);
	}
	
	/**
	 * Deletes a note by a specified id.
	 * 
	 */
	@Override
	public void deleteNote(String noteId) {
		this.noteRepository.delete(findNoteById(noteId));
	}
	
	/**
	 * Saves a note.
	 * 
	 */
	@Override
	public Note saveNote(Note note) {
		note.setLastModifiedOn(LocalDateTime.now());
		return this.noteRepository.save(note);
	}
	
	/**
	 * Returns a note with specified id.
	 * 
	 */
	@Override
	public Note findNoteById(String noteId) {
		return noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException(""));
	}
	
	/**
	 * Returns a list of notes of a specified notebook.
	 * 
	 */
	@Override
	public Page<Note> findNotesByNotebookId(String notebookId, Pageable pageable){
		return this.noteRepository.findAllByNotebookId(notebookId, pageable);
	}

	/**
	 * Remove all notes from a specified notebook.
	 * 
	 */
	@Override
	public void deleteNotesByNotebookId(String notebookId) {
		findNotesByNotebookId(notebookId, Pageable.unpaged()).forEach(noteRepository::delete);
	}
	
}
