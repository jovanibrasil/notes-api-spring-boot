package com.notes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.notes.models.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, Long> {
	
		public Note save(Note note);
		public Optional<Note> findById(String noteId);
		@Query("{ 'notebookId' : ?0 }")
		public List<Note> findAllByNotebookId(String notebookId);
		public void deleteById(String noteId);
		public List<Note> findByUserName(String userName);
		
}
