package com.notes.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.notes.dtos.NotebookDTO;
import com.notes.models.Notebook;
import com.notes.services.NotebookService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notebook")
public class NotebookController {

	@Autowired
	private NotebookService notebookService;
		
	@GetMapping("/all")
	public ResponseEntity<List<NotebookDTO>> getAllNotebooks(HttpServletRequest request, Principal principal) {
		String currentUserName = principal.getName();
		System.out.println(currentUserName);
		ArrayList<Notebook> notebooks = (ArrayList<Notebook>) notebookService.findAllByUserId(currentUserName);
		List<NotebookDTO> res = notebooks.stream().map(notebook -> {
			return new NotebookDTO(notebook.getId(), notebook.getName(), currentUserName);
		}).collect(Collectors.toList());
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{notebookId}")
	public ResponseEntity<NotebookDTO> deleteNotebook(@PathVariable String notebookId, Principal principal) {
		
		Optional<Notebook> optNotebook = this.notebookService.findNotebookById(notebookId);
		
		if(optNotebook.isPresent()) {
			this.notebookService.deleteNotebookById(notebookId);
			Notebook notebook = optNotebook.get();
			return ResponseEntity.ok(new NotebookDTO(notebook.getId(), notebook.getName(), principal.getName()));
		}else {
			return null;
		}
		
	}

	@PostMapping()
	public ResponseEntity<NotebookDTO> postNotebook(@RequestBody NotebookDTO notebookDTO, HttpServletRequest request, Principal principal) {
		
		String currentUserName = principal.getName();
		Notebook notebook = new Notebook();
		notebook.setName(notebookDTO.getName());
		notebook.setUserName(currentUserName);
		notebook = this.notebookService.saveNotebook(notebook);
		
		return ResponseEntity.ok(new NotebookDTO(notebook.getId(), notebook.getName(), currentUserName));
	}
	
	@PutMapping()
	public ResponseEntity<NotebookDTO> putNotebook(@RequestBody NotebookDTO notebookDTO, HttpServletRequest request, Principal principal) {
		
		String currentUserName = principal.getName();
		
		Notebook notebook = new Notebook();
		notebook.setId(notebookDTO.getId());
		notebook.setUserName(currentUserName);
		notebook.setName(notebookDTO.getName());
		notebook = this.notebookService.saveNotebook(notebook);
		
		return ResponseEntity.ok(new NotebookDTO(notebook.getId(), notebook.getName(), currentUserName));
	}

}
