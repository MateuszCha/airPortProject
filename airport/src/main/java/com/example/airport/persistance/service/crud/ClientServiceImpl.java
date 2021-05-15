package com.example.airport.persistance.service.crud;

import com.example.airport.domain.entity.Client;
import com.example.airport.domain.to.ClientDto;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.mapper.ClientMapper;
import com.example.airport.persistance.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientServiceImpl implements AbstractCrudService<Long, ClientDto>{

    private final ClientMapper mapper;
   // private final ClientValidator validator;
    private final ClientRepository repository;

    @Autowired
    public ClientServiceImpl(ClientMapper mapper, ClientRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    @Override
    public ClientDto get(Long index){
        if(!this.doesIndexProperly(index)){
            throw new IllegalIndexEntity("index:" + index.toString());
        }
        Optional<Client> client = repository.findById(index);
        if(client.isEmpty()){
            throw new NoFoundEntity("entity on index: " + index.toString());
        }
        return mapper.map2To(client.get());
    }

    @Transactional
    @Override
    public List<ClientDto> getAll(){
        List<Client> clients = repository.findAll();
        if(Objects.isNull(clients) || clients.isEmpty()) {
            throw new NoFoundEntity("entities");
        }
        return mapper.map2Toes(clients);
    }

    @Transactional
    @Override
    public ClientDto add(ClientDto clientDto){
        if(Objects.isNull(clientDto) )  //|| validator.isValidAdd)
        {
            throw new IllegalArgumentException();
        }
        Client client = mapper.map2Entity(clientDto);
        client = repository.save(client);
        return mapper.map2To(client);
    }

    @Override
    public ClientDto remove(Long index){
        if(!this.doesIndexProperly(index)){
            throw new IllegalIndexEntity("index:" + index.toString());
        }
        Optional<Client> client = repository.findById(index);
        if(client.isEmpty()){
            throw new NoFoundEntity("entity on index: " + index.toString());
        }
        repository.delete(client.get());
        return mapper.map2To(client.get());
    }

    @Override
    public ClientDto update(ClientDto clientDto){
        if(Objects.isNull(clientDto)) //|| validator.isValidUpdate()
        {
            throw new IllegalArgumentException();
        }
        Optional<Client> client = repository.findById(clientDto.getId());
        if(client.isEmpty()){
            throw new IllegalIndexEntity("index:" + clientDto.getId());
        }
        client.get().setFirstName(clientDto.getFirstName());
        client.get().setSurname(clientDto.getSurname());
        client.get().setPhoneNumber(clientDto.getPhoneNumber());
        client.get().setEmail(clientDto.getEmail());
        client.get().setIdNumber(clientDto.getIdNumber());
        client.get().setDocumentType(clientDto.getDocumentType());
        repository.flush(); // jesli dobze pamietra
        return mapper.map2To(client.get());
    }

    /**
     *  check parameter index is properly. This means that is more than one and object is not null;
     * @param index description index in database
     * @return true if index is properly, otherwise false.
     */
    private boolean doesIndexProperly(Long index){
        if(Objects.isNull(index) || index < 1){
            return false;
        }
        return true;
    }

}
