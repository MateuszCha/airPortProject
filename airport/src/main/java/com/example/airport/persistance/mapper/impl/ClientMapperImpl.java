package com.example.airport.persistance.mapper.impl;

import com.example.airport.domain.entity.Client;
import com.example.airport.persistance.mapper.ClientMapper;
import com.example.airport.domain.to.ClientDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ClientMapperImpl implements ClientMapper {
    @Override
    public ClientDto map2To(Client entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity,ClientDto.class);
    }

    @Override
    public Client map2Entity(ClientDto to) {
        if(Objects.isNull(to)){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        return mapper.map(to,Client.class);
    }

    @Override
    public List<Client> map2Entities(List<ClientDto> toes) {
        if(Objects.isNull(toes) || toes.isEmpty()){
            return null;
        }
        return toes.stream().map((e)->this.map2Entity(e)).collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> map2Toes(List<Client> entities) {
        if(Objects.isNull(entities) || entities.isEmpty()){
            return null;
        }
        return entities.stream().map((t)->this.map2To(t)).collect(Collectors.toList());
    }
}
