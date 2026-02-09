package com.roima.hrms.seeder;

import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.entities.TravelType;
import com.roima.hrms.repository.TravelPlanRepository;
import com.roima.hrms.repository.TravelTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TravelSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final TravelPlanRepository travelPlanRepository;
    private final TravelTypeRepository travelTypeRepository;

    public TravelSeeder(TravelPlanRepository travelPlanRepository, TravelTypeRepository travelTypeRepository) {
        this.travelPlanRepository = travelPlanRepository;
        this.travelTypeRepository = travelTypeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadTravels();
    }



    public void loadTravels(){

        TravelType travelType = new TravelType();
        travelType.setName("Business");

        travelTypeRepository.save(travelType);


        TravelPlan travelPlan = TravelPlan.builder()
                .travelType(travelType)
                .startDate(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .endDate(new Date(System.currentTimeMillis()+1000*60*60*24*5))
                .destinationCity("Mumbai")
                .destinationState("Maharashtra")
                .destinationCountry("India")
                .lastDateOfExpenseSubmission(new Date(System.currentTimeMillis()+1000*60*60*24*10))
                .maxAmountPerDay(BigDecimal.valueOf(2000))
                .build();

        travelPlanRepository.save(travelPlan);
    }
}
