package com.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;


@SpringBootApplication
@EnableMongoRepositories({"com.notes.repositories"})
public class NotesApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NotesApiApplication.class, args);
	}

}
