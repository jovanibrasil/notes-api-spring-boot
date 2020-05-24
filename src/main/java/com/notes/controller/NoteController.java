package com.notes.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.notes.controller.dto.NoteDTO;
import com.notes.mapper.NoteMapper;
import com.notes.model.Note;
import com.notes.service.NoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

	private final NoteService noteService;
	private final NoteMapper noteMapper;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<Note> getNotes(Pageable pageable) {
		log.info("Getting all notes.");
		return noteService.findNotesByUserName(pageable);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{noteId}")
	public void deleteNote(@PathVariable String noteId){
		log.info("Deleting note {}.", noteId);
		noteService.deleteNote(noteId);
	}
	
	@PostMapping
	public ResponseEntity<?> saveNote(@Valid @RequestBody NoteDTO noteDTO) {
		log.info("Saving note.");
		Note note = noteService.saveNote(noteMapper.noteDtoToNote(noteDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{noteId}")
				.buildAndExpand(note.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{noteid}")
	public NoteDTO updateNote(@RequestBody @Valid NoteDTO noteDTO, String noteId) {
		log.info("Updating note id: {}", noteId);
		Note note = noteMapper.noteDtoToNote(noteDTO);
		note.setId(noteId);
		note = noteService.saveNote(note);
		return noteMapper.noteToNoteDto(note);
	}
		
}
