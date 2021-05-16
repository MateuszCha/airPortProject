package com.example.airport.persistance.mapper.impl;

import com.example.airport.domain.entity.Plane;
import com.example.airport.persistance.mapper.PlaneMapper;
import com.example.airport.domain.to.PlaneDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PlaneMapperImpl implements PlaneMapper {
    @Override
    public PlaneDto map2To(Plane entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity,PlaneDto.class);
    }

    @Override
    public Plane map2Entity(PlaneDto to) {
        if(Objects.isNull(to)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(to,Plane.class);
    }

    @Override
    public List<Plane> map2Entities(List<PlaneDto> toes) {
        if(Objects.isNull(toes) || toes.isEmpty()){
            return new ArrayList<>();
        }
        return toes.stream().map((e)->this.map2Entity(e)).collect(Collectors.toList());

    }

    @Override
    public List<PlaneDto> map2Toes(List<Plane> entities) {
        if(Objects.isNull(entities) || entities.isEmpty()){
            return new ArrayList<>();
        }
        return entities.stream().map((t)->this.map2To(t)).collect(Collectors.toList());

    }
}
