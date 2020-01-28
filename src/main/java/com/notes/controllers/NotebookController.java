package com.notes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.notes.exceptions.CustomMessageSource;
import com.notes.mappers.NotebookMapper;
import com.notes.services.NoteService;
import com.notes.services.NotebookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.notes.services.models.ErrorDetail;
import com.notes.services.models.Response;
import com.notes.models.Note;
import com.notes.models.Notebook;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notebooks")
public class NotebookController {

	private static final Logger log = LoggerFactory.getLogger(NotebookController.class);

	private NotebookService notebookService;
	private NoteService noteService;
	private CustomMessageSource msgSrc;
	private final NotebookMapper notebookMapper;

	public NotebookController(NotebookService notebookService, NoteService noteService,
							  CustomMessageSource msgSrc, NotebookMapper notebookMapper) {
		this.notebookService = notebookService;
		this.noteService = noteService;
		this.msgSrc = msgSrc;
		this.notebookMapper = notebookMapper;
	}

	/**
	 * Returns a collection of notebooks of a particular user.
	 *
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Response<List<NotebookDTO>>> getAllNotebooks() {
		Response<List<NotebookDTO>> response = new Response<List<NotebookDTO>>();
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Notebook> notebooks = notebookService.findAllByUserName(currentUserName);
		List<NotebookDTO> res = notebooks.stream().map(notebookMapper::notebookToNotebookDto)
				.collect(Collectors.toList());
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
	public ResponseEntity<Response<NotebookDTO>> deleteNotebook(@PathVariable String notebookId) {
		Response<NotebookDTO> response = new Response<NotebookDTO>();
		Optional<Notebook> optNotebook = this.notebookService.findById(notebookId);
		if(optNotebook.isPresent()) {
			// delete all notebook notes
			List<Note> notes = noteService.findNotesByNotebookId(notebookId);
			notes.forEach(n -> { noteService.deleteNote(n.getId()); });
			// then delete the notebook
			this.notebookService.deleteById(notebookId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}else {
			log.error("It was not possible delete the notebook {}.", notebookId);
			response.addError(new ErrorDetail(msgSrc.getMessage("error.notebook.delete")));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
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
		
		Notebook notebook = this.notebookMapper.notebookDtoToNotebook(notebookDTO);
		Optional<Notebook> optNotebook = this.notebookService.saveNotebook(notebook);
		notebook = optNotebook.get();
		response.setData(this.notebookMapper.notebookToNotebookDto(notebook));
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
		
		Optional<Notebook> nt = notebookService.findById(notebookDTO.getId());
		
		if(nt.isPresent()) {
			String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();;
			Notebook notebook = nt.get();
			notebook.setTitle(notebookDTO.getName());

			Optional<Notebook> optNotebook = this.notebookService.saveNotebook(notebook);
			notebook = optNotebook.get();
			response.setData(this.notebookMapper.notebookToNotebookDto(notebook));
			return ResponseEntity.ok(response);
		}
		
		log.error("It was not possible update the notebook {}.", notebookDTO.getId());
		response.addError(new ErrorDetail(msgSrc.getMessage("error.notebook.update")));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		
	}

}
