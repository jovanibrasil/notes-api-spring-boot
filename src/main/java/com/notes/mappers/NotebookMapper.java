package com.notes.mappers;

import com.notes.dtos.NotebookDTO;
import com.notes.models.Notebook;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(NotebookMapperDecorator.class)
public interface NotebookMapper {
    @Mapping(target = "title", source = "name")
    Notebook notebookDtoToNotebook(NotebookDTO notebookDTO);
    @Mapping(target = "name", source = "title")
    NotebookDTO notebookToNotebookDto(Notebook notebook);
}
