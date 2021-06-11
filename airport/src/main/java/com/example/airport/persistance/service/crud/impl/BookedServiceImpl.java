package com.example.airport.persistance.service.crud.impl;

import com.example.airport.domain.entity.Booked;
import com.example.airport.domain.entity.Client;
import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.to.BookedDto;
import com.example.airport.persistance.exception.DifferentVersion;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.mapper.BookedMapper;
import com.example.airport.persistance.repository.BookedRepository;
import com.example.airport.persistance.repository.ClientRepository;
import com.example.airport.persistance.repository.SeatRepository;
import com.example.airport.persistance.service.crud.BookedService;
import com.example.airport.persistance.validation.BookedValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookedServiceImpl implements BookedService {

    private final BookedMapper mapper;
    private final BookedRepository repository;
    private final ClientRepository clientRepository;
    private final SeatRepository seatRepository;
    private final BookedValidator validator;
    public static final String INDEX_EXCEPTION_MSG = "Booked on index: ";


    @Autowired
    public BookedServiceImpl(BookedMapper mapper, BookedRepository repository, ClientRepository clientRepository, SeatRepository seatRepository, BookedValidator validator) {
        this.mapper = mapper;
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.seatRepository = seatRepository;
        this.validator = validator;
    }

    @Transactional
    @Override
    public BookedDto get(Long index) {
        if(!this.doesIndexProperly(index)){
            throw new IllegalIndexEntity(INDEX_EXCEPTION_MSG + index);
        }
        Optional<Booked> booked = repository.findById(index);
        if(booked.isEmpty()){
            throw new NoFoundEntity(INDEX_EXCEPTION_MSG + index);
        }
        return mapper.map2To(booked.get());
    }

    @Transactional
    @Override
    public List<BookedDto> getAll() {
        List<Booked> listOfBooked = repository.findAll();
        return mapper.map2Toes(listOfBooked);
    }

    @Override
    @Transactional
    public BookedDto add(BookedDto dto, Long clientIndex, Long seatIndex) {
        if (!validator.addValidate(dto)){
            throw new IllegalArgumentException();
        }
        dto.setId(null);
        Booked booked = mapper.map2Entity(dto);
        if(doesIndexProperly(clientIndex)){
            Optional<Client> client = clientRepository.findById(clientIndex);
            if(client.isEmpty()){
                throw new NoFoundEntity(INDEX_EXCEPTION_MSG + clientIndex);
            }
            booked.setClient(client.get());
        }else{
            throw new IllegalIndexEntity(INDEX_EXCEPTION_MSG + clientIndex);
        }
        if(doesIndexProperly(seatIndex)){
            Optional<Seat> seat = seatRepository.findById(seatIndex);
            if(seat.isEmpty()){
                throw new NoFoundEntity(SeatServiceImpl.INDEX_EXCEPTION_MSG + seatIndex);
            }
            booked.setSeat(seat.get());
        }else{
            throw new IllegalIndexEntity(SeatServiceImpl.INDEX_EXCEPTION_MSG + seatIndex);
        }
        booked = repository.save(booked);
        return mapper.map2To(booked);
    }

    @Override
    @Transactional
    public BookedDto update(BookedDto dto, Long clientIndex, Long seatIndex) {
        if(!validator.updateValidate(dto)){
            throw new IllegalArgumentException();
        }
        Optional<Booked> booked = repository.findById(dto.getId());
        if(booked.isEmpty()){
            throw new NoFoundEntity("index:" + dto.getId());
        }
        this.doesTheSameVersion(dto.getVersion(),booked.get().getVersion());
        booked.get().setReservationNumber(dto.getReservationNumber());
        booked.get().setPrice(dto.getPrice());
        booked.get().setSoldType(dto.getSoldType());
        booked.get().setBookedState(dto.getBookedState());
        booked.get().setBuyingDate(dto.getBuyingDate());
        if(doesIndexProperly(clientIndex)){
            Optional<Client> client = clientRepository.findById(clientIndex);
            if(client.isEmpty()){
                throw new NoFoundEntity(ClientServiceImpl.INDEX_EXCEPTION_MSG + clientIndex);
            }
            booked.get().setClient(client.get());
        }
        if(doesIndexProperly(seatIndex)){
            Optional<Seat> seat = seatRepository.findById(seatIndex);
            if(seat.isEmpty()){
                throw new NoFoundEntity(ClientServiceImpl.INDEX_EXCEPTION_MSG  + seatIndex);
            }
            booked.get().setSeat(seat.get());
        }
        return mapper.map2To(booked.get());
    }

    @Transactional
    @Override
    public BookedDto remove(Long index) {
        if(!this.doesIndexProperly(index)){
            throw new IllegalIndexEntity(INDEX_EXCEPTION_MSG + index);
        }
        Optional<Booked> booked = repository.findById(index);
        if(booked.isEmpty()){
            throw new NoFoundEntity(INDEX_EXCEPTION_MSG + index);
        }
        repository.delete(booked.get());
        return mapper.map2To(booked.get());
    }

    @Override
    public BookedDto setToRemove(Long index) {
        if(!this.doesIndexProperly(index)){
            throw new IllegalIndexEntity(INDEX_EXCEPTION_MSG + index);
        }
        Optional<Booked> booked = repository.findById(index);
        if(booked.isEmpty()){
            throw new NoFoundEntity(INDEX_EXCEPTION_MSG + index);
        }
        booked.get().setRemove(true);
        repository.save(booked.get());
        return mapper.map2To(booked.get());
    }

    /**
     *  check parameter index is properly. This means that is more than one and object is not null;
     * @param index description index in database
     * @return true if index is properly, otherwise false.
     */
    private boolean doesIndexProperly(Long index) {
        return !(Objects.isNull(index) || index < 1);
    }
    private void doesTheSameVersion(int versionDto, int versionEntity){
        if(versionDto != versionEntity) {
            throw new DifferentVersion("Your version : " + versionDto + " server version : " + versionEntity);
        }
    }
}
