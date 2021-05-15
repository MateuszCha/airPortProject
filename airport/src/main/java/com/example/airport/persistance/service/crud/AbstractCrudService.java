package com.example.airport.persistance.service.crud;

import java.util.List;

public interface AbstractCrudService<Index,T> {
    T get(Index index);
    List<T> getAll();
    T add(T dto);
    T update(T dto);
    T remove(Index index);
}
