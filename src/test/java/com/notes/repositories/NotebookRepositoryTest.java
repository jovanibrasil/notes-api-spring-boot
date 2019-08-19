package com.notes.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.models.Notebook;

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
		assertNotEquals(null, savedNotebook.getId());
	}
	
	@Test
	public void testFindNotebook() {
		notebookRepository.save(notebook);
		Optional<Notebook> savedNotebook = notebookRepository.findById(notebook.getId());
		assertEquals(true, savedNotebook.isPresent());
	}
	
	@Test
	public void testDeleteNotebook() {
		notebook = notebookRepository.save(notebook);
		notebookRepository.delete(notebook);
		Optional<Notebook> deletedNotebook = notebookRepository.findById(notebook.getId());
		assertEquals(false, deletedNotebook.isPresent());
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
