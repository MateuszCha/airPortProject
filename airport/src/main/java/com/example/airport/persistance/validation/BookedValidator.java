package com.example.airport.persistance.validation;

import com.example.airport.domain.to.BookedDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class BookedValidator implements AbstractValidator<BookedDto>{
    @Override
    public boolean addValidate(BookedDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        if(Objects.isNull(dto.getBuyingDate()) || dto.getBuyingDate().isAfter(LocalDateTime.now().plusHours(2L))){
            return false;
        }
        return isValidate(dto);
    }

    @Override
    public boolean updateValidate(BookedDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        if (Objects.isNull(dto.getId()) || dto.getId() < 1L) {
            return false;
        }
        if(Objects.isNull(dto.getBuyingDate()) || dto.getBuyingDate().isAfter(LocalDateTime.now())){
            return false;
        }
        return isValidate(dto);
    }

    private boolean isValidate(BookedDto dto){
        if(Objects.isNull(dto.getReservationNumber()) || dto.getReservationNumber() < 1000L){
            return false;
        }
        if(Objects.isNull(dto.getPrice()) || dto.getPrice() < 0d){
            return false;
        }
        if(Objects.isNull(dto.getSoldType())){
            return false;
        }
        if(Objects.isNull(dto.getBookedState())){
            return false;
        }
        return true;
    }
}
