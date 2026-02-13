package com.roima.hrms.seeder.travel;

import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.entities.TravelType;
import com.roima.hrms.repository.TravelPlanRepository;
import com.roima.hrms.repository.TravelTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Component
@Order(3)
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

//        if (travelTypeRepository.count() == 0) {
        TravelType travelType1 = new TravelType();
        travelType1.setName("Business");
        travelTypeRepository.save(travelType1);

        TravelType travelType2 = new TravelType();
        travelType2.setName("OffSite");
        travelTypeRepository.save(travelType2);
//        }


//        if (travelPlanRepository.count() == 0) {
        TravelPlan travelPlan1 = TravelPlan.builder()
                .purpose("Travel 1")
                .travelType(travelType1)
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
                .purpose("Travel 2")
                .travelType(travelType2)
                .startDate(LocalDate.parse("2026-02-15"))
                .endDate(LocalDate.parse("2026-02-20"))
                .destinationCity("Banglore")
                .destinationState("Karnataka")
                .destinationCountry("India")
                .lastDateOfExpenseSubmission(LocalDate.parse("2026-02-25"))
                .maxAmountPerDay(BigDecimal.valueOf(2000))
                .build();
        travelPlanRepository.save(travelPlan2);

        TravelPlan travelPlan3 = TravelPlan.builder()
                .purpose("Travel 3")
                .travelType(travelType1)
                .startDate(LocalDate.parse("2026-02-26"))
                .endDate(LocalDate.parse("2026-02-26"))
                .destinationCity("Gandhinagar")
                .destinationState("Gujarat")
                .destinationCountry("India")
                .lastDateOfExpenseSubmission(LocalDate.parse("2026-03-06"))
                .maxAmountPerDay(BigDecimal.valueOf(1000))
                .build();
        travelPlanRepository.save(travelPlan3);

        TravelPlan travelPlan4 = TravelPlan.builder()
                .purpose("Travel 4")
                .travelType(travelType2)
                .startDate(LocalDate.parse("2026-03-01"))
                .endDate(LocalDate.parse("2026-03-10"))
                .destinationCity("New York")
                .destinationState("New York")
                .destinationCountry("USA")
                .lastDateOfExpenseSubmission(LocalDate.parse("2026-03-20"))
                .maxAmountPerDay(BigDecimal.valueOf(6000))
                .build();
        travelPlanRepository.save(travelPlan4);

        TravelPlan travelPlan5 = TravelPlan.builder()
                .purpose("Site visit to warehouse")
                .travelType(travelType1)
                .startDate(LocalDate.parse("2025-06-01"))
                .endDate(LocalDate.parse("2025-06-03"))
                .destinationCity("Stockholm")
                .destinationState("Stockholm County")
                .destinationCountry("Sweden")
                .lastDateOfExpenseSubmission(LocalDate.parse("2025-06-20"))
                .maxAmountPerDay(BigDecimal.valueOf(11000))
                .build();

        travelPlanRepository.save(travelPlan5);


//        }
    }
}
