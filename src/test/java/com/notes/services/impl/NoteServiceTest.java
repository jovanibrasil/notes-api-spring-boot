package com.notes.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.exceptions.NotFoundException;
import com.notes.model.Note;
import com.notes.repositories.NoteRepository;
import com.notes.services.NoteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NoteServiceTest {

	@MockBean
	private NoteRepository noteRepository;
	
	@Autowired
	private NoteService noteService;
	
	private Note note1, note2;
	private Pageable pageable = PageRequest.of(0, 5);
	
	private Page<Note> notesPage = new PageImpl<>(Arrays.asList(note1, note2));
	private Page<Note> emptyNotesPage = new PageImpl<>(Arrays.asList());
	
	@Before
	public void setUp() {	
		note1 = new Note("note1", "title1", "text1", "notebookId", "userName", "rgba(251, 243, 129, 0.74)");
		note2 = new Note("note2", "title2", "text2", "notebookId", "userName", "rgba(251, 243, 129, 0.74)");
		
		
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn("userName");
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
	}
	
	@Test
	public void testFindNotesByUserName(){
		when(noteRepository.findByUserName("userName", pageable)).thenReturn(notesPage);
		Page<Note> notes = noteService.findNotesByUserName(pageable);
		assertEquals(2, notes.getContent().size());
	}
	
	@Test
	public void testFindNotesByUnknownUserName(){
		when(noteRepository.findByUserName("userName", pageable)).thenReturn(emptyNotesPage);
		Page<Note> notes = noteService.findNotesByUserName(pageable);
		assertEquals(0, notes.getContent().size());
	}
	
	@Test
	public void testFindNoteByValidId() {
		when(noteRepository.findById("note2")).thenReturn(Optional.of(note2));
		Note note = noteService.findNoteById(("note2"));
		assertNotNull(note);
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindNoteByUnknownId() {
		when(noteRepository.findById("note3")).thenThrow(NotFoundException.class);
		noteService.findNoteById("note3");
	}
	
	@Test
	public void testFindNotesByValidNotebookId(){
		when(noteRepository.findAllByNotebookId("notebookid", pageable)).thenReturn(notesPage);

		Page<Note> notes = noteService.findNotesByNotebookId("notebookid", pageable);
		assertEquals(2, notes.getContent().size());
	}
	
	@Test
	public void testFindNotesByInvalidNotebookId(){
		when(noteRepository.findAllByNotebookId("notebookidX", pageable)).thenReturn(emptyNotesPage);
		Page<Note> notes = noteService.findNotesByNotebookId("notebookidX", pageable);
		assertEquals(0, notes.getContent().size());
	}
	
	@Test
	public void testSaveNote() {
		when(noteRepository.save(note1)).thenReturn(note1);
		Note note = noteService.saveNote(note1);
		assertNotNull(note);
	}
	
}
