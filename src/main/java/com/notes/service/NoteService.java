package com.notes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.notes.controller.dto.NoteDTO;

public interface NoteService {
    Page<NoteDTO> findNotesByUserName(Pageable pageable);
    Page<NoteDTO> findNotesByNotebookId(String notebookId, Pageable pageable);
	void deleteNote(String noteId);
    NoteDTO saveNote(NoteDTO noteDTO);
    NoteDTO findNoteById(String noteId);
    void deleteNotesByNotebookId(String notebookId);
}
