package com.notes.services;

import com.notes.models.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    List<Note> findNotesByUserName(String userName);
    void deleteNote(String noteId);
    Optional<Note> saveNote(Note note);
    Optional<Note> findNoteById(String noteId);
    List<Note> findNotesByNotebookId(String notebookId);
	void deleteNotesByNotebookId(String notebookId);
}
