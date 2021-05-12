package com.example.airport.persistance.mapper.impl;

import com.example.airport.domain.entity.FlightSchedule;
import com.example.airport.persistance.mapper.FlightScheduleMapper;
import com.example.airport.persistance.to.FlightScheduleDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FlightScheduleMapperImpl implements FlightScheduleMapper {
    @Override
    public FlightScheduleDto map2To(FlightSchedule entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity, FlightScheduleDto.class);
    }

    @Override
    public FlightSchedule map2Entity(FlightScheduleDto to) {
        if(Objects.isNull(to)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(to, FlightSchedule.class);
    }

    @Override
    public List<FlightSchedule> map2Entities(List<FlightScheduleDto> toes) {
        if(Objects.isNull(toes) || toes.isEmpty()){
            return null;
        }
        return toes.stream().map((e)->this.map2Entity(e)).collect(Collectors.toList());

    }

    @Override
    public List<FlightScheduleDto> map2Toes(List<FlightSchedule> entities) {
        if(Objects.isNull(entities) || entities.isEmpty()){
            return null;
        }
        return entities.stream().map((t)->this.map2To(t)).collect(Collectors.toList());

    }
}
