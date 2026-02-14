package com.roima.hrms.seeder.travel;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.ExpenseType;
import com.roima.hrms.entities.TravelExpense;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.TravelExpenseRepository;
import com.roima.hrms.repository.TravelPlanRepository;
import com.roima.hrms.repository.ExpenseTypeRepository;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Order(4)
public class ExpenseSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final TravelPlanRepository travelPlanRepository;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final TravelExpenseRepository travelExpenseRepository;

    public ExpenseSeeder(TravelPlanRepository travelPlanRepository, ExpenseTypeRepository expenseTypeRepository, EmployeeProfileRepository employeeProfileRepository, TravelExpenseRepository travelExpenseRepository) {
        this.travelPlanRepository = travelPlanRepository;
        this.expenseTypeRepository = expenseTypeRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.travelExpenseRepository = travelExpenseRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadExpenses();
    }


    public void loadExpenses() {

        ExpenseType food = new ExpenseType();
        food.setName("Food");
        ExpenseType travel = new ExpenseType();
        travel.setName("Travel");
        ExpenseType accomodation = new ExpenseType();
        accomodation.setName("Accomodation");

        expenseTypeRepository.saveAll(Arrays.asList(food, travel, accomodation));


        EmployeeProfile emp = employeeProfileRepository.findByFirstName("ad").orElseThrow();
        TravelExpense travelExpense = TravelExpense
                .builder()
                .travelPlan(travelPlanRepository.findById(2L).orElseThrow())
                .employeeProfile(emp)
                .submitStatus(true)
                .expenseUploadDate(LocalDate.now())
                .expenseType(food)
                .expenseAmount(new BigDecimal(1000))
                .expenseDate(LocalDate.now())
                .status("Pending")
                .remark("NO REMARK")
                .statusChangedOn(LocalDateTime.now())
                .statusChangedBy(emp)
                .build();
        travelExpenseRepository.save(travelExpense);
    }
}
