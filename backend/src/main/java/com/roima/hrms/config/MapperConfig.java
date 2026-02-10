package com.roima.hrms.config;


import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper model = new ModelMapper();
        model.getConfiguration()
                .setSkipNullEnabled(false)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(context -> {
                    return Hibernate.isInitialized(context.getSource());
                });
        return model;
    }
}
