package com.notes.helpers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.notes.dtos.NoteDTO;
import com.notes.models.Note;
import com.notes.models.Notebook;
import com.notes.services.NoteService;
import com.notes.services.NotebookService;

@Component
public class NoteHelper {

	@Autowired
	private NotebookService notebookService;
	
	@Autowired
	private NoteService noteService;
	
	public ValidationResult validateNewNote(Note note, String userName) {
		ValidationResult vr = new ValidationResult();
		Optional<Notebook> nb = notebookService.findNotebookById(note.getNotebookId());
		if(nb.isPresent()) {
			// invalid user reference
			if(!nb.get().getUserName().equals(userName)) {
				vr.addError("The referenced user does not exist.");
			}
		}else {
			// invalid notebook reference: the notebook doesn't exist
			vr.addError("The referenced notebook does not exist. ");
		}
		return vr;
	}
	
	public ValidationResult validateExistentNote(Note note, String userName) {
		ValidationResult vr = new ValidationResult();
		Optional<Note> nt = noteService.findNoteById(note.getId());
		if(!nt.isPresent()) {
			// invalid note: the note doesn't exist
			vr.addError("This note does not exist.");
		}		
		return vr;
	}
	
	public Note convertNoteDTOtoNote(NoteDTO noteDTO, String userName) {
		return new Note(
			noteDTO.getId(),
			noteDTO.getTitle(),
			noteDTO.getText(),
			noteDTO.getNotebookId(),
			userName
		);
	}
	
	public NoteDTO convertNoteToNoteDTO(Note note) {
		return new NoteDTO(
			note.getId(), 
			note.getTitle(), 
			note.getText(), 
			note.getNotebookId(), 
			note.getLastModifiedOn());
	}
	
}
