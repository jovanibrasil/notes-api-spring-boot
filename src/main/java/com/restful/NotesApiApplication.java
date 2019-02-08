package com.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class NotesApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NotesApiApplication.class, args);
	}
}
