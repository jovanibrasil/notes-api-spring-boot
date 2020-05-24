package com.notes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.notes.controller.dto.NotebookDTO;

public interface NotebookService {
    Page<NotebookDTO> findAllByUserName(Pageable pageable);
    NotebookDTO findById(String notebookId);
    void deleteById(String notebookId);
    NotebookDTO saveNotebook(NotebookDTO notebookDTO);
	NotebookDTO updateNotebook(NotebookDTO notebookDTO);
}
