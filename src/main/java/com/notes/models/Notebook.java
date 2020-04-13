package com.notes.models;

import lombok.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
@Document(collection = "notebooks")
public class Notebook {

	@Id
	private String id;
	private String title;
	private String userName;
	private LocalDateTime lastModifiedOn;

}
