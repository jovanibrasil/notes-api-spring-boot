package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class BlogServiceConfig {

    @Bean
    public BlogServiceProperties blogServiceProperties(){
        BlogServiceProperties config = new BlogServiceProperties();
        config.setUsername("BLOG");
        config.setPassword("123456");
        return config;
    }

}
