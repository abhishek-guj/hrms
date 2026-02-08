package com.roima.hrms.config;


import com.roima.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {

    private UserRepository userRepository;

    @Autowired
    public ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Bean
//    public User
}