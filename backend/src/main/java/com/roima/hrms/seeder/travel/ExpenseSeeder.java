package com.roima.hrms.seeder.travel;

import com.roima.hrms.entities.ExpenseType;
import com.roima.hrms.repository.TravelPlanRepository;
import com.roima.hrms.repository.ExpenseTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ExpenseSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final TravelPlanRepository travelPlanRepository;
    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseSeeder(TravelPlanRepository travelPlanRepository, ExpenseTypeRepository expenseTypeRepository) {
        this.travelPlanRepository = travelPlanRepository;
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadTravels();
    }


    public void loadTravels() {

        if (expenseTypeRepository.count() == 0) {
            ExpenseType food = new ExpenseType();
            food.setName("Food");
            ExpenseType travel = new ExpenseType();
            travel.setName("Travel");
            ExpenseType accomodation = new ExpenseType();
            accomodation.setName("Accomodation");

            expenseTypeRepository.saveAll(Arrays.asList(food, travel, accomodation));
        }
    }
}
