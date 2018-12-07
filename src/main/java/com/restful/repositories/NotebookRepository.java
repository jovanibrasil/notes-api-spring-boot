package com.restful.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restful.models.Notebook;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long> {
	
}
