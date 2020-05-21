package com.notes.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.notes.controllers.dto.NoteDTO;
import com.notes.model.Note;

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

}
