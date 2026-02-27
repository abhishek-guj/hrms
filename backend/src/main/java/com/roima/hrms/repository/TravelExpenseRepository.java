package com.roima.hrms.repository;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.TravelExpense;
import com.roima.hrms.entities.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TravelExpenseRepository extends JpaRepository<TravelExpense, Long> {

        @Query(value = "select te from TravelExpense te where te.id = :id and te.travelPlan.isDeleted = false ")
        Optional<TravelExpense> findById(Long id);

        // ----------------------------------------
        // GETTING ALL EXPENSES WITHOUT ANY TRAVEL ID
        // ----------------------------------------
        // for admin hr
        @Override
        @Query(value = "SELECT te,tp From TravelExpense te join fetch te.travelPlan tp where tp.isDeleted = false order by te.id desc")
        List<TravelExpense> findAll();

        // get all expenses under this manager
        @Query(value = "select te from TravelExpense te join te.submittedBy ep where ep.manager.id = :managerId and te.travelPlan.isDeleted = false order by te.submittedBy.id, te.expenseUploadDate")
        List<TravelExpense> findAllByManagerId(Long managerId);

        // get all expenses of this employee
        @Query("select te from TravelExpense te join te.submittedBy ep where ep.id = :employeeId and te.travelPlan.isDeleted = false order by te.submittedBy.id, te.expenseUploadDate")
        List<TravelExpense> findAllByEmployeeId(Long employeeId);
        // ----------------------------------------
        // GETTING ALL EXPENSES WITHOUT ANY TRAVEL ID
        // ----------------------------------------

        //
        //
        //
        // ----------------------------------------
        // GETTING ALL EXPENSES BY ANY TRAVEL ID
        // ----------------------------------------
        @Query("SELECT te,tp From TravelExpense te join fetch te.travelPlan tp where tp.id = :travelPlanId and tp.isDeleted = false order by te.id desc")
        List<TravelExpense> findAllByTravelId(Long travelPlanId);

        @Query(value = "select te from TravelExpense te join te.submittedBy ep where ep.manager.id = :managerId and te.travelPlan.id = :travelPlanId and te.travelPlan.isDeleted = false order by te.id desc")
        List<TravelExpense> findAllByManagerIdAndTravelId(Long managerId, Long travelPlanId);

        @Query("select te from TravelExpense te join te.submittedBy ep where ep.id = :employeeId and te.travelPlan.id = :travelPlanId and te.travelPlan.isDeleted = false order by te.id desc")
        List<TravelExpense> findAllByEmployeeIdAndTravelId(Long employeeId, Long travelPlanId);
        // ----------------------------------------
        // GETTING ALL EXPENSES BY ANY TRAVEL ID
        // ----------------------------------------

        @Query("""
                        select sum(te.expenseAmount)
                        from TravelExpense te
                        where te.submittedBy = :currentEmployee
                        and te.expenseDate = :expenseDate
                        and te.travelPlan = :travelPlan
                        """)
        BigDecimal sumOfExpenseDateByEmployee(EmployeeProfile currentEmployee, LocalDate expenseDate,
                        TravelPlan travelPlan);
}
