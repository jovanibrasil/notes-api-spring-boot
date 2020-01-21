package com.notes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Profile({"prod", "stage"})
@ConfigurationProperties("notes-cred")
@Getter @Setter
public class NotesMongoProperties {
	private String url;
}
