package com.notes.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"prod", "stage"})
@EnableConfigurationProperties(ServiceProperties.class)
@Configuration
public class ServiceVaultConfig {

    private ServiceProperties serviceProperties;

    public ServiceVaultConfig(ServiceProperties serviceProperties){
        this.serviceProperties = serviceProperties;
    }

    @Bean(name = "ServiceProperties")
    public ServiceProperties notesServiceProperties(){
        return this.serviceProperties;
    }

}
