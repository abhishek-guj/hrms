package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "travel_employees", schema = "travel")
public class TravelEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_travel_employee_id", nullable = false)
    private Long id;

    @Column(name = "travel_plan_id")
    private Long travelPlanId;

    @Column(name = "employee_id")
    private Long employeeId;


}