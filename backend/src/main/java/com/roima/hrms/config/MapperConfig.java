package com.roima.hrms.config;


import com.roima.hrms.dtos.res.TravelEmployeeDto;
import com.roima.hrms.entities.TravelEmployee;
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
                .setSkipNullEnabled(false);
//                .setMatchingStrategy(MatchingStrategies.STRICT)
//                .setPropertyCondition(context -> {
//                    return Hibernate.isInitialized(context.getSource());
//                });

        addMapping(model);

        return model;
    }

    private void addMapping(ModelMapper model) {
        model.typeMap(TravelEmployee.class, TravelEmployeeDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getEmployeeProfile().getId(),
                            TravelEmployeeDto::setId);
                    mapper.map(src -> src.getEmployeeProfile().getFirstName(),
                            TravelEmployeeDto::setFirstName);
                    mapper.map(src->src.getEmployeeProfile().getFirstName(),
                            TravelEmployeeDto::setLastName);
                });
    }


}
