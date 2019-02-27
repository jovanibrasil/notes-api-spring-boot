package com.restful.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restful.models.Notebook;

@Repository
public interface NotebookRepository extends MongoRepository<Notebook, Long> {

	List<Notebook> findByUserId(String userId);
	Optional<Notebook> findById(String notebookId);
	void deleteById(String notebookId); 
	
}
