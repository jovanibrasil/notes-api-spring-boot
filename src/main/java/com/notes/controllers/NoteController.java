package com.notes.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.notes.dtos.NoteDTO;
import com.notes.mappers.NoteMapper;
import com.notes.models.Note;
import com.notes.services.NoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

	private final NoteService noteService;
	private final NoteMapper noteMapper;

	/** 
	 * Retorna uma coleção de notas do usuário corrente.
	 *
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Page<Note>> getNotes(Pageable pageable) {
		log.info("Getting all notes.");
		Page<Note> notes = this.noteService.findNotesByUserName(pageable);
		return ResponseEntity.ok(notes);
	}
	
	/**
	 * Remove uma nota de Id especificado.
	 * 
	 * @param noteId é o Id da nota ser removida
	 * @return
	 */
	@DeleteMapping("/{noteId}")
	public ResponseEntity<?> deleteNote(@PathVariable String noteId){
		log.info("Deleting note {}.", noteId);
		this.noteService.deleteNote(noteId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	/**
	 * Salva uma nota.
	 * 
	 * @param noteDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<NoteDTO> saveNote(@Valid @RequestBody NoteDTO noteDTO) {
		log.info("Saving note.");
		
		Note note = noteMapper.noteDtoToNote(noteDTO);
		note = this.noteService.saveNote(note);
		 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{noteId}")
				.buildAndExpand(note.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Atualiza uma nota já existente.
	 * 
	 * @param noteDTO
	 * @return
	 */
	@PutMapping
	public ResponseEntity<NoteDTO> updateNote(@RequestBody @Valid NoteDTO noteDTO) {
		log.info("Updating note id: {}", noteDTO.getId());
		
		Note note = noteMapper.noteDtoToNote(noteDTO);
		note = noteService.saveNote(note);
		noteDTO.setId(note.getId());
		noteDTO.setLastModifiedOn(note.getLastModifiedOn());
		
		return ResponseEntity.ok(noteDTO);
	}
		
}
