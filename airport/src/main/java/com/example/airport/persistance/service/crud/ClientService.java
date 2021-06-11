package com.example.airport.persistance.service.crud;

import com.example.airport.domain.to.ClientDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientService {
    @Transactional
    ClientDto get(Long index);

    @Transactional
    List<ClientDto> getAll();

    @Transactional
    ClientDto add(ClientDto clientDto);

    @Transactional
    ClientDto remove(Long index);

    @Transactional
    ClientDto setToRemove(Long index);

    @Transactional
    ClientDto update(ClientDto clientDto);
}
