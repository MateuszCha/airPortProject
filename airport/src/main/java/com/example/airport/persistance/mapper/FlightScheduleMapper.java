package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.FlightSchedule;
import com.example.airport.persistance.to.FlightScheduleDto;

import java.util.List;

public interface FlightScheduleMapper extends AbstractMapper<FlightSchedule, FlightScheduleDto> {
    @Override
    FlightScheduleDto map2To(FlightSchedule entity);

    @Override
    FlightSchedule map2Entity(FlightScheduleDto to);

    @Override
    List<FlightSchedule> map2Entities(List<FlightScheduleDto> toes);

    @Override
    List<FlightScheduleDto> map2Toes(List<FlightSchedule> entities);
}
