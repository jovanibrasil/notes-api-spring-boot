package com.restful.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restful.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	
	//@Query("SELECT p FROM Post p LEFT JOIN FETCH p.author ORDER BY p.date DESC")
	// nativeQuery=true - disable jpa query verification. 
	@Query(value="SELECT * FROM notes n JOIN notebooks nb "
			+ "on n.notebook_id = nb.notebook_id "
			+ "WHERE nb.notebook_id=:notebookId", nativeQuery=true)
	List<Note> getNotesByNotebook(@Param("notebookId") Long notebookId);
	
}
