package com.notes.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.notes.exceptions.CustomMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.notes.dtos.NoteDTO;
import com.notes.exceptions.ResourceNotFoundException;
import com.notes.helpers.NoteHelper;
import com.notes.helpers.ValidationResult;
import com.notes.integrations.ErrorDetail;
import com.notes.integrations.Response;
import com.notes.models.Note;
import com.notes.services.NoteService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notes")
public class NoteController {

	private static final Logger log = LoggerFactory.getLogger(NoteController.class);
	
	private NoteService noteService;
	private NoteHelper noteHelper;
	private CustomMessageSource msgSrc;

	public NoteController(NoteService noteService, NoteHelper noteHelper, CustomMessageSource msgSrc) {
		this.noteService = noteService;
		this.noteHelper = noteHelper;
		this.msgSrc = msgSrc;
	}

	/**
	 * Returns a collection of notes of a particular user. 
	 * 
	 * @param request
	 * @param principal
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Response<List<Note>>> getNotes(HttpServletRequest request, Principal principal) {
		log.info("Getting all notes.");
		Response<List<Note>> response = new Response<List<Note>>(); 
		String userName = principal.getName();
		List<Note> notes = this.noteService.findNotesByUserName(userName);
		response.setData(notes);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Deletes a note by a particular id.
	 * 
	 * @param noteId is the note id, that is the identification field
	 * @return
	 */
	@DeleteMapping("/{noteId}")
	public ResponseEntity<Response<String>> deleteNote(@PathVariable String noteId){
		log.info("Delete note {}.", noteId);
		Response<String> response = new Response<String>(); 
		Optional<Note> optNote = this.noteService.findNoteById(noteId);
		if(optNote.isPresent()) {
			this.noteService.deleteNote(noteId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}else {
			log.error("It was not possible delete the note {}.", noteId);
			throw new ResourceNotFoundException(msgSrc.getMessage("error.note.delete"));
		}
	}
	
	/**
	 * Saves a new note.
	 * 
	 * @param noteDTO
	 * @param request
	 * @param principal
	 * @param bindingResult
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response<NoteDTO>> saveNote(@Valid @RequestBody NoteDTO noteDTO, 
			HttpServletRequest request, Principal principal) {
		log.info("Saving note.");
		Response<NoteDTO> response = new Response<NoteDTO>();
		
		String currentUserName = principal.getName();
		Note note = noteHelper.convertNoteDTOtoNote(noteDTO, currentUserName);
		ValidationResult vr = noteHelper.validateNewNote(note, currentUserName);
		if(vr.hasErrors()) {
			log.error("Validation error {}",  vr.getErrors());
			// Return bad request, invalid content.
			vr.getErrors().forEach(e -> response.addError(new ErrorDetail(e)));
			ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);  
		}
		// Save on database
		note.setLastModifiedOn(LocalDateTime.now());
		Optional<Note> optNote = this.noteService.saveNote(note);
		if(optNote.isPresent()) {
			// Return note with the valid id generated 
			noteDTO.setId(optNote.get().getId());
			noteDTO.setLastModifiedOn(optNote.get().getLastModifiedOn());
			response.setData(noteDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}else {
			response.addError(new ErrorDetail(msgSrc.getMessage("error.note.create")));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	/**
	 * Updates an already existent note.
	 * 
	 * @param noteDTO
	 * @param request
	 * @param principal
	 * @param bindingResult
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Response<NoteDTO>> updateNote(@RequestBody @Valid NoteDTO noteDTO, 
			HttpServletRequest request, Principal principal) {
		log.info("Updating note.");
		Response<NoteDTO> response = new Response<NoteDTO>();
		
		String currentUserName = principal.getName();
		Note note = noteHelper.convertNoteDTOtoNote(noteDTO, currentUserName);
		ValidationResult vr = noteHelper.validateExistentNote(note, currentUserName);
		if(vr.hasErrors()) {
			log.error("Validation error {}", vr.getErrors());
			vr.getErrors().forEach(e -> response.addError(new ErrorDetail(e)));
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  
		}
		note.setLastModifiedOn(LocalDateTime.now());
		note.setUserName(currentUserName);
		Optional<Note> optNote = this.noteService.saveNote(note);
		if(optNote.isPresent()) {
			// Return note with the valid id generated 
			note = optNote.get();
			noteDTO.setId(note.getId());
			noteDTO.setLastModifiedOn(note.getLastModifiedOn());
			response.setData(noteDTO);
			return ResponseEntity.ok(response);
		}else {
			log.error("It was not possible to update the note {}.", note.getId());
			response.addError(new ErrorDetail(msgSrc.getMessage("error.note.update")));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
		
}
