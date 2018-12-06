package com.restful.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/notebook")
public class NotebookController {

	public ArrayList<Notebook> notebookList;
	Long i = 0L;

	public NotebookController() {
		this.notebookList = new ArrayList<>();

		Notebook nb1 = new Notebook(i++, "Trabalho de conclusão");
		Notebook nb2 = new Notebook(i++, "Idiomas");
		notebookList.add(nb1);
		notebookList.add(nb2);

		Note nt1 = new Note(0L, "Logs", "Procurar por bibliotecas de logs em Python.", new Date());
		Note nt2 = new Note(1L, "Features", "Catalogar as features encontradas na literatura.", new Date());
		Note nt3 = new Note(2L, "Francês", "Procurar um curso na internet.", new Date());

		nb1.addNote(nt1);
		nb1.addNote(nt2);
		nb2.addNote(nt3);

	}

	@GetMapping("/all")
	public ResponseEntity<ArrayList<Notebook>> getAllNotebooks() {
		return ResponseEntity.ok(this.notebookList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Notebook> deleteNotebook(@PathVariable Long id) {
		Iterator<Notebook> iterator = notebookList.iterator();
		while (iterator.hasNext()) { 
			Notebook n = iterator.next(); 
			if(n.getId() == id) { 
				notebookList.remove(n); 
				return ResponseEntity.ok(n);
			}
		}
		// TODO return error state
		return ResponseEntity.ok(null);
	}

	@PostMapping()
	public ResponseEntity<Notebook> postNotebook(@RequestBody Notebook notebook) {
		notebook.setId(i++);
		notebookList.add(notebook);
		return ResponseEntity.ok(notebook);
	}

}
