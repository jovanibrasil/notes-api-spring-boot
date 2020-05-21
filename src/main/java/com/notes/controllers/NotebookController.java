package com.notes.controllers;

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

import com.notes.controllers.dto.NotebookDTO;
import com.notes.mappers.NotebookMapper;
import com.notes.model.Note;
import com.notes.model.Notebook;
import com.notes.services.NoteService;
import com.notes.services.NotebookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notebooks")
public class NotebookController {

	private final NotebookService notebookService;
	private final NoteService noteService;
	private final NotebookMapper notebookMapper;

	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{notebookId}")
	public void deleteNotebook(@PathVariable String notebookId) {
		log.info("Deleting notebook id = {}", notebookId);
		notebookService.deleteById(notebookId);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<NotebookDTO> getAllNotebooks(Pageable pageable) {
		return notebookService.findAllByUserName(pageable)
			.map(notebookMapper::notebookToNotebookDto);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{notebookId}/notes")
	public Page<Note> getNotesByNotebook(@PathVariable String notebookId, Pageable pageable){
		return noteService.findNotesByNotebookId(notebookId, pageable);
	}

	@PostMapping
	public ResponseEntity<?> createNotebook(@RequestBody @Valid NotebookDTO notebookDTO) {
		log.info("Creating new notebook.");
		Notebook notebook = notebookService.saveNotebook(notebookMapper.notebookDtoToNotebook(notebookDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{notebookId}")
			.buildAndExpand(notebook.getId())
			.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{notebookId}")
	public NotebookDTO updateNotebook(@RequestBody @Valid NotebookDTO notebookDTO, @PathVariable String notebookId) {
		log.info("Updating notebook id: {}", notebookId);
		Notebook notebook = notebookMapper.notebookDtoToNotebook(notebookDTO);
		notebook.setId(notebookId);
		notebook = notebookService.updateNotebook(notebook);
		return notebookMapper.notebookToNotebookDto(notebook);		
	}

}
