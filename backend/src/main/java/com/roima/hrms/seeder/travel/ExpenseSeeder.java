package com.roima.hrms.seeder.travel;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.ExpenseType;
import com.roima.hrms.entities.TravelExpense;
import com.roima.hrms.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Order(4)
public class ExpenseSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final TravelPlanRepository travelPlanRepository;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final TravelExpenseRepository travelExpenseRepository;
    private final TravelTypeRepository travelTypeRepository;

    public ExpenseSeeder(TravelPlanRepository travelPlanRepository, ExpenseTypeRepository expenseTypeRepository, EmployeeProfileRepository employeeProfileRepository, TravelExpenseRepository travelExpenseRepository, TravelTypeRepository travelTypeRepository) {
        this.travelPlanRepository = travelPlanRepository;
        this.expenseTypeRepository = expenseTypeRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.travelExpenseRepository = travelExpenseRepository;
        this.travelTypeRepository = travelTypeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadExpenses();
    }


    public void loadExpenses() {

        if(expenseTypeRepository.existsByName("Food")){
            return;
        }
        ExpenseType food = new ExpenseType();
        food.setName("Food");
        ExpenseType travel = new ExpenseType();
        travel.setName("Travel");
        ExpenseType accommodation = new ExpenseType();
        accommodation.setName("Accommodation");

        expenseTypeRepository.saveAll(Arrays.asList(food, travel, accommodation));

        try {

            EmployeeProfile emp = employeeProfileRepository.findByFirstName("emp").orElseThrow();
            TravelExpense travelExpense = TravelExpense
                    .builder()
                    .travelPlan(travelPlanRepository.findById(2L).orElseThrow())
                    .submittedBy(emp)
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
        } catch (Exception e) {
            log.error("errror", e);
        }
        EmployeeProfile emp = employeeProfileRepository.findByFirstName("emp").orElseThrow();
        TravelExpense travelExpense1 = TravelExpense
                .builder()
                .travelPlan(travelPlanRepository.findById(1L).orElseThrow())
                .submittedBy(emp)
                .submitStatus(true)
                .expenseUploadDate(LocalDate.now())
                .expenseType(travel)
                .expenseAmount(new BigDecimal(1001))
                .expenseDate(LocalDate.now())
                .status("Pending")
                .remark("NO REMARK")
                .statusChangedOn(LocalDateTime.now())
                .statusChangedBy(emp)
                .build();
        travelExpenseRepository.save(travelExpense1);


        EmployeeProfile emp2 = employeeProfileRepository.findByFirstName("employee4").orElseThrow();
        TravelExpense travelExpense3 = TravelExpense
                .builder()
                .travelPlan(travelPlanRepository.findById(2L).orElseThrow())
                .submittedBy(emp2)
                .submitStatus(true)
                .expenseUploadDate(LocalDate.now())
                .expenseType(food)
                .expenseAmount(new BigDecimal(1000))
                .expenseDate(LocalDate.now())
                .status("Pending")
                .remark("NO REMARK")
                .statusChangedOn(LocalDateTime.now())
                .statusChangedBy(emp2)
                .build();
        travelExpenseRepository.save(travelExpense3);
    }
}
