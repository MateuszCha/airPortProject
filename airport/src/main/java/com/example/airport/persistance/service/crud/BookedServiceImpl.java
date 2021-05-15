package com.example.airport.persistance.service.crud;

import com.example.airport.domain.entity.Booked;
import com.example.airport.domain.entity.Client;
import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.to.BookedDto;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.mapper.BookedMapper;
import com.example.airport.persistance.repository.BookedRepository;
import com.example.airport.persistance.repository.ClientRepository;
import com.example.airport.persistance.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookedServiceImpl implements AbstractCrudService<Long, BookedDto> {


    private final BookedMapper mapper;
    private final BookedRepository repository;
    private final ClientRepository clientRepository;
    private final SeatRepository seatRepository;
   // private final BookedValidator validator;


    @Autowired
    public BookedServiceImpl(BookedMapper mapper, BookedRepository repository, ClientRepository clientRepository, SeatRepository seatRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    @Override
    public BookedDto get(Long index) {
        if(!this.doesIndexProperly(index)){
            throw new IllegalIndexEntity("index:" + index.toString());
        }
        Optional<Booked> booked = repository.findById(index);
        if(booked.isEmpty()){
            throw new NoFoundEntity("entity on index: " + index.toString());
        }
        return mapper.map2To(booked.get());
    }

    @Transactional
    @Override
    public List<BookedDto> getAll() {
        List<Booked> listOfBooked = repository.findAll();
        if(Objects.isNull(listOfBooked) || listOfBooked.isEmpty()) {
            throw new NoFoundEntity("entities");
        }
        return mapper.map2Toes(listOfBooked);
    }
    // to remove
    @Override
    public BookedDto add(BookedDto dto) {
        if (Objects.isNull(dto))  //|| validator.isValidAdd(dto)
        {
            throw new IllegalArgumentException();
        }
        Booked booked = mapper.map2Entity(dto);
        booked = repository.save(booked);
        return mapper.map2To(booked);
    }

    @Transactional
    public BookedDto add(BookedDto dto, Long clientIndex,Long seatIndex) {
        if (Objects.isNull(dto))  //|| validator.isValidAdd(dto)
        {
            throw new IllegalArgumentException();
        }
        Booked booked = mapper.map2Entity(dto);
        if(doesIndexProperly(clientIndex)){
            Optional<Client> client = clientRepository.findById(clientIndex);
            if(client.isEmpty()){
                throw new NoFoundEntity("client index : " + clientIndex);
            }
            booked.setClient(client.get());
        }else{
            throw new IllegalIndexEntity(" client index:" + clientIndex.toString());
        }
        if(doesIndexProperly(seatIndex)){
            Optional<Seat> seat = seatRepository.findById(seatIndex);
            if(seat.isEmpty()){
                throw new NoFoundEntity("seat index : " + seatIndex);
            }
            booked.setSeat(seat.get());
        }else{
            throw new IllegalIndexEntity(" seat index:" + seatIndex.toString());
        }
        booked = repository.save(booked);
        return mapper.map2To(booked);
    }
//to remove
    @Transactional
    @Override
    public BookedDto update(BookedDto dto) {
        if(Objects.isNull(dto)) //|| validator.isValidUpdate()
        {
            throw new IllegalArgumentException();
        }
        Optional<Booked> booked = repository.findById(dto.getId());
        if(booked.isEmpty()){
            throw new IllegalIndexEntity("index:" + dto.getId());
        }

        /*
         this.id = id;
        this.reservationNumber = reservationNumber;
        this.price = price;
        this.soldType = soldType;
        this.bookedState = bookedState;
        this.buyingDate = buyingDate;
        this.client = client;
        this.seat = seat;
         */
        booked.get().setReservationNumber(dto.getReservationNumber());
        booked.get().setPrice(dto.getPrice());
        booked.get().setSoldType(dto.getSoldType());
        booked.get().setBookedState(dto.getBookedState());
        booked.get().setBuyingDate(dto.getBuyingDate());
        return mapper.map2To(booked.get());
    }

    @Transactional
    public BookedDto update(BookedDto dto,Long clientIndex,Long seatIndex) {
        if(Objects.isNull(dto)) //|| validator.isValidUpdate()
        {
            throw new IllegalArgumentException();
        }
        Optional<Booked> booked = repository.findById(dto.getId());
        if(booked.isEmpty()){
            throw new IllegalIndexEntity("index:" + dto.getId());
        }
        booked.get().setReservationNumber(dto.getReservationNumber());
        booked.get().setPrice(dto.getPrice());
        booked.get().setSoldType(dto.getSoldType());
        booked.get().setBookedState(dto.getBookedState());
        booked.get().setBuyingDate(dto.getBuyingDate());
        if(doesIndexProperly(clientIndex)){
            Optional<Client> client = clientRepository.findById(clientIndex);
            if(client.isEmpty()){
                throw new NoFoundEntity("client index : " + clientIndex);
            }
            booked.get().setClient(client.get());
        }
        if(doesIndexProperly(seatIndex)){
            Optional<Seat> seat = seatRepository.findById(seatIndex);
            if(seat.isEmpty()){
                throw new NoFoundEntity("seat index : " + seatIndex);
            }
            booked.get().setSeat(seat.get());
        }
        return mapper.map2To(booked.get());
    }

    @Transactional
    @Override
    public BookedDto remove(Long index) {
        if(!this.doesIndexProperly(index)){
            throw new IllegalIndexEntity("index:" + index.toString());
        }
        Optional<Booked> booked = repository.findById(index);
        if(booked.isEmpty()){
            throw new NoFoundEntity("entity on index: " + index.toString());
        }
        repository.delete(booked.get());
        return mapper.map2To(booked.get());
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
