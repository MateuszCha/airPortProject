package com.example.airport.persistance.repository;

import com.example.airport.domain.entity.Booked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookedRepository extends JpaRepository<Booked,Long>, BookedDao {

}
