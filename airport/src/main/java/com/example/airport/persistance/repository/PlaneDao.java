package com.example.airport.persistance.repository;

import com.example.airport.domain.entity.Plane;

import java.util.List;

public interface PlaneDao {
    List<Plane> getPlanesToRemove();
}
