package com.example.airport.persistance.repository;

import com.example.airport.domain.entity.Seat;

import java.util.List;

public interface SeatDao {
    List<Seat> getSeatToRemove();
}
