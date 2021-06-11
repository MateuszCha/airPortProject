package com.example.airport.persistance.repository;

import com.example.airport.domain.entity.Client;

import java.util.List;

public interface ClientDao {
    List<Client> getClientsToRemove();

}
