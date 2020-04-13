package com.notes.services.impl;

import com.notes.exceptions.NotFoundException;
import com.notes.models.Note;
import com.notes.repositories.NoteRepository;
import com.notes.services.NoteService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Primary
public class NoteServiceImpl implements NoteService {

	private final NoteRepository noteRepository;

	@Override
	public Page<Note> findNotesByUserName(Pageable pageable){
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return noteRepository.findByUserName(userName, pageable);
	}
	
	@Override
	public void deleteNote(String noteId) {
		Note note = findNoteById(noteId);  
		this.noteRepository.delete(note);
	}
	
	@Override
	public Note saveNote(Note note) {
		note.setLastModifiedOn(LocalDateTime.now());
		return this.noteRepository.save(note);
	}
	
	@Override
	public Note findNoteById(String noteId) {
		Optional<Note> optNotebook = noteRepository.findById(noteId);
		if(optNotebook.isPresent()) {
			return optNotebook.get();
		}
		throw new NotFoundException("");
	}
	
	@Override
	public Page<Note> findNotesByNotebookId(String notebookId, Pageable pageable){
		return this.noteRepository.findAllByNotebookId(notebookId, pageable);
	}

	/**
	 * Remove todas as notas de um notebook específico.
	 * 
	 */
	@Override
	public void deleteNotesByNotebookId(String notebookId) {
		findNotesByNotebookId(notebookId, Pageable.unpaged()).forEach(noteRepository::delete);
	}
	
}
