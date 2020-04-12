package com.notes.services.impl;

import com.notes.models.Note;
import com.notes.repositories.NoteRepository;
import com.notes.services.NoteService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Primary
public class NoteServiceImpl implements NoteService {

	private final NoteRepository noteRepository;

	public List<Note> findNotesByUserName(String userName){
		return this.noteRepository.findByUserName(userName);
	}
	
	public void deleteNote(String noteId) {
		this.noteRepository.deleteById(noteId);
	}
	
	public Optional<Note> saveNote(Note note) {
		return Optional.of(this.noteRepository.save(note));
	}
	
	public Optional<Note> findNoteById(String noteId) {
		return this.noteRepository.findById(noteId);
	}
	
	public List<Note> findNotesByNotebookId(String notebookId){
		return this.noteRepository.findAllByNotebookId(notebookId);
	}

	/**
	 * Remove todas as notas de um notebook específico.
	 * 
	 */
	@Override
	public void deleteNotesByNotebookId(String notebookId) {
		findNotesByNotebookId(notebookId).forEach(noteRepository::delete);
	}
	
}
