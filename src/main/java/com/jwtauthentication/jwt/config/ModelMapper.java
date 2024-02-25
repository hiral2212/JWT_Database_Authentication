package com.jwtauthentication.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapper {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    /*The ModelMapper class is a library that helps Java applications 
    map between Java objects and JSON data. In this case, the bean is used to create an instance of the ModelMapper class, which can then be used by other 
    components in the application. */

    
}
