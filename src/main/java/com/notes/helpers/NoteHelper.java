package com.notes.helpers;

import java.util.Optional;

import com.notes.services.NoteService;
import com.notes.services.NotebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.notes.dtos.NoteDTO;
import com.notes.models.Note;
import com.notes.models.Notebook;

@RequiredArgsConstructor
@Component
public class NoteHelper {

	private final NotebookService notebookService;
	private final NoteService noteService;

	public ValidationResult validateNewNote(Note note) {
		ValidationResult vr = new ValidationResult();
		Optional<Notebook> nb = notebookService.findById(note.getNotebookId());
		if(nb.isPresent()) {
			// invalid user reference
			if(!nb.get().getUserName().equals(note.getUserName())) {
				vr.addError("The referenced user does not exist.");
			}
		}else {
			// invalid notebook reference: the notebook doesn't exist
			vr.addError("The referenced notebook does not exist. ");
		}
		return vr;
	}
	
	public ValidationResult validateExistentNote(Note note) {
		ValidationResult vr = new ValidationResult();
		Optional<Note> nt = noteService.findNoteById(note.getId());
		if(!nt.isPresent()) {
			// invalid note: the note doesn't exist
			vr.addError("This note does not exist.");
		}		
		return vr;
	}

}
