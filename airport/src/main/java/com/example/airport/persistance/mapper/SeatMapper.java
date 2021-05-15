package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.to.SeatDto;

import java.util.List;

public interface SeatMapper extends AbstractMapper<Seat, SeatDto> {
    @Override
    SeatDto map2To(Seat entity);

    @Override
    Seat map2Entity(SeatDto to);

    @Override
    List<Seat> map2Entities(List<SeatDto> toes);

    @Override
    List<SeatDto> map2Toes(List<Seat> entities);
}
