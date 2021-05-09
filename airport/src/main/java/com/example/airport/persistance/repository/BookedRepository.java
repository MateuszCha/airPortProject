package com.example.airport.persistance.repository;

import com.example.airport.domain.entity.Booked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRepository extends JpaRepository<Booked,Long> {
}
