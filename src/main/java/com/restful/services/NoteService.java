package com.restful.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.restful.models.Note;
import com.restful.models.Notebook;
import com.restful.repositories.NoteRepository;

@Service
@Primary
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;
	
	// findall
	public List<Note> findAll(){
		return this.noteRepository.findAll();
	}
	
	// delete
	public void deleteNote(Long noteId) {
		this.noteRepository.deleteById(noteId);
	}
	
	// save
	public Note saveNote(Note note) {
		return this.noteRepository.save(note);
	}
	
	// find by note id
	public Optional<Note> findNoteById(Long noteId) {
		return this.noteRepository.findById(noteId);
	}
	
	// find by notebook id
	public List<Note> findNotesByNotebook(Long notebookId){
		System.out.println(notebookId);
		return this.noteRepository.getNotesByNotebook(notebookId);
	}
	
}
