package com.notes.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.notes.models.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	
		public Page<Note> findAllByNotebookId(String notebookId, Pageable pageable);	
		public Page<Note> findByUserName(String userName, Pageable pageable);

		public Note save(Note note);
		public Optional<Note> findById(String noteId);
		@Query("{ 'notebookId' : ?0 }")
		public void deleteById(String noteId);
		
}
