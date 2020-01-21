package com.notes.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Profile({"prod", "stage"})
@ConfigurationProperties("notes-service")
@Getter @Setter
@NoArgsConstructor
public class ServiceProperties {

    private String username;
    private String password;

}
