package com.notes.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.notes.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {	
	Page<Note> findAllByNotebookId(String notebookId, Pageable pageable);	
	Page<Note> findByUserName(String userName, Pageable pageable);
	Optional<Note> findById(String noteId);
}
