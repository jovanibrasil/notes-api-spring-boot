package com.restful.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restful.models.Note;
import com.restful.models.Notebook;
import com.restful.repositories.NotebookRepository;
import com.restful.services.NotebookService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/notebook")
public class NotebookController {

	// Note nt1 = new Note(0L, "Logs", "Procurar por bibliotecas de logs em Python.", new Date());
	// Note nt2 = new Note(1L, "Features", "Catalogar as features encontradas na literatura.", new Date());
	// Note nt3 = new Note(2L, "Francês", "Procurar um curso na internet.", new Date());

	@Autowired
	private NotebookService notebookService;
	
	@GetMapping("/all")
	public ResponseEntity<ArrayList<Notebook>> getAllNotebooks() {
		
		ArrayList<Notebook> notebooks = (ArrayList<Notebook>) notebookService.findAll();
		
		return ResponseEntity.ok(notebooks);
	}

	@DeleteMapping("/{notebookId}")
	public ResponseEntity<Notebook> deleteNotebook(@PathVariable Long notebookId) {
//		Iterator<Notebook> iterator = notebookList.iterator();
//		while (iterator.hasNext()) { 
//			Notebook n = iterator.next(); 
//			if(n.getId() == id) { 
//				notebookList.remove(n); 
//				return ResponseEntity.ok(n);
//			}
//		}
		Notebook nb = this.notebookService.findNote(notebookId);
		this.notebookService.deleteNotebook(notebookId);
		return ResponseEntity.ok(nb);
	}

	@PostMapping()
	public ResponseEntity<Notebook> postNotebook(@RequestBody Notebook notebook) {
		//notebook.setId(i++);
		//notebookList.add(notebook);
		//System.out.println("saving notebook "+notebook.toString());
		//Notebook nb = new Notebook(0L, "Hello");
		//otebookRepository.save(notebook);
		Notebook nb = this.notebookService.saveNotebook(notebook);
		System.out.println(nb);
		return ResponseEntity.ok(nb);
	}

}
