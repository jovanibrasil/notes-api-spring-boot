package com.restful;

import com.restful.dtos.NoteDTO;
import com.restful.models.Note;

public class Util {

	public static Note convertNoteDTOtoNote(NoteDTO noteDTO, String userId) {
		return new Note(
			noteDTO.getId(),
			noteDTO.getTitle(),
			noteDTO.getText(),
			noteDTO.getNotebookId(),
			userId
		);
	}
	
}
