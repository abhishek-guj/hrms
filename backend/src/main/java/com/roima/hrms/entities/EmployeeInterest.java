package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee_interests", schema = "game")
public class EmployeeInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_employee_interest_id", nullable = false)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "game_type_id")
    private Long gameTypeId;


}