package com.roima.hrms.repository;

import com.roima.hrms.entities.SlotBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotBookingRepository extends JpaRepository<SlotBooking, Long> {
}