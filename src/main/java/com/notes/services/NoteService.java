package com.notes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.notes.models.Note;
import com.notes.repositories.NoteRepository;

@Service
@Primary
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;
	
	// findall
	public List<Note> findNotesByUserName(String userName){
		return this.noteRepository.findByUserName(userName);
	}
	
	// delete
	public void deleteNote(String noteId) {
		this.noteRepository.deleteById(noteId);
	}
	
	// save
	public Optional<Note> saveNote(Note note) {
		return Optional.of(this.noteRepository.save(note));
	}
	
	// find by note id
	public Optional<Note> findNoteById(String noteId) {
		return this.noteRepository.findById(noteId);
	}
	
	// find by notebook id
	public List<Note> findNotesByNotebookId(String notebookId){
		return this.noteRepository.findAllByNotebookId(notebookId);
	}
	
}
