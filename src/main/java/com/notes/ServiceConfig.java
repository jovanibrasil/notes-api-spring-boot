package com.notes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class ServiceConfig {

    @Bean
    public ServiceProperties blogServiceProperties(){
        ServiceProperties config = new ServiceProperties();
        // Service authentication information
        config.setUsername("NOTES");
        config.setPassword("123456");
        return config;
    }

}
