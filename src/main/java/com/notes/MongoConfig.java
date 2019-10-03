package com.notes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClientURI;

@Profile("prod")
@Configuration
@EnableConfigurationProperties(NotesProperties.class)
public class MongoConfig {

	private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);

	private final NotesProperties configuration;

	public MongoConfig(NotesProperties configuration) {
		this.configuration = configuration;
	}
	
	@Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        log.info("Creating mongo factory ...");
		MongoClientURI clientURI = new MongoClientURI(this.configuration.getUrl());
        return new SimpleMongoDbFactory(clientURI);
    }
	
	/**
	 * 
	 * MongoTemplate is a wrapper with connection information.
	 * 
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Bean
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
            MongoMappingContext context) throws Exception {
		log.info("Generating mongo template ...");
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
	}

}
