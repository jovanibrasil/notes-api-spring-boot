package com.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile({"default", "dev", "test"})
@Configuration
public class ServiceDefaultConfig {

    @Bean(name = "ServiceProperties")
    public ServiceProperties notesServiceProperties(){
        ServiceProperties config = new ServiceProperties();
        // Service authentication information
        config.setUsername("NOTES");
        config.setPassword("123456");
        return config;
    }

}
