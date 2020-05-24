package com.notes.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.exception.NotFoundException;
import com.notes.model.Notebook;
import com.notes.repository.NotebookRepository;
import com.notes.service.NotebookService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotebookServiceTest {

	@MockBean
	private NotebookRepository notebookRepository;
	
	@Autowired
	private NotebookService notebookService;
	
	private Notebook notebook1, notebook2;
	private Pageable pageable = PageRequest.of(0, 5);
	
	@Before
	public void setUp() {
		
		notebook1 = new Notebook("notebook1", "name1", "userName1", LocalDateTime.now());
		notebook2 = new Notebook("notebook2", "name2", "userName1", LocalDateTime.now());
		
		when(notebookRepository.findByUserName("userName1", pageable))
			.thenReturn(new PageImpl<>(Arrays.asList(notebook1, notebook2)));
		when(notebookRepository.findByUserName("userName2", pageable))
			.thenReturn(new PageImpl<>(Arrays.asList()));
		
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn("userName");
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	public void testFindNotebookByValidId() {
		when(notebookRepository.findById("notebook1")).thenReturn(Optional.of(notebook1));
		Notebook notebook = notebookService.findById(("notebook1"));
		assertNotNull(notebook);
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindNotebookByInvalidId() {
		when(notebookRepository.findById("notebook1")).thenThrow(NotFoundException.class);
		notebookService.findById("notebook3");
	}
	
	@Test
	public void testSaveNotebook() {
		when(notebookRepository.save(notebook1)).thenReturn(notebook1);	
		Notebook notebook = notebookService.saveNotebook(notebook1);
		assertNotNull(notebook);
	}
	
	@Test
	public void testDeleteNotebook() {
		when(notebookRepository.findById("notebook1")).thenReturn(Optional.of(notebook1));
		
		notebookService.deleteById(notebook1.getId());
	}
	
}
