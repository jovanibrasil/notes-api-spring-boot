package com.restful;

import com.restful.dtos.NoteDTO;
import com.restful.models.Note;

public class Util {

	public static Note convertNoteDTOtoNote(NoteDTO noteDTO) {
		return new Note(
			noteDTO.getNoteId(),
			noteDTO.getTitle(),
			noteDTO.getText(),
			noteDTO.getLastModifiedOn()
		);
	}
	
}
