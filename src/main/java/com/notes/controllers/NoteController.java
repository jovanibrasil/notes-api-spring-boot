package com.notes.controllers;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.notes.mappers.NoteMapper;
import com.notes.models.Note;
import com.notes.services.NoteService;
import com.notes.services.models.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notes")
@Slf4j
@RequiredArgsConstructor
public class NoteController {

	private final NoteService noteService;
	private final NoteMapper noteMapper;

	/**
	 * Returns a collection of notes of a particular user. 
	 *
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Response<Page<Note>>> getNotes(Pageable pageable) {
		log.info("Getting all notes.");
		Response<Page<Note>> response = new Response<>();
		Page<Note> notes = this.noteService.findNotesByUserName(pageable);
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
	public ResponseEntity<?> deleteNote(@PathVariable String noteId){
		log.info("Delete note {}.", noteId);
		this.noteService.deleteNote(noteId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
		Response<NoteDTO> response = new Response<>();

		Note note = noteMapper.noteDtoToNote(noteDTO);
		// Save on database
		note.setLastModifiedOn(LocalDateTime.now());
		
		note = this.noteService.saveNote(note);
		
		// Return note with the valid id generated 
		noteDTO.setId(note.getId());
		noteDTO.setLastModifiedOn(note.getLastModifiedOn());
		response.setData(noteDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
		Response<NoteDTO> response = new Response<>();
		Note note = noteMapper.noteDtoToNote(noteDTO);
		note = noteService.saveNote(note);
		// Return note with the valid id generated 
		noteDTO.setId(note.getId());
		noteDTO.setLastModifiedOn(note.getLastModifiedOn());
		response.setData(noteDTO);
		return ResponseEntity.ok(response);
	}
		
}
