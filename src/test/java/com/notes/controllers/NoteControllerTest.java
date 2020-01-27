package com.notes.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.notes.services.AuthService;
import com.notes.services.NoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.enums.ProfileTypeEnum;
import com.notes.helpers.NoteHelper;
import com.notes.helpers.ValidationResult;
import com.notes.models.Note;
import com.notes.security.TempUser;

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

	@MockBean
	private NoteHelper noteHelper;
	
	@Before
	public void setUp() {
		note1 = new Note("noteId1", "noteTitle", "noteText", "notebookId1", "userName", "rgba(251, 243, 129, 0.74)");
		note2 = new Note("noteId2", "noteTitle", "noteText", "notebookId2", "userName", "rgba(251, 243, 129, 0.74)");
		BDDMockito.given(this.noteService.findNotesByUserName("userName")).willReturn(Arrays.asList(note1, note2));
		BDDMockito.given(this.authClient.checkUserToken(Mockito.anyString())).willReturn(new TempUser("userName", ProfileTypeEnum.ROLE_ADMIN));
	}

	/**
	 *	Get Notes  
	 */
	
	@Test
	public void testGetListNotes() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}
	
	@Test
	public void testGetEmptyListNotes() throws Exception {
		BDDMockito.given(this.noteService.findNotesByUserName("userName")).willReturn(Arrays.asList());
		mvc.perform(MockMvcRequestBuilders.get("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testGetListNotesWithoutAuthHeader() throws Exception {
		BDDMockito.given(this.noteService.findNotesByUserName("userName")).willReturn(Arrays.asList());
		mvc.perform(MockMvcRequestBuilders.get("/notes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
//				.andExpect(jsonPath("$.errors").isEmpty())
//				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testGetListNotesWithInvalidToken() throws Exception {
		BDDMockito.given(this.authClient.checkUserToken(Mockito.anyString())).willReturn(null);
		mvc.perform(MockMvcRequestBuilders.get("/notes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
//				.andExpect(jsonPath("$.errors").isEmpty())
//				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	/**
	 * Delete Note
	 * 
	 */
	
	@Test
	public void testDeleteNote() throws Exception {	
		BDDMockito.given(this.noteService.findNoteById("noteId")).willReturn(Optional.of(note1));
		mvc.perform(MockMvcRequestBuilders.delete("/notes/noteId")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testDeleteNotExistentNote() throws Exception {	
		BDDMockito.given(this.noteService.findNoteById("noteIdX")).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.delete("/notes/noteIdX")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errors").isNotEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testDeleteNoteWithoutAuthHeader() throws Exception {	
		mvc.perform(MockMvcRequestBuilders.delete("/notes/noteIdY")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testDeleteNoteWithInvalidToken() throws Exception {
		BDDMockito.given(this.authClient.checkUserToken(Mockito.anyString())).willReturn(null);
		mvc.perform(MockMvcRequestBuilders.delete("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	/**
	 * Save Note
	 * 
	 */
	
	@Test
	public void testSaveNote() throws Exception {
		BDDMockito.given(this.noteService.saveNote(Mockito.any())).willReturn(Optional.of(note1));
		BDDMockito.given(this.noteHelper.convertNoteDTOtoNote(Mockito.any(), 
				Mockito.any())).willReturn(note1);
		BDDMockito.given(noteHelper.validateNewNote(Mockito.any(), Mockito.any()))
		.willReturn(new ValidationResult());
		mvc.perform(MockMvcRequestBuilders.post("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x")
				.content(asJsonString((new NoteHelper(null, null)).convertNoteToNoteDTO(note1))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	public void testSaveNoteWithoutAuthHeader() throws Exception {
		BDDMockito.given(this.noteService.findNoteById("noteIdY")).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.post("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString((new NoteHelper(null, null)).convertNoteToNoteDTO(note2))))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	public void testPostNoteWithInvalidToken() throws Exception {
		BDDMockito.given(this.authClient.checkUserToken(Mockito.anyString())).willReturn(null);
		mvc.perform(MockMvcRequestBuilders.post("/notes").contentType(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
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
