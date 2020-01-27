package com.notes.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

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
import com.notes.services.AuthServiceImpl;
import com.notes.models.Notebook;
import com.notes.security.TempUser;
import com.notes.services.NotebookService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotebookControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private NotebookService notebookService;

	@MockBean
	private AuthServiceImpl authClient;

	@Mock
	private Principal principal;

	private Notebook notebook1, notebook2;
	
	@MockBean
	private NoteHelper noteHelper;
	
	@Before
	public void setUp() {
		notebook1 = new Notebook("id1", "name1", "userName");
		notebook2 = new Notebook("id2", "name2", "userName");
		BDDMockito.given(this.notebookService.findAllByUserId("userName"))
			.willReturn(Arrays.asList(notebook1, notebook2));
		BDDMockito.given(this.authClient.checkUserToken(Mockito.anyString()))
			.willReturn(new TempUser("userName", ProfileTypeEnum.ROLE_ADMIN));
	}

	/**
	 *	Get Notebooks
	 */
	
	@Test
	public void testGetListNotebooks() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}
	
	@Test
	public void testGetEmptyListNotebooks() throws Exception {
		BDDMockito.given(this.notebookService.findAllByUserId("userName")).willReturn(Arrays.asList());
		mvc.perform(MockMvcRequestBuilders.get("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testGetListNotebooksWithoutAuthHeader() throws Exception {
		BDDMockito.given(this.notebookService.findAllByUserId("userName")).willReturn(Arrays.asList());
		mvc.perform(MockMvcRequestBuilders.get("/notebooks")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testGetListNotebooksWithInvalidToken() throws Exception {
		BDDMockito.given(this.authClient.checkUserToken(Mockito.anyString())).willReturn(null);
		mvc.perform(MockMvcRequestBuilders.get("/notebooks")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	/**
	 * Delete Notebook
	 * 
	 */
	
	@Test
	public void testDeleteNotebook() throws Exception {	
		BDDMockito.given(this.notebookService.findNotebookById("id1")).willReturn(Optional.of(notebook1));
		mvc.perform(MockMvcRequestBuilders.delete("/notebooks/id1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testDeleteNotebookExistentNote() throws Exception {	
		BDDMockito.given(this.notebookService.findNotebookById("noteIdX")).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.delete("/notebooks/idX")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").isNotEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testDeleteNotebookWithoutAuthHeader() throws Exception {	
		mvc.perform(MockMvcRequestBuilders.delete("/notebooks/idX")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testDeleteNotebookWithInvalidToken() throws Exception {
		BDDMockito.given(this.authClient.checkUserToken(Mockito.anyString())).willReturn(null);
		mvc.perform(MockMvcRequestBuilders.delete("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	/**
	 * Save Notebook
	 * 
	 */
	
	@Test
	public void testSaveNotebook() throws Exception {
		BDDMockito.given(this.notebookService.saveNotebook(Mockito.any())).willReturn(Optional.of(notebook1));
		mvc.perform(MockMvcRequestBuilders.post("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x")
				.content(asJsonString(new Notebook(notebook1.getId(), notebook1.getTitle(), notebook1.getUserName()))))			
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	public void testSaveNotebookWithoutAuthHeader() throws Exception {
		BDDMockito.given(this.notebookService.findNotebookById(notebook1.getId())).willReturn(Optional.of(notebook1));
		mvc.perform(MockMvcRequestBuilders.post("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new Notebook(notebook1.getId(), notebook1.getTitle(), notebook1.getUserName()))))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	public void testPostNotebookWithInvalidToken() throws Exception {
		BDDMockito.given(this.authClient.checkUserToken(Mockito.anyString())).willReturn(null);
		mvc.perform(MockMvcRequestBuilders.post("/notebooks").contentType(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
