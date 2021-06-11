package com.example.airport.persistance.service.crud;

import com.example.airport.domain.to.SeatDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SeatService {
    @Transactional
    SeatDto get(Long seatIndex);

    @Transactional
    List<SeatDto> getAll();

    @Transactional
    SeatDto add(SeatDto dto, Long planeIndex);

    @Transactional
    SeatDto update(SeatDto dto, Long planeIndex);

    @Transactional
    SeatDto remove(Long seatIndex);

    @Transactional
    SeatDto setToRemove(Long seatIndex);
}
