package com.example.airport.persistance.validation;

public interface AbstractValidator<T> {

    boolean addValidate(T dto);
    boolean updateValidate(T dto);
}
