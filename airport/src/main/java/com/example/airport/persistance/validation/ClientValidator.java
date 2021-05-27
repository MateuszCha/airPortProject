package com.example.airport.persistance.validation;

import com.example.airport.domain.to.ClientDto;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class ClientValidator implements AbstractValidator<ClientDto> {


    @Override
    public final boolean addValidate(ClientDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        return isValidate(dto);
    }

    @Override
    public final boolean updateValidate(ClientDto dto) {
        if(Objects.isNull(dto)){
            return false;
        }
        if(Objects.isNull(dto.getId()) || dto.getId() < 1L){
            return false;
        }
        return this.isValidate(dto);
    }

    private boolean isValidate(ClientDto dto) {
        if(Objects.isNull(dto.getFirstName()) || dto.getFirstName().isEmpty()){
            return false;
        }
        if(Objects.isNull(dto.getSurname()) || dto.getSurname().isEmpty()){
            return false;
        }
        if(Objects.isNull(dto.getPhoneNumber()) || dto.getPhoneNumber().isEmpty()){
            return false;
        }
        if(Objects.isNull(dto.getEmail()) || dto.getEmail().isEmpty()){
            return false;
        }
        if(Objects.isNull(dto.getIdNumber()) || dto.getIdNumber().isEmpty()){
            return false;
        }
        if(Objects.isNull(dto.getDocumentType())){
            return false;
        }
        return true;
    }

}
