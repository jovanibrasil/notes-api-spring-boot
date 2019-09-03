package com.notes;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.notes.integrations.AccessToken;


@SpringBootApplication
@EnableMongoRepositories({"com.notes.repositories"})
public class NotesApiApplication extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(NotesApiApplication.class);
		
	public static void main(String[] args) {
		SpringApplication.run(NotesApiApplication.class, args);
	}
	
	@PostConstruct
	private void init() {
		log.debug("Initialization logic ...");
		try {
			String token = AccessToken.getToken();
			log.info("Token: {}", token);
		} catch (Exception e) {
			log.info("Error. " + e.getMessage());
		}
		
	}

}
