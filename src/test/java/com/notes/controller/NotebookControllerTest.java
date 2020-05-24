package com.notes.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
import com.notes.configuration.security.TempUser;
import com.notes.controller.dto.NotebookDTO;
import com.notes.model.Notebook;
import com.notes.model.enums.ProfileTypeEnum;
import com.notes.service.AuthService;
import com.notes.service.NotebookService;

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
	private AuthService authClient;

	@Mock
	private Principal principal;

	private NotebookDTO notebook1, notebook2;
	private NotebookDTO notebookDto1;
	
	@Before
	public void setUp() {
		notebook1 = new NotebookDTO(null, "name1", "userName");
		notebook2 = new NotebookDTO(null, "name2", "userName");
	
		notebookDto1 = new NotebookDTO(notebook1.getId(), notebook1.getName(), notebook1.getUserName());
		
		when(this.authClient.checkUserToken(Mockito.anyString()))
			.thenReturn(new TempUser("userName", ProfileTypeEnum.ROLE_ADMIN));
	}

	/**
	 *	Get Notebooks
	 */
	
	@Test
	public void testGetListNotebooks() throws Exception {
		when(this.notebookService.findAllByUserName(any()))
		.thenReturn(new PageImpl<>(Arrays.asList(notebook1, notebook2)));
		mvc.perform(MockMvcRequestBuilders.get("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void testGetEmptyListNotebooks() throws Exception {
		when(this.notebookService.findAllByUserName(any()))
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
		when(notebookService.saveNotebook(any())).thenReturn(notebook1);
		mvc.perform(MockMvcRequestBuilders.post("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "x.x.x.x")
				.content(asJsonString(notebookDto1)))			
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", 
						containsString("/notebooks/")));
	}
	
	@Test
	public void testSaveNotebookWithoutAuthHeader() throws Exception {
		when(this.notebookService.findById(notebook1.getId())).thenReturn(notebook1);
		mvc.perform(MockMvcRequestBuilders.post("/notebooks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new Notebook(notebook1.getId(), notebook1.getName(), notebook1.getUserName(), LocalDateTime.now()))))
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
