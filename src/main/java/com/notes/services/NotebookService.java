package com.notes.services;

import com.notes.models.Notebook;

import java.util.List;
import java.util.Optional;

public interface NotebookService {
    List<Notebook> findAllByUserName(String userName);
    Optional<Notebook> findById(String notebookId);
    void deleteById(String notebookId);
    Optional<Notebook> saveNotebook(Notebook notebook);
}
