package com.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@SpringBootApplication
@EnableMongoRepositories({"com.notes.repositories"})
public class NotesApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NotesApiApplication.class, args);
	}

	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource);
		return validator;
	}

}
