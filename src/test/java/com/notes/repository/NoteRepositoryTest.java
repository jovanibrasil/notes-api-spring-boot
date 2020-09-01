package com.notes.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.model.Note;
import com.notes.repository.NoteRepository;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class NoteRepositoryTest {

	@Autowired
	private NoteRepository noteRepository;
	
	private Note note;
	
	@Before
	public void setUp() {
		note = new Note();
		note.setLastModifiedOn(LocalDateTime.now());
		note.setNotebookId("nt0");
		note.setText("text");
		note.setTitle("title");
		note.setUserName("jovani");
	}
	
	@After
	public void tearDown() {
		this.noteRepository.deleteAll();
	}
	
	// find note by id
	@Test
	public void findNotebyId() {
		note = noteRepository.save(note);
		Optional<Note> savedNote = noteRepository.findById(note.getId());
		assertEquals(note.getId(), savedNote.get().getId());
	}
	
	// save note
	@Test
	public void saveNote() {
		Note savedNote = noteRepository.save(note);
		assertNotEquals(null, savedNote.getId());
	}
	
	// delete note
	@Test
	public void deleteNote() {
		note = noteRepository.save(note);
		noteRepository.delete(note);
		assertEquals(Optional.empty(), this.noteRepository.findById(note.getId()));
	}
	
	// update note
	@Test
	public void updateNote() {
		note = noteRepository.save(note);
		note.setText("newText");
		Note savedNote = noteRepository.save(note);
		assertEquals("newText", savedNote.getText());
		assertEquals("title", savedNote.getTitle());
	}
	
}
