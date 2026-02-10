package com.roima.hrms.seeder;

import com.roima.hrms.entities.TravelDocumentType;
import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.entities.TravelDocumentType;
import com.roima.hrms.repository.TravelPlanRepository;
import com.roima.hrms.repository.TravelDocumentTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@Component
public class TravelDocumentSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final TravelPlanRepository travelPlanRepository;
    private final TravelDocumentTypeRepository travelDocumentTypeRepository;

    public TravelDocumentSeeder(TravelPlanRepository travelPlanRepository, TravelDocumentTypeRepository travelDocumentTypeRepository) {
        this.travelPlanRepository = travelPlanRepository;
        this.travelDocumentTypeRepository = travelDocumentTypeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadTravels();
    }


    public void loadTravels() {

        TravelDocumentType flightTicket = new TravelDocumentType();
        flightTicket.setName("Flight Tickets");
        TravelDocumentType trainTicket = new TravelDocumentType();
        trainTicket.setName("Train Tickets");
        TravelDocumentType visaLetter = new TravelDocumentType();
        visaLetter.setName("Visa Letter");
        TravelDocumentType policy = new TravelDocumentType();
        policy.setName("Policy");

        travelDocumentTypeRepository.saveAll(Arrays.asList(flightTicket, trainTicket, visaLetter, policy));

    }
}
