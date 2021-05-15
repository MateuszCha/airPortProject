package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.to.PlaneDto;

import java.util.List;

public interface PlaneMapper extends AbstractMapper<Plane, PlaneDto> {
    @Override
    PlaneDto map2To(Plane entity);

    @Override
    Plane map2Entity(PlaneDto to);

    @Override
    List<Plane> map2Entities(List<PlaneDto> toes);

    @Override
    List<PlaneDto> map2Toes(List<Plane> entities);
}
