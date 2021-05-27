package com.example.airport.persistance.service.crud;

import java.util.List;

public interface AbstractCrudService<INDEX,T> {
    T get(INDEX index);
    List<T> getAll();
    T add(T dto);
    T update(T dto);
    T remove(INDEX index);


}
