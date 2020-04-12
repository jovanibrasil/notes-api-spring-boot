package com.notes.services;

import com.notes.models.Notebook;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotebookService {
    Page<Notebook> findAllByUserName(String userName, Pageable pageable);
    Notebook findById(String notebookId);
    void deleteById(String notebookId);
    Notebook saveNotebook(Notebook notebook);
	Notebook updateNotebook(Notebook notebook);
}
