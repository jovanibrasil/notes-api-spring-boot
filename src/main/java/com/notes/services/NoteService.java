package com.notes.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.notes.model.Note;

public interface NoteService {
    Page<Note> findNotesByUserName(Pageable pageable);
    Page<Note> findNotesByNotebookId(String notebookId, Pageable pageable);
	void deleteNote(String noteId);
    Note saveNote(Note note);
    Note findNoteById(String noteId);
    void deleteNotesByNotebookId(String notebookId);
}
