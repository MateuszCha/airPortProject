package com.example.airport.persistance.repository;

import com.example.airport.domain.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<Plane,Long> {
}
