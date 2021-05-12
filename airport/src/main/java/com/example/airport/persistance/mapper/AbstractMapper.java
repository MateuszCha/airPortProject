package com.example.airport.persistance.mapper;

import java.util.List;

public interface AbstractMapper<E,T> {
    T map2To(E entity);
    E map2Entity(T to);
    List<E> map2Entities(List<T> toes);
    List<T> map2Toes(List<E> entities);
}
