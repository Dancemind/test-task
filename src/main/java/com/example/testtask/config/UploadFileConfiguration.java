package com.example.testtask.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;


@Configuration
public class UploadFileConfiguration {
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("256KB"));
        factory.setMaxRequestSize(DataSize.parse("512KB"));
        return factory.createMultipartConfig();
    }
}

