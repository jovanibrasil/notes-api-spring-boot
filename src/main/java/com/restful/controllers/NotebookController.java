package com.restful.controllers;

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

import com.restful.dtos.NotebookDTO;
import com.restful.models.Notebook;
import com.restful.services.NotebookService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notebook")
public class NotebookController {

	@Autowired
	private NotebookService notebookService;
	
	@GetMapping("/all")
	public ResponseEntity<List<NotebookDTO>> getAllNotebooks(HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("userName");
		ArrayList<Notebook> notebooks = (ArrayList<Notebook>) notebookService.findAllByUserId(userId);
		List<NotebookDTO> res = notebooks.stream().map(notebook -> {
			return new NotebookDTO(notebook.getId(), notebook.getName());
		}).collect(Collectors.toList());
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{notebookId}")
	public ResponseEntity<NotebookDTO> deleteNotebook(@PathVariable String notebookId) {
		
		Optional<Notebook> optNotebook = this.notebookService.findNotebookById(notebookId);
		
		if(optNotebook.isPresent()) {
			this.notebookService.deleteNotebookById(notebookId);
			Notebook notebook = optNotebook.get();
			return ResponseEntity.ok(new NotebookDTO(notebook.getId(), notebook.getName()));
		}else {
			return null;
		}
		
	}

	@PostMapping()
	public ResponseEntity<NotebookDTO> postNotebook(@RequestBody NotebookDTO notebookDTO, HttpServletRequest request) {
		
		String userId = (String) request.getSession().getAttribute("userName");
		Notebook notebook = new Notebook();
		notebook.setName(notebookDTO.getName());
		notebook.setUserId(userId);
		notebook = this.notebookService.saveNotebook(notebook);
		
		return ResponseEntity.ok(new NotebookDTO(notebook.getId(), notebook.getName()));
	}
	
	@PutMapping()
	public ResponseEntity<NotebookDTO> putNotebook(@RequestBody NotebookDTO notebookDTO, HttpServletRequest request) {
		
		String userId = (String) request.getSession().getAttribute("userName");
		Notebook notebook = new Notebook();
		notebook.setId(notebookDTO.getId());
		notebook.setUserId(userId);
		notebook.setName(notebookDTO.getName());
		notebook.setUserId(userId);
		notebook = this.notebookService.saveNotebook(notebook);
		
		return ResponseEntity.ok(new NotebookDTO(notebook.getId(), notebook.getName()));
	}
	
	

}
