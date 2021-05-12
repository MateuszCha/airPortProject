package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.Booked;
import com.example.airport.persistance.to.BookedDto;

import java.util.List;

public interface BookedMapper extends AbstractMapper<Booked, BookedDto> {
    @Override
    BookedDto map2To(Booked entity);

    @Override
    Booked map2Entity(BookedDto to);

    @Override
    List<Booked> map2Entities(List<BookedDto> toes);

    @Override
    List<BookedDto> map2Toes(List<Booked> entities);
}
