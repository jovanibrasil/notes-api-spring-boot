package com.notes.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.notes.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Transactional(readOnly=true)
	@Query("{ 'userName' : ?0 }")
	User findUserByName(String userName);
	
}
