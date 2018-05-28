package com.zhy.gateway.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jackson {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
