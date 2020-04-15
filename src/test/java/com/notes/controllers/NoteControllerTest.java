package com.notes.controllers;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.notes.dtos.NoteDTO;
import com.notes.enums.ProfileTypeEnum;
import com.notes.exceptions.NotFoundException;
import com.notes.mappers.NoteMapper;
import com.notes.models.Note;
import com.notes.security.TempUser;
import com.notes.services.AuthService;
import com.notes.services.NoteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NoteControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private NoteService noteService;

	@MockBean
	private AuthService authClient;

	@Mock
	private Principal principal;

	private Note note1, note2;
	private NoteDTO noteDto1, noteDto2;

	private Page<Note> notesPage;
	
	@Autowired
	private NoteMapper noteMapper;

	@Before
	public void setUp() {
		note1 = new Note("noteId1", "noteTitle", "noteText",
				"notebookId1", "userName", "rgba(251, 243, 129, 0.74)");
		note2 = new Note("noteId2", "noteTitle", "noteText",
				"notebookId2", "userName", "rgba(251, 243, 129, 0.74)");

		noteDto1 = noteMapper.noteToNoteDto(note1);
		noteDto1 = noteMapper.noteToNoteDto(note2);

		notesPage = new PageImpl<>(Arrays.asList(note1, note2));
		
		when(authClient.checkUserToken(Mockito.anyString())).thenReturn(new TempUser("userName", ProfileTypeEnum.ROLE_ADMIN));
	}

	/**
	 *	Get Notes  
	 */
	
	@Test
	public void testGetListNotes() throws Exception {
		when(noteService.findNotesByUserName(any())).thenReturn(notesPage);
		mvc.perform(MockMvcRequestBuilders.get("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testGetEmptyListNotes() throws Exception {
		when(noteService.findNotesByUserName(any())).thenReturn(new PageImpl<>(Arrays.asList()));
		mvc.perform(MockMvcRequestBuilders.get("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements", equalTo(0)));
	}
	
	@Test
	public void testGetListNotesWithoutAuthHeader() throws Exception {
		when(authClient.checkUserToken(Mockito.anyString()))
			.thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.get("/notes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());;
	}
	
	@Test
	public void testGetListNotesWithInvalidToken() throws Exception {
		when(authClient.checkUserToken(Mockito.anyString())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.get("/notes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	/**
	 * Delete Note
	 * 
	 */
	
	@Test
	public void testDeleteNote() throws Exception {	
		doNothing().when(noteService).deleteNote("noteId");
		mvc.perform(MockMvcRequestBuilders.delete("/notes/noteId")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void testDeleteNotExistentNote() throws Exception {	
		doThrow(NotFoundException.class).when(noteService).deleteNote(any());
		mvc.perform(MockMvcRequestBuilders.delete("/notes/noteIdX")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testDeleteNoteWithoutAuthHeader() throws Exception {	
		mvc.perform(MockMvcRequestBuilders.delete("/notes/noteIdY")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	@Test
	public void testDeleteNoteWithInvalidToken() throws Exception {
		when(authClient.checkUserToken(Mockito.anyString())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.delete("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	/**
	 * Save Note
	 * 
	 */
	
	@Test
	public void testSaveNote() throws Exception {
		when(noteService.saveNote(Mockito.any()))
			.thenReturn(note1);
		mvc.perform(MockMvcRequestBuilders.post("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x")
				.content(asJsonString(noteDto1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testSaveNoteWithoutAuthHeader() throws Exception {
		when(authClient.checkUserToken(Mockito.anyString()))
			.thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.post("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(noteDto2)))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	@Test
	public void testPostNoteWithInvalidToken() throws Exception {
		when(authClient.checkUserToken(Mockito.anyString())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.post("/notes").contentType(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
