package com.notes.mappers;

import com.notes.dtos.NoteDTO;
import com.notes.models.Note;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(NoteMapperDecorator.class)
public interface NoteMapper {
    Note noteDtoToNote(NoteDTO noteDTO);
    NoteDTO noteToNoteDto(Note note);
}
