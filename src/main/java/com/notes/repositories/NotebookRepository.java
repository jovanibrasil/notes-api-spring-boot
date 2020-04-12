package com.notes.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.notes.models.Notebook;

@Repository
public interface NotebookRepository extends MongoRepository<Notebook, String> {

	Page<Notebook> findByUserName(String userName, Pageable pageable);
	Optional<Notebook> findById(String notebookId);
	void deleteById(String notebookId); 
	
}
