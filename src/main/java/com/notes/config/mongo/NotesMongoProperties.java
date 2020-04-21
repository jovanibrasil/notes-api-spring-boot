package com.notes.config.mongo;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Profile({"prod", "stage"})
@Getter @Setter
@ConfigurationProperties("notes-db")
public class NotesMongoProperties {
	private String url;
}
