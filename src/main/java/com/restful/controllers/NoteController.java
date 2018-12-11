package com.restful.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restful.Util;
import com.restful.dtos.NoteDTO;
import com.restful.models.Note;
import com.restful.models.Notebook;
import com.restful.services.NoteService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/note")
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@GetMapping("/all")
	public ResponseEntity<ArrayList<Note>> getAllNotes() {
		ArrayList<Note> notes = (ArrayList<Note>) this.noteService.findAll();
		return ResponseEntity.ok(notes);
	}
	
	@PostMapping
	public ResponseEntity<NoteDTO> saveNote(@RequestBody NoteDTO noteDTO) {
		
		System.out.println(noteDTO);
		
		// TODO validation
		Notebook nb = new Notebook();
		nb.setNotebookId(noteDTO.getNotebookId());
		// Save on database
		Note n = Util.convertNoteDTOtoNote(noteDTO);
		n.setNotebook(nb);
		n = this.noteService.saveNote(n);
		// Return note with the valid id generated 
		noteDTO.setNoteId(n.getNoteId());
		noteDTO.setLastModifiedOn(n.getLastModifiedOn());
		return ResponseEntity.ok(noteDTO);
	}
	
	@PutMapping
	public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO noteDTO) {
		
		
		// TODO validation
		Notebook nb = new Notebook();
		nb.setNotebookId(noteDTO.getNotebookId());
		// Save on database
		Note n = Util.convertNoteDTOtoNote(noteDTO);
		n.setNotebook(nb);
		
		System.out.println(noteDTO);
		
		n = this.noteService.saveNote(n);
		// Return note with the valid id generated 
		noteDTO.setNoteId(n.getNoteId());
		noteDTO.setLastModifiedOn(n.getLastModifiedOn());
		return ResponseEntity.ok(noteDTO);
	}
	
	@DeleteMapping("/{noteId}")
	public ResponseEntity<Note> deleteNote(@PathVariable Long noteId){
		Note note = this.noteService.findNoteById(noteId);
		this.noteService.deleteNote(noteId);
		return ResponseEntity.ok(note);
	}
	
	@GetMapping("/byNotebookId/{notebookId}")
	public ResponseEntity<ArrayList<Note>> getNotesByNotebook(@PathVariable Long notebookId){
		ArrayList<Note> notes = (ArrayList<Note>) this.noteService.findNotesByNotebook(notebookId);
		return ResponseEntity.ok(notes);
	}
	
	
}
