package com.example.airport.persistance.mapper.impl;

import com.example.airport.domain.entity.Seat;
import com.example.airport.persistance.mapper.SeatMapper;
import com.example.airport.persistance.to.SeatDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SeatMapperImpl implements SeatMapper {
    @Override
    public SeatDto map2To(Seat entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity,SeatDto.class);
    }

    @Override
    public Seat map2Entity(SeatDto to) {
        if(Objects.isNull(to)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(to,Seat.class);
    }

    @Override
    public List<Seat> map2Entities(List<SeatDto> toes) {
        if(Objects.isNull(toes) || toes.isEmpty()){
            return null;
        }
        return toes.stream().map((e)->this.map2Entity(e)).collect(Collectors.toList());

    }

    @Override
    public List<SeatDto> map2Toes(List<Seat> entities) {
        if(Objects.isNull(entities) || entities.isEmpty()){
            return null;
        }
        return entities.stream().map((t)->this.map2To(t)).collect(Collectors.toList());

    }
}
