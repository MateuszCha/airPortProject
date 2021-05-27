package com.example.airport.persistance.validation;

import com.example.airport.domain.to.SeatDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SeatValidator implements AbstractValidator<SeatDto>{
    @Override
    public boolean addValidate(SeatDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        return isValidate(dto);
    }

    @Override
    public boolean updateValidate(SeatDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        if (Objects.isNull(dto.getId()) || dto.getId() < 1L) {
            return false;
        }
        return isValidate(dto);
    }
    private boolean isValidate(SeatDto dto) {
        if(Objects.isNull(dto.getRow()) || dto.getRow() < 0){
            return false;
        }
        if(Objects.isNull(dto.getColumn()) || dto.getColumn() < 0){
            return false;
        }
        if(Objects.isNull(dto.getCategoryType())){
            return false;
        }
        if(Objects.isNull(dto.getPosition())){
            return false;
        }
        if(Objects.isNull(dto.getEnable())){
            return false;
        }
        return true;
    }
}
