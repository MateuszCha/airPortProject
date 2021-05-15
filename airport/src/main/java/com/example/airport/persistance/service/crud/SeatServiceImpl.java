package com.example.airport.persistance.service.crud;

import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.to.SeatDto;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.mapper.SeatMapper;
import com.example.airport.persistance.repository.PlaneRepository;
import com.example.airport.persistance.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class SeatServiceImpl implements AbstractCrudService<Long, SeatDto> {

    private final SeatRepository repository;
    private final SeatMapper mapper;
    //private final SeatValidator validation;
    private final PlaneRepository planeRepository;

    @Autowired
    public SeatServiceImpl(SeatRepository repository, SeatMapper mapper, PlaneRepository planeRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.planeRepository = planeRepository;
    }

    @Transactional
    @Override
    public SeatDto get(Long seatIndex) {
        if(!this.doesIndexProperly(seatIndex)){
            throw new IllegalIndexEntity("seat index : " + seatIndex.toString());
        }
        Optional<Seat> seat = repository.findById(seatIndex);
        if(seat.isEmpty()){
            throw new NoFoundEntity("Seat on index: " + seat.toString());
        }
        return mapper.map2To(seat.get());
    }

    @Transactional
    @Override
    public List<SeatDto> getAll() {
        List<Seat> seats = repository.findAll();
        if(Objects.isNull(seats) || seats.isEmpty()){
            throw new NoFoundEntity("Seats");
        }
        return mapper.map2Toes(seats);
    }
// to remove
    @Transactional
    @Override
    public SeatDto add(SeatDto dto) {
        return null;
    }


    @Transactional
    public SeatDto add(SeatDto dto, Long planeIndex) {
        if(Objects.isNull(dto)) // || validator.isAdd(dto)
        {
            throw new IllegalArgumentException();
        }
        Seat seat = mapper.map2Entity(dto);
        if(doesIndexProperly(planeIndex)){
            Optional<Plane> plane = planeRepository.findById(planeIndex);
            if(plane.isEmpty()){
                throw new NoFoundEntity("plane index : " + planeIndex.toString());
            }
            seat.setPlane(plane.get());
        }else {
            throw new IllegalIndexEntity("plane index : " + planeIndex.toString());
        }

        repository.save(seat);
        return mapper.map2To(seat);
    }
//to remove
    @Override
    public SeatDto update(SeatDto dto) {
        return null;
    }

    @Transactional
    public SeatDto update(SeatDto dto, Long planeIndex) {
        if(Objects.isNull(dto)) // || validator.isUpdate(dto)
        {
            throw new IllegalArgumentException();
        }
        Optional<Seat> seat = repository.findById(dto.getId());
        if(seat.isEmpty()){
            throw new NoFoundEntity("Seat index :" + dto.getId());
        }
        seat.get().setRow(dto.getRow());
        seat.get().setColumn(dto.getColumn());
        seat.get().setCategoryType(dto.getCategoryType());
        seat.get().setPosition(dto.getPosition());
        seat.get().setEnable(dto.getEnable());
        if(doesIndexProperly(planeIndex)){
            Optional<Plane> plane = planeRepository.findById(planeIndex);
            if(plane.isEmpty()){
                throw new NoFoundEntity("plane on index: " + planeIndex.toString());
            }
            seat.get().setPlane(plane.get());
        }else{
            throw new IllegalIndexEntity("plane index" + planeIndex.toString());
        }
        return mapper.map2To(seat.get());
    }


    @Override
    public SeatDto remove(Long seatIndex) {
        if(!doesIndexProperly(seatIndex)){
            throw new IllegalIndexEntity("seat index: " + seatIndex.toString());
        }
        Optional<Seat> seat = repository.findById(seatIndex);
        if(seat.isEmpty()){
            throw new NoFoundEntity("seat index: " + seatIndex.toString());
        }
        SeatDto seatDto = mapper.map2To(seat.get());
        repository.delete(seat.get());
        return seatDto;
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
