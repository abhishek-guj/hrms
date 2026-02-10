package com.roima.hrms.seeder;

import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.entities.ExpenseType;
import com.roima.hrms.repository.TravelPlanRepository;
import com.roima.hrms.repository.ExpenseTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

        ExpenseType food = new ExpenseType();
        food.setName("Food");
        ExpenseType travel = new ExpenseType();
        travel.setName("Travel");
        ExpenseType accomodation = new ExpenseType();
        accomodation.setName("Accomodation");

        expenseTypeRepository.saveAll(Arrays.asList(food, travel, accomodation));
    }
}
