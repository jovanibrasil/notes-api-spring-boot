package com.notes.services;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
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

import com.notes.models.Note;
import com.notes.repositories.NoteRepository;

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
	
	@Before
	public void setUp() {
		
		note1 = new Note("note1", "title1", "text1", "notebookId", "userName");
		note2 = new Note("note2", "title2", "text2", "notebookId", "userName");
		
		BDDMockito.given(noteRepository.findByUserName("userName"))
			.willReturn(Arrays.asList(note1, note2));
		BDDMockito.given(noteRepository.save(note1))
			.willReturn(note1);
		BDDMockito.given(noteRepository.findById("note2"))
			.willReturn(Optional.of(note2));
		BDDMockito.given(noteRepository.findById("note3"))
			.willReturn(Optional.empty());
		BDDMockito.given(noteRepository.findAllByNotebookId("notebookid"))
			.willReturn(Arrays.asList(note1, note2));
		
	}
	
	@Test
	public void testFindNotesByUserName(){
		List<Note> notes = noteService.findNotesByUserName("userName");
		assertEquals(2, notes.size());
	}
	
	@Test
	public void testFindNotesByUnknownUserName(){
		List<Note> notes = noteService.findNotesByUserName("userNameX");
		assertEquals(0, notes.size());
	}
	
	@Test
	public void testFindNoteByValidId() {
		Optional<Note> note = noteService.findNoteById(("note2"));
		assertEquals(true, note.isPresent());
	}
	
	@Test
	public void testFindNoteByUnknownId() {
		Optional<Note> note = noteService.findNoteById("note3");
		assertEquals(false, note.isPresent());
	}
	
	@Test
	public void testFindNotesByValidNotebookId(){
		List<Note> notes = noteService.findNotesByNotebookId("notebookid");
		assertEquals(2, notes.size());
	}
	
	@Test
	public void testFindNotesByInvalidNotebookId(){
		List<Note> notes = noteService.findNotesByNotebookId("notebookidX");
		assertEquals(0, notes.size());
	}
	
	@Test
	public void testSaveNote() {
		Optional<Note> note = noteService.saveNote(note1);
		assertEquals(true, note.isPresent());
	}
	
}
