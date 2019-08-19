package com.notes.services;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.models.Notebook;
import com.notes.repositories.NotebookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotebookServiceTest {

	@MockBean
	private NotebookRepository notebookRepository;
	
	@Autowired
	private NotebookService notebookService;
	
	private Notebook notebook1, notebook2;
	
	@Before
	public void setUp() {
		
		notebook1 = new Notebook("notebook1", "name1", "userName1");
		notebook2 = new Notebook("notebook2", "name2", "userName1");
		
		
		BDDMockito.given(notebookRepository.findByUserName("userName1"))
			.willReturn(Arrays.asList(notebook1, notebook2));
		BDDMockito.given(notebookRepository.findByUserName("userName2"))
		.willReturn(Arrays.asList());

		BDDMockito.given(notebookRepository.save(notebook1))
			.willReturn(notebook1);
		BDDMockito.given(notebookRepository.findById("notebook1"))
			.willReturn(Optional.of(notebook1));
		BDDMockito.given(notebookRepository.findById("notebook2"))
			.willReturn(Optional.empty());
	}

	@Test
	public void testFindNotebookByValidId() {
		Optional<Notebook> notebook = notebookService.findNotebookById(("notebook1"));
		assertEquals(true, notebook.isPresent());
	}
	
	@Test
	public void testFindNotebookByUnknownId() {
		Optional<Notebook> note = notebookService.findNotebookById("notebook3");
		assertEquals(false, note.isPresent());
	}
	
	@Test
	public void testSaveNotebook() {
		Optional<Notebook> notebook = notebookService.saveNotebook(notebook1);
		assertEquals(true, notebook.isPresent());
	}
	
	@Test
	public void testDeleteNotebook() {
		notebookService.saveNotebook(notebook1);
		notebookService.deleteNotebookById(notebook1.getId());
		Optional<Notebook> notebook = notebookService.findNotebookById(notebook1.getId());
		assertEquals(true, notebook.isPresent());
	}
	
	
}
