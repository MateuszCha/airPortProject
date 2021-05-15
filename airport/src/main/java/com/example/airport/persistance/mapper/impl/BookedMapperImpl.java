package com.example.airport.persistance.mapper.impl;

import com.example.airport.domain.entity.Booked;
import com.example.airport.persistance.mapper.BookedMapper;
import com.example.airport.domain.to.BookedDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BookedMapperImpl implements BookedMapper {
    @Override
    public BookedDto map2To(Booked entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity,BookedDto.class);
    }

    @Override
    public Booked map2Entity(BookedDto to) {
        if(Objects.isNull(to)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(to,Booked.class);
    }

    @Override
    public List<Booked> map2Entities(List<BookedDto> toes) {
        if(Objects.isNull(toes) || toes.isEmpty()){
            return null;
        }
        return toes.stream().map((e)->this.map2Entity(e)).collect(Collectors.toList());

    }

    @Override
    public List<BookedDto> map2Toes(List<Booked> entities) {
        if(Objects.isNull(entities) || entities.isEmpty()){
            return null;
        }
        return entities.stream().map((t)->this.map2To(t)).collect(Collectors.toList());

    }
}
