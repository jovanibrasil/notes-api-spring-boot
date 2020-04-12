package com.notes.controllers;

import java.util.ArrayList;

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
import com.notes.services.models.Response;

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
	public ResponseEntity<Response<Page<NotebookDTO>>> getAllNotebooks(Pageable pageable) {
		Response<Page<NotebookDTO>> response = new Response<>();
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		Page<Notebook> notebooks = notebookService.findAllByUserName(currentUserName, pageable);
		Page<NotebookDTO> res = notebooks.map(notebookMapper::notebookToNotebookDto);
		response.setData(res);
		return ResponseEntity.ok(response);
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
	public ResponseEntity<Response<ArrayList<Note>>> getNotesByNotebook(@PathVariable String notebookId){
		Response<ArrayList<Note>> response = new Response<ArrayList<Note>>();
		ArrayList<Note> notes = (ArrayList<Note>) this.noteService.findNotesByNotebookId(notebookId);
		response.setData(notes);
		return ResponseEntity.ok(response);
	}

	/**
	 * Saves a new notebook.
	 * 
	 * @param notebookDTO
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response<NotebookDTO>> createNotebook(@RequestBody @Valid NotebookDTO notebookDTO) {
		Response<NotebookDTO> response = new Response<NotebookDTO>();
		
		Notebook notebook = notebookMapper.notebookDtoToNotebook(notebookDTO);
		notebook = notebookService.saveNotebook(notebook);
		response.setData(notebookMapper.notebookToNotebookDto(notebook));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/**
	 * Updated an existent notebook.
	 * 
	 * @param notebookDTO
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Response<NotebookDTO>> updateNotebook(@RequestBody @Valid NotebookDTO notebookDTO) {
		Response<NotebookDTO> response = new Response<NotebookDTO>();
		Notebook notebook = notebookMapper.notebookDtoToNotebook(notebookDTO);
		notebook = notebookService.updateNotebook(notebook);
		response.setData(notebookMapper.notebookToNotebookDto(notebook));
		return ResponseEntity.ok(response);		
	}

}
