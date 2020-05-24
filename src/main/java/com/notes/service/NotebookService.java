package com.notes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.notes.model.Notebook;

public interface NotebookService {
    Page<Notebook> findAllByUserName(Pageable pageable);
    Notebook findById(String notebookId);
    void deleteById(String notebookId);
    Notebook saveNotebook(Notebook notebook);
	Notebook updateNotebook(Notebook notebook);
}
