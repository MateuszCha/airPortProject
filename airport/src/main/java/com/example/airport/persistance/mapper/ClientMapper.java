package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.Client;
import com.example.airport.persistance.to.ClientDto;

import java.util.List;

public interface ClientMapper extends AbstractMapper<Client, ClientDto> {
    @Override
    ClientDto map2To(Client entity);

    @Override
    Client map2Entity(ClientDto to);

    @Override
    List<Client> map2Entities(List<ClientDto> toes);

    @Override
    List<ClientDto> map2Toes(List<Client> entities);
}
