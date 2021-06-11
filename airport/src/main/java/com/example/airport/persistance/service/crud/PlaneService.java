package com.example.airport.persistance.service.crud;

import com.example.airport.domain.to.PlaneDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaneService {
    @Transactional
    PlaneDto get(Long planeIndex);

    @Transactional
    List<PlaneDto> getAll();

    @Transactional
    PlaneDto add(PlaneDto dto);

    @Transactional
    PlaneDto update(PlaneDto dto);

    @Transactional
    PlaneDto remove(Long planeIndex);

    @Transactional
    PlaneDto setToRemove(Long planeIndex);
}
