package com.example.airport.persistance.validation;

import com.example.airport.domain.to.PlaneDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PlaneValidator implements AbstractValidator<PlaneDto> {
    @Override
    public boolean addValidate(PlaneDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        return this.isValidate(dto);
    }
    @Override
    public boolean updateValidate(PlaneDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        if (Objects.isNull(dto.getId()) || dto.getId() < 1L) {
            return false;
        }
        return this.isValidate(dto);
    }
    private boolean isValidate(PlaneDto dto) {
        if(Objects.isNull(dto.getSerialNumber()) || dto.getSerialNumber().isEmpty()){
            return false;
        }
        if(Objects.isNull(dto.getNameCarrier()) || dto.getNameCarrier().isEmpty()){
            return false;
        }
        return true;
    }
}
