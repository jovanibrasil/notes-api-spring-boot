package com.notes.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.notes.controller.dto.NoteDTO;
import com.notes.exception.NotFoundException;
import com.notes.mapper.NoteMapper;
import com.notes.model.Note;
import com.notes.repository.NoteRepository;
import com.notes.service.NoteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NoteServiceTest {

	@MockBean
	private NoteRepository noteRepository;
	@MockBean
	private NoteMapper noteMapper;
	@Autowired
	private NoteService noteService;
	
	private Note note1, note2;
	private NoteDTO note1DTO;
	private Pageable pageable = PageRequest.of(0, 5);
	
	private Page<Note> notesPage = new PageImpl<>(Arrays.asList(note1, note2));
	private Page<Note> emptyNotesPage = new PageImpl<>(Arrays.asList());
	
	@Before
	public void setUp() {	
		note1 = new Note("note1", "title1", "text1", "notebookId", "userName", "rgba(251, 243, 129, 0.74)");
		note2 = new Note("note2", "title2", "text2", "notebookId", "userName", "rgba(251, 243, 129, 0.74)");
		
		note1DTO = new NoteDTO();
		
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn("userName");
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
	}
	
	@Test
	public void testFindNotesByUserName(){
		when(noteRepository.findByUserName("userName", pageable)).thenReturn(notesPage);
		Page<NoteDTO> notes = noteService.findNotesByUserName(pageable);
		assertEquals(2, notes.getContent().size());
	}
	
	@Test
	public void testFindNotesByUnknownUserName(){
		when(noteRepository.findByUserName("userName", pageable)).thenReturn(emptyNotesPage);
		Page<NoteDTO> noteDTOList = noteService.findNotesByUserName(pageable);
		assertEquals(0, noteDTOList.getContent().size());
	}
	
	@Test
	public void testFindNoteByValidId() {
		when(noteRepository.findById("note1")).thenReturn(Optional.of(note1));
		when(noteMapper.noteToNoteDto(note1)).thenReturn(note1DTO);
		NoteDTO noteDTO = noteService.findNoteById(("note1"));
		assertNotNull(noteDTO);
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindNoteByUnknownId() {
		when(noteRepository.findById("note3")).thenThrow(NotFoundException.class);
		noteService.findNoteById("note3");
	}
	
	@Test
	public void testFindNotesByValidNotebookId(){
		when(noteRepository.findAllByNotebookId("notebookid", pageable)).thenReturn(notesPage);

		Page<NoteDTO> noteDTOPage = noteService.findNotesByNotebookId("notebookid", pageable);
		assertEquals(2, noteDTOPage.getContent().size());
	}
	
	@Test
	public void testFindNotesByInvalidNotebookId(){
		when(noteRepository.findAllByNotebookId("notebookidX", pageable)).thenReturn(emptyNotesPage);
		Page<NoteDTO> noteDTOPage = noteService.findNotesByNotebookId("notebookidX", pageable);
		assertEquals(0, noteDTOPage.getContent().size());
	}
	
	@Test
	public void testSaveNote() {
		when(noteMapper.noteDtoToNote(any())).thenReturn(note1);
		when(noteMapper.noteToNoteDto(note1)).thenReturn(note1DTO);
		when(noteRepository.save(note1)).thenReturn(note1);
		NoteDTO note = noteService.saveNote(note1DTO);
		assertNotNull(note);
	}
	
}
