package com.example.airport.persistance.repository;

import com.example.airport.domain.entity.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule,Long> {
}
