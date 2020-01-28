package com.notes.mappers;

import com.notes.dtos.NoteDTO;
import com.notes.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class NoteMapperDecorator implements NoteMapper {

    private NoteMapper noteMapper;

    @Autowired
    public void setNoteMapper(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @Override
    public Note noteDtoToNote(NoteDTO noteDTO) {
        Note note = this.noteMapper.noteDtoToNote(noteDTO);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        note.setUserName(currentUserName);
        return note;
    }

    @Override
    public NoteDTO noteToNoteDto(Note note) {
        NoteDTO noteDTO = this.noteMapper.noteToNoteDto(note);
        return noteDTO;
    }
}
