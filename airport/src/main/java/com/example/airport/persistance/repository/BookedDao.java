package com.example.airport.persistance.repository;

import com.example.airport.domain.entity.Booked;

import java.util.List;

public interface BookedDao {
    List<Booked> getListOfBookedToRemove();
}
