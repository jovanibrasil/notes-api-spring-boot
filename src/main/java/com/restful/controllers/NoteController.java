package com.restful.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@GetMapping("/all")
	public ResponseEntity<ArrayList<Note>> getAllNotes(HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("userName");
		ArrayList<Note> notes = (ArrayList<Note>) this.noteService.findNotesByUserId(userId);
		return ResponseEntity.ok(notes);
	}
	
	@PostMapping
	public ResponseEntity<NoteDTO> saveNote(@RequestBody NoteDTO noteDTO, HttpServletRequest request) {
		
		String userId = (String) request.getSession().getAttribute("userName");
		// TODO validation
		Notebook nb = new Notebook();
		nb.setId(noteDTO.getNotebookId());
		nb.setUserId(userId);
		// Save on database
		Note n = Util.convertNoteDTOtoNote(noteDTO, userId);
		n.setLastModifiedOn(new Date());
		n = this.noteService.saveNote(n);
		// Return note with the valid id generated 
		noteDTO.setId(n.getId());
		noteDTO.setLastModifiedOn(n.getLastModifiedOn());
		return ResponseEntity.ok(noteDTO);
	}
	
	@PutMapping
	public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO noteDTO, HttpServletRequest request) {
		
		String userId = (String) request.getSession().getAttribute("userName");
		// TODO validation
		//Notebook nb = new Notebook();
		//nb.setId(noteDTO.getNotebookId());
		// Save on database
		Note n = Util.convertNoteDTOtoNote(noteDTO, userId);
		// TODO n.setNotebook(nb);
		n.setLastModifiedOn(new Date());
		n.setUserId(userId);
		n = this.noteService.saveNote(n);
		// Return note with the valid id generated 
		noteDTO.setId(n.getId());
		noteDTO.setLastModifiedOn(n.getLastModifiedOn());
		return ResponseEntity.ok(noteDTO);
	}
	
	@DeleteMapping("/{noteId}")
	public ResponseEntity<Note> deleteNote(@PathVariable String noteId){
		Optional<Note> optNote = this.noteService.findNoteById(noteId);
		if(optNote.isPresent()) {
			this.noteService.deleteNote(noteId);
			return ResponseEntity.ok(optNote.get());
		}else {
			// TODO return error
			//System.out.println("nota não encontrada");
			return null;
		}
		
	}
	
	@GetMapping("/byNotebookId/{notebookId}")
	public ResponseEntity<ArrayList<Note>> getNotesByNotebook(@PathVariable String notebookId){
		ArrayList<Note> notes = (ArrayList<Note>) this.noteService.findNotesByNotebookId(notebookId);
		return ResponseEntity.ok(notes);
	}
	
	
}
