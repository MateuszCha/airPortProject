package com.example.airport.persistance.service.crud;

import com.example.airport.domain.to.BookedDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookedService {
    @Transactional
    BookedDto get(Long index);

    @Transactional
    List<BookedDto> getAll();

    @Transactional
    BookedDto add(BookedDto dto, Long clientIndex, Long seatIndex);

    @Transactional
    BookedDto update(BookedDto dto, Long clientIndex, Long seatIndex);

    @Transactional
    BookedDto remove(Long index);
}
