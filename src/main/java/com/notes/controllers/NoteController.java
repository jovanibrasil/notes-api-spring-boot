package com.notes.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import com.notes.exceptions.CustomMessageSource;
import com.notes.mappers.NoteMapper;
import com.notes.services.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.notes.services.models.ErrorDetail;
import com.notes.services.models.Response;
import com.notes.models.Note;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notes")
@Slf4j
public class NoteController {

	private NoteService noteService;
	private NoteHelper noteHelper;
	private CustomMessageSource msgSrc;
	private final NoteMapper noteMapper;

	public NoteController(NoteService noteService, NoteHelper noteHelper, CustomMessageSource msgSrc, NoteMapper noteMapper) {
		this.noteService = noteService;
		this.noteHelper = noteHelper;
		this.msgSrc = msgSrc;
		this.noteMapper = noteMapper;
	}

	/**
	 * Returns a collection of notes of a particular user. 
	 *
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Response<List<Note>>> getNotes() {
		log.info("Getting all notes.");
		Response<List<Note>> response = new Response<List<Note>>();
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Note> notes = this.noteService.findNotesByUserName(currentUserName);
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
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response<NoteDTO>> saveNote(@Valid @RequestBody NoteDTO noteDTO) {
		log.info("Saving note.");
		Response<NoteDTO> response = new Response<NoteDTO>();

		Note note = noteMapper.noteDtoToNote(noteDTO);
		ValidationResult vr = noteHelper.validateNewNote(note);
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
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Response<NoteDTO>> updateNote(@RequestBody @Valid NoteDTO noteDTO) {
		log.info("Updating note.");
		Response<NoteDTO> response = new Response<NoteDTO>();

		Note note = noteMapper.noteDtoToNote(noteDTO);
		ValidationResult vr = noteHelper.validateExistentNote(note);
		if(vr.hasErrors()) {
			log.error("Validation error {}", vr.getErrors());
			vr.getErrors().forEach(e -> response.addError(new ErrorDetail(e)));
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  
		}
		note.setLastModifiedOn(LocalDateTime.now());
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
