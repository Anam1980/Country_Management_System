package com.example.Country_Management_System.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    //common piece pf code
    //standarized way to getresttemplate
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder){
        return  restTemplateBuilder.build();
    }

}
