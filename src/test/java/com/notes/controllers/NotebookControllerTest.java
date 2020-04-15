package com.notes.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.time.LocalDateTime;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.dtos.NotebookDTO;
import com.notes.enums.ProfileTypeEnum;
import com.notes.mappers.NotebookMapper;
import com.notes.models.Notebook;
import com.notes.security.TempUser;
import com.notes.services.AuthService;
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
	private NotebookMapper notebookMapper;
	@MockBean
	private AuthService authClient;

	@Mock
	private Principal principal;

	private Notebook notebook1, notebook2;
	private NotebookDTO notebookDto1;
	
	@Before
	public void setUp() {
		notebook1 = new Notebook("id1", "name1", "userName", null);
		notebook2 = new Notebook("id2", "name2", "userName", null);
	
		notebookDto1 = new NotebookDTO(notebook1.getId(), notebook1.getTitle(), notebook1.getUserName(), notebook1.getLastModifiedOn());
		
		when(this.authClient.checkUserToken(Mockito.anyString()))
			.thenReturn(new TempUser("userName", ProfileTypeEnum.ROLE_ADMIN));
	}

	/**
	 *	Get Notebooks
	 */
	
	@Test
	public void testGetListNotebooks() throws Exception {
		when(this.notebookService.findAllByUserName(any(), any()))
		.thenReturn(new PageImpl<>(Arrays.asList(notebook1, notebook2)));
		mvc.perform(MockMvcRequestBuilders.get("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testGetEmptyListNotebooks() throws Exception {
		when(this.notebookService.findAllByUserName(any(), any()))
			.thenReturn(new PageImpl<>(Arrays.asList()));
		mvc.perform(MockMvcRequestBuilders.get("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testGetListNotebooksWithoutAuthHeader() throws Exception {
		when(this.authClient.checkUserToken(Mockito.anyString())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.get("/notebooks")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	@Test
	public void testGetListNotebooksWithInvalidToken() throws Exception {
		when(this.authClient.checkUserToken(Mockito.anyString())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.get("/notebooks")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	/**
	 * Delete Notebook
	 * 
	 */
	
	@Test
	public void testDeleteNotebook() throws Exception {	
		doNothing().when(this.notebookService).deleteById("id1");
		mvc.perform(MockMvcRequestBuilders.delete("/notebooks/id1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isNoContent());
	}
		
	@Test
	public void testDeleteNotebookWithoutAuthHeader() throws Exception {	
		mvc.perform(MockMvcRequestBuilders.delete("/notebooks/idX")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	@Test
	public void testDeleteNotebookWithInvalidToken() throws Exception {
		when(this.authClient.checkUserToken(Mockito.anyString())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.delete("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	/**
	 * Save Notebook
	 * 
	 */
	
	@Test
	public void testSaveNotebook() throws Exception {
		when(this.notebookService.saveNotebook(Mockito.any())).thenReturn(notebook1);
		when(notebookMapper.notebookDtoToNotebook(notebookDto1)).thenReturn(notebook1);
		when(notebookMapper.notebookToNotebookDto(notebook1)).thenReturn(notebookDto1);
		mvc.perform(MockMvcRequestBuilders.post("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x")
				.content(asJsonString(notebookDto1)))			
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testSaveNotebookWithoutAuthHeader() throws Exception {
		when(this.notebookService.findById(notebook1.getId())).thenReturn(notebook1);
		mvc.perform(MockMvcRequestBuilders.post("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new Notebook(notebook1.getId(), notebook1.getTitle(), notebook1.getUserName(), LocalDateTime.now()))))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
	}
	
	@Test
	public void testPostNotebookWithInvalidToken() throws Exception {
		when(this.authClient.checkUserToken(Mockito.anyString())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.post("/notebooks").contentType(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$").isEmpty());
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
