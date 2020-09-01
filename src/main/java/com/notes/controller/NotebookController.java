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
import com.notes.controller.dto.NotebookDTO;
import com.notes.service.NoteService;
import com.notes.service.NotebookService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notebooks")
public class NotebookController {

	private final NotebookService notebookService;
	private final NoteService noteService;
	
	@ApiOperation(value = "Busca todos os notebooks de um usuário.", notes = "Usuário é identificado pelo JWT.")
	@ApiResponses({@ApiResponse(code = 200, message = "Resultado encontrado.", response = NotebookDTO.class, responseContainer = "Page")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<NotebookDTO> getAllNotebooks(Pageable pageable) {
		return notebookService.findAllByUserName(pageable);
	}
	
	@ApiOperation(value = "Busca todos os notes de um notebook específico.")
	@ApiResponses({@ApiResponse(code = 200, message = "Resultado encontrado.", response = NoteDTO.class, responseContainer = "Page")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{notebookId}/notes")
	public Page<NoteDTO> getNotesByNotebook(@PathVariable String notebookId, Pageable pageable){
		return noteService.findNotesByNotebookId(notebookId, pageable);
	}

	@ApiOperation("Cria um notebook.")
	@ApiResponses({@ApiResponse(code = 200, message = "Notebook criado com sucesso.", response = Void.class)})
	@PostMapping
	public ResponseEntity<?> createNotebook(@RequestBody @Valid NotebookDTO notebookDTO) {
		log.info("Creating new notebook.");
		notebookDTO = notebookService.saveNotebook(notebookDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{notebookId}")
			.buildAndExpand(notebookDTO.getId())
			.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation("Atualiza notebook.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Notebook atualizado com sucesso.", response = NotebookDTO.class),
		@ApiResponse(code = 404, message = "Notebook não encontrado.")})
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{notebookId}")
	public NotebookDTO updateNotebook(@RequestBody @Valid NotebookDTO notebookDTO, @PathVariable String notebookId) {
		log.info("Updating notebook id: {}", notebookId);
		notebookDTO.setId(notebookId);
		return notebookService.updateNotebook(notebookDTO);		
	}
	
	@ApiOperation(value = "Remove um notebook.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Notebook removido."),
		@ApiResponse(code = 404, message = "Notebook não encontrado.")})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{notebookId}")
	public void deleteNotebook(@PathVariable String notebookId) {
		log.info("Deleting notebook id = {}", notebookId);
		notebookService.deleteById(notebookId);
	}

}
