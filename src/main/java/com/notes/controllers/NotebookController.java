package com.notes.controllers;

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

import com.notes.dtos.NotebookDTO;
import com.notes.mappers.NotebookMapper;
import com.notes.models.Note;
import com.notes.models.Notebook;
import com.notes.services.NoteService;
import com.notes.services.NotebookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notebooks")
public class NotebookController {

	private final NotebookService notebookService;
	private final NoteService noteService;
	private final NotebookMapper notebookMapper;

	/**
	 * Returns a collection of notebooks of a particular user.
	 *
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Page<NotebookDTO>> getAllNotebooks(Pageable pageable) {
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		Page<Notebook> notebooks = notebookService.findAllByUserName(currentUserName, pageable);
		Page<NotebookDTO> res = notebooks.map(notebookMapper::notebookToNotebookDto);
		return ResponseEntity.ok(res);
	}

	/**
	 * Deletes a notebook by a particular id.
	 * 
	 * @param notebookId
	 * @return
	 */
	@DeleteMapping("/{notebookId}")
	public ResponseEntity<?> deleteNotebook(@PathVariable String notebookId) {
			this.notebookService.deleteById(notebookId);
			return ResponseEntity.noContent().build();
	}
	
	/**
	 * Returns a list notes of a specified notebook.
	 * 
	 * @param notebookId
	 * @return
	 */
	@GetMapping("/{notebookId}/notes")
	public ResponseEntity<Page<Note>> getNotesByNotebook(@PathVariable String notebookId, Pageable pageable){
		Page<Note> notes = this.noteService.findNotesByNotebookId(notebookId, pageable);
		return ResponseEntity.ok(notes);
	}

	/**
	 * Saves a new notebook.
	 * 
	 * @param notebookDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<NotebookDTO> createNotebook(@RequestBody @Valid NotebookDTO notebookDTO) {
		Notebook notebook = notebookMapper.notebookDtoToNotebook(notebookDTO);
		notebook = notebookService.saveNotebook(notebook);
		NotebookDTO notebookDto = notebookMapper.notebookToNotebookDto(notebook);
		return ResponseEntity.status(HttpStatus.CREATED).body(notebookDto);
	}
	
	/**
	 * Updated an existent notebook.
	 * 
	 * @param notebookDTO
	 * @return
	 */
	@PutMapping
	public ResponseEntity<NotebookDTO> updateNotebook(@RequestBody @Valid NotebookDTO notebookDTO) {
		Notebook notebook = notebookMapper.notebookDtoToNotebook(notebookDTO);
		notebook = notebookService.updateNotebook(notebook);
		notebookDTO = notebookMapper.notebookToNotebookDto(notebook);
		return ResponseEntity.ok(notebookDTO);		
	}

}
