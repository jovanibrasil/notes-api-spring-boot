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
import com.notes.service.NoteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

	private final NoteService noteService;

	@ApiOperation(value = "Busca notes de um usuário.")
	@ApiResponses({@ApiResponse(code = 200, message = "Notes encontrados.", response = NoteDTO.class, responseContainer = "Page")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<NoteDTO> getNotes(Pageable pageable) {
		return noteService.findNotesByUserName(pageable);
	}
		
	@ApiOperation("Cria uma nota.")
	@ApiResponses({@ApiResponse(code = 200, message = "Nota criada com sucesso.", response = Void.class)})
	@PostMapping
	public ResponseEntity<?> saveNote(@Valid @RequestBody NoteDTO noteDTO) {
		log.info("Saving note.");
		noteDTO = noteService.saveNote(noteDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{noteId}")
				.buildAndExpand(noteDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation("Atualiza note.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Note atualizado com sucesso.", response = NoteDTO.class),
		@ApiResponse(code = 404, message = "Note não encontrado.")})
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{noteId}")
	public NoteDTO updateNote(@RequestBody @Valid NoteDTO noteDTO, @PathVariable String noteId) {
		log.info("Updating note id: {}", noteId);
		noteDTO.setId(noteId);
		return noteService.saveNote(noteDTO);
	}
	
	@ApiOperation(value = "Remove um note.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Note removida."),
		@ApiResponse(code = 404, message = "Note não encontrada.")})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{noteId}")
	public void deleteNote(@PathVariable String noteId){
		log.info("Deleting note {}.", noteId);
		noteService.deleteNote(noteId);
	}
		
}
