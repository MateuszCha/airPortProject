package com.example.airport.persistance.validation;

import com.example.airport.domain.to.FlightScheduleDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FlightScheduleValidator implements AbstractValidator<FlightScheduleDto>{
    @Override
    public boolean addValidate(FlightScheduleDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        return this.isValidate(dto);
    }

    @Override
    public boolean updateValidate(FlightScheduleDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        if(Objects.isNull(dto.getId()) || dto.getId() < 1L){
            return false;
        }
        return this.isValidate(dto);
    }

    private boolean isValidate(FlightScheduleDto dto) {

        if(Objects.isNull(dto.getName()) || dto.getName().isEmpty()){
            return false;
        }
        if(Objects.isNull(dto.getStartTime())){
            return false;
        }
        if(Objects.isNull(dto.getArriveTime()) || dto.getArriveTime().isBefore(dto.getStartTime())){
            return false;
        }
        if(Objects.isNull(dto.getDestination()) || dto.getDestination().isEmpty()){
            return false;
        }
        if(Objects.isNull(dto.getFlyType())){
            return false;
        }
        return true;
    }

}
