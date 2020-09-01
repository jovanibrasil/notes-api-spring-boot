package com.notes.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.model.Notebook;
import com.notes.repository.NotebookRepository;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NotebookRepositoryTest {
	
	@Autowired
	private NotebookRepository notebookRepository;
	
	private Notebook notebook;
	
	@Before
	public void setUp() {
		notebook = new Notebook();
		notebook.setTitle("notebookTitle");
		notebook.setUserName("userName");
	}
	
	@After
	public void tearDown() {
		notebookRepository.deleteAll();
	}
	
	@Test
	public void testSaveNotebook() {
		Notebook savedNotebook = notebookRepository.save(notebook);
		assertNotNull(savedNotebook.getId());
	}
	
	@Test
	public void testFindNotebook() {
		notebookRepository.save(notebook);
		Optional<Notebook> savedNotebook = notebookRepository.findById(notebook.getId());
		assertTrue(savedNotebook.isPresent());
	}
	
	@Test
	public void testDeleteNotebook() {
		notebook = notebookRepository.save(notebook);
		notebookRepository.delete(notebook);
		Optional<Notebook> deletedNotebook = notebookRepository.findById(notebook.getId());
		assertFalse(deletedNotebook.isPresent());
	}
	
	@Test
	public void testDeleteNotebookById() {
		notebook = notebookRepository.save(notebook);
		notebookRepository.deleteById(notebook.getId());
		Optional<Notebook> deletedNotebook = notebookRepository.findById(notebook.getId());
		assertFalse(deletedNotebook.isPresent());
	}
	
	
	@Test
	public void testFindNotesByNotebookId() {
//		Note note1 = new Note(null, "title1", "text1", "notebookId1", "userName1");
//		Note note2 = new Note(null, "title2", "text2", "notebookId1", "userName2");
//		Note note3 = new Note(null, "title3", "text3", "notebookId1", "userName3");
//		Note note4 = new Note(null, "title4", "text4", "notebookId2", "userName4");
//		
//		note1 = noteRepository.save(note1);
//		note2 = noteRepository.save(note2);
//		note3 = noteRepository.save(note3);
//		note4 = noteRepository.save(note4);
//		
//		notebookRepository.findAllByNotebookId("notebookId1");
		
	}
	
	@Test
	public void testUpdateNotebook() {
		notebook = notebookRepository.save(notebook);
		notebook.setTitle("newTitle");
		Notebook savedUser = notebookRepository.save(notebook);
		assertEquals(notebook.getId(), savedUser.getId());
		assertEquals(notebook.getUserName(), savedUser.getUserName());
		assertEquals("newTitle", notebook.getTitle());
	}
	
}
