package com.notes;

import com.notes.dtos.NoteDTO;
import com.notes.models.Note;

public class Util {

	public static Note convertNoteDTOtoNote(NoteDTO noteDTO, String userName) {
		return new Note(
			noteDTO.getId(),
			noteDTO.getTitle(),
			noteDTO.getText(),
			noteDTO.getNotebookId(),
			userName
		);
	}
	
}
