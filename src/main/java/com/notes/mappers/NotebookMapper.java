package com.notes.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.notes.model.Notebook;
import com.notes.model.dto.NotebookDTO;

@Mapper
@DecoratedWith(NotebookMapperDecorator.class)
public interface NotebookMapper {
    @Mapping(target = "title", source = "name")
    Notebook notebookDtoToNotebook(NotebookDTO notebookDTO);
    @Mapping(target = "name", source = "title")
    NotebookDTO notebookToNotebookDto(Notebook notebook);
}
