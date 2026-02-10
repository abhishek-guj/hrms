package com.roima.hrms.seeder.travel;

import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.entities.TravelType;
import com.roima.hrms.repository.TravelPlanRepository;
import com.roima.hrms.repository.TravelTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
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


    public void loadTravels() {

        TravelType travelType = new TravelType();
//        if (travelTypeRepository.count() == 0) {
            travelType.setId(1L);
            travelType.setName("Business");
            travelTypeRepository.save(travelType);
//        }


//        if (travelPlanRepository.count() == 0) {
            TravelPlan travelPlan1 = TravelPlan.builder()
                    .name("Travel 1")
                    .travelType(travelType)
                    .startDate(LocalDate.parse("2026-02-15"))
                    .endDate(LocalDate.parse("2026-02-23"))
                    .destinationCity("Mumbai")
                    .destinationState("Maharashtra")
                    .destinationCountry("India")
                    .lastDateOfExpenseSubmission(LocalDate.parse("2026-02-28"))
                    .maxAmountPerDay(BigDecimal.valueOf(2000))
                    .build();
            travelPlanRepository.save(travelPlan1);

            TravelPlan travelPlan2 = TravelPlan.builder()
                    .name("Travel 2")
                    .travelType(travelType)
                    .startDate(LocalDate.parse("2026-02-15"))
                    .endDate(LocalDate.parse("2026-02-20"))
                    .destinationCity("Benglore")
                    .destinationState("Karnataka")
                    .destinationCountry("India")
                    .lastDateOfExpenseSubmission(LocalDate.parse("2026-02-25"))
                    .maxAmountPerDay(BigDecimal.valueOf(2000))
                    .build();
            travelPlanRepository.save(travelPlan2);
//        }
    }
}
