package com.example.airport.persistance.repository;

import com.example.airport.domain.entity.FlightSchedule;

import java.util.List;

public interface FlightScheduleDao {
    List<FlightSchedule> getSchedulesToRemove();
}
