package com.notes.mappers;

import com.notes.dtos.NotebookDTO;
import com.notes.models.Notebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class NotebookMapperDecorator implements NotebookMapper {

    private NotebookMapper notebookMapper;

    @Autowired
    public void setNotebookMapper(NotebookMapper notebookMapper) {
        this.notebookMapper = notebookMapper;
    }

    @Override
    public Notebook notebookDtoToNotebook(NotebookDTO notebookDTO) {
        Notebook notebook = this.notebookMapper.notebookDtoToNotebook(notebookDTO);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        notebook.setUserName(currentUserName);
        return notebook;
    }

    @Override
    public NotebookDTO notebookToNotebookDto(Notebook notebook) {
        NotebookDTO notebookDto = this.notebookMapper.notebookToNotebookDto(notebook);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        notebook.setUserName(currentUserName);
        return notebookDto;
    }
}
