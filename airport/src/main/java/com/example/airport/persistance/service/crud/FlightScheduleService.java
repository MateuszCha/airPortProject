package com.example.airport.persistance.service.crud;

import com.example.airport.domain.to.FlightScheduleDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FlightScheduleService {
    @Transactional
    FlightScheduleDto get(Long flightIndex);

    @Transactional
    List<FlightScheduleDto> getAll();

    @Transactional
    FlightScheduleDto add(FlightScheduleDto dto, Long planeIndex);

    @Transactional
    FlightScheduleDto update(FlightScheduleDto dto, Long planeIndex);

    @Transactional
    FlightScheduleDto remove(Long flightIndex);

    @Transactional
    FlightScheduleDto setToRemove(Long flightIndex);
}
