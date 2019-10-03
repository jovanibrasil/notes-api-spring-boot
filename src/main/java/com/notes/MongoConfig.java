package com.notes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

//@Profile("prod")
@Configuration
@EnableConfigurationProperties(NotesProperties.class)
public class MongoConfig {

	private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);
	
	private final NotesProperties configuration;

	public MongoConfig(NotesProperties configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * 
	 * MongoTemplate is a wrapper with connection information.
	 * 
	 * 
	 * @return
	 */
	@Bean
	public MongoTemplate getMongoTemplate() {
		MongoClient mongoClient = new MongoClient(this.configuration.getUrl());
		return new MongoTemplate(mongoClient, "notesdb");
	}	

}
