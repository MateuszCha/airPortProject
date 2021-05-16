package com.example.airport.persistance.service.crud.impl;

import com.example.airport.domain.entity.Client;
import com.example.airport.domain.to.ClientDto;
import com.example.airport.persistance.exception.BusinessException;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.mapper.ClientMapper;
import com.example.airport.persistance.repository.ClientRepository;
import com.example.airport.persistance.service.crud.AbstractCrudService;
import com.example.airport.persistance.service.crud.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientServiceImpl implements AbstractCrudService<Long, ClientDto>, ClientService {

    private final ClientMapper mapper;
   // private final ClientValidator validator;
    private final ClientRepository repository;
    private static final String INDEX_EXCEPTION = "entity on index: ";

    @Autowired
    public ClientServiceImpl(ClientMapper mapper, ClientRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    @Override
    public ClientDto get(Long index) throws BusinessException {
        if(!this.doesIndexProperly(index)){
            throw new IllegalIndexEntity("index:" + index);
        }
        Optional<Client> client = repository.findById(index);
        if(client.isEmpty()){
            throw new NoFoundEntity(INDEX_EXCEPTION + index);
        }
        return mapper.map2To(client.get());
    }

    @Transactional
    @Override
    public List<ClientDto> getAll(){
        List<Client> clients = repository.findAll();
        return mapper.map2Toes(clients);
    }

    @Transactional
    @Override
    public ClientDto add(ClientDto clientDto){
        if(Objects.isNull(clientDto) )  //|| validator.isValidAdd)
        {
            throw new IllegalArgumentException();
        }
        clientDto.setId(null);
        Client client = mapper.map2Entity(clientDto);
        client = repository.save(client);
        return mapper.map2To(client);
    }

    @Override
    public ClientDto remove(Long index){
        if(!this.doesIndexProperly(index)){
            throw new IllegalIndexEntity("index:" + index);
        }
        Optional<Client> client = repository.findById(index);
        if(client.isEmpty()){
            throw new NoFoundEntity(INDEX_EXCEPTION + index);
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
            throw new NoFoundEntity(INDEX_EXCEPTION + clientDto.getId());
        }
        client.get().setFirstName(clientDto.getFirstName());
        client.get().setSurname(clientDto.getSurname());
        client.get().setPhoneNumber(clientDto.getPhoneNumber());
        client.get().setEmail(clientDto.getEmail());
        client.get().setIdNumber(clientDto.getIdNumber());
        client.get().setDocumentType(clientDto.getDocumentType());
        repository.save(client.get());
        return mapper.map2To(client.get());
    }

    /**
     *  check parameter index is properly. This means that is more than one and object is not null;
     * @param index description index in database
     * @return true if index is properly, otherwise false.
     */
    private boolean doesIndexProperly(Long index){
        return !(Objects.isNull(index) || index < 1);
    }

}
