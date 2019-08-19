package com.notes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.notes.models.Notebook;

@Repository
public interface NotebookRepository extends MongoRepository<Notebook, String> {

	List<Notebook> findByUserName(String userName);
	Optional<Notebook> findById(String notebookId);
	void deleteById(String notebookId); 
	
}
