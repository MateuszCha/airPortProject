package com.example.airport.persistance.service.crud;

import com.example.airport.domain.to.PlaneDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaneService {
    @Transactional
    PlaneDto get(Long planeIndex);

    @Transactional
    List<PlaneDto> getAll();

    PlaneDto add(PlaneDto dto);

    PlaneDto update(PlaneDto dto);

    PlaneDto remove(Long planeIndex);
}
