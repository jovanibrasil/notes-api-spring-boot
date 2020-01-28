package com.notes.mappers;

import com.notes.dtos.NoteDTO;
import com.notes.models.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteMapperTest {

    @Autowired
    private NoteMapper noteMapper;

    @Test
    public void testNoteMapper(){
        Note note = new Note();
        note.setUserName("fake-user");
        note.setId("fake-id");
        note.setLastModifiedOn(LocalDateTime.now());
        note.setText("fake-text");
        note.setNotebookId("fake-notebook-id");
        note.setTitle("fake-title");
        note.setBackgroundColor("fake-bgc");
        NoteDTO noteDTO = this.noteMapper.noteToNoteDto(note);
        assertNotNull(noteDTO);
    }


}
