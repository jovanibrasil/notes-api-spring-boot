package com.notes.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import com.notes.controllers.dto.NoteDTO;
import com.notes.model.Note;

@Mapper
@DecoratedWith(NoteMapperDecorator.class)
public interface NoteMapper {
    Note noteDtoToNote(NoteDTO noteDTO);
    NoteDTO noteToNoteDto(Note note);
}
