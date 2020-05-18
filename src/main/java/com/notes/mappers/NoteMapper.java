package com.notes.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import com.notes.model.Note;
import com.notes.model.dto.NoteDTO;

@Mapper
@DecoratedWith(NoteMapperDecorator.class)
public interface NoteMapper {
    Note noteDtoToNote(NoteDTO noteDTO);
    NoteDTO noteToNoteDto(Note note);
}
