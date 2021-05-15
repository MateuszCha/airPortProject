package com.example.airport.persistance.service.crud;

import com.example.airport.domain.entity.FlightSchedule;
import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.to.FlightScheduleDto;
import com.example.airport.domain.to.PlaneDto;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.mapper.FlightScheduleMapper;
import com.example.airport.persistance.repository.FlightScheduleRepository;
import com.example.airport.persistance.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FlightScheduleServiceImpl implements AbstractCrudService<Long, FlightScheduleDto>{
    private final FlightScheduleRepository repository;
    private final FlightScheduleMapper mapper;
    //private final FlightScheduleValidator validator;
    private final PlaneRepository planeRepository;


    @Autowired
    public FlightScheduleServiceImpl(FlightScheduleRepository repository, FlightScheduleMapper mapper, PlaneRepository planeRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.planeRepository = planeRepository;
    }
    @Transactional
    @Override
    public FlightScheduleDto get(Long flightIndex) {
        if(!doesIndexProperly(flightIndex)){
            throw new IllegalIndexEntity("Flight schedule on index: " + flightIndex.toString());
        }
        Optional<FlightSchedule> flight = repository.findById(flightIndex);
        if(flight.isEmpty()){
            throw new NoFoundEntity("Flight schedules on index: " + flightIndex.toString());
        }
        return mapper.map2To(flight.get());

    }
    @Transactional
    @Override
    public List<FlightScheduleDto> getAll() {
        List<FlightSchedule> flightSchedules = repository.findAll();
        if(Objects.isNull(flightSchedules) || flightSchedules.isEmpty()){
            throw new NoFoundEntity("Flight schedules");
        }
        return mapper.map2Toes(flightSchedules);
    }
    @Transactional
    @Override
    public FlightScheduleDto add(FlightScheduleDto dto) {

        return null;
    }

    @Transactional
    public FlightScheduleDto add(FlightScheduleDto dto,Long planeIndex) {
        if(Objects.isNull(dto)) // validator.isUpdate(dto)
        {
            throw new IllegalArgumentException();
        }
        FlightSchedule flightSchedule = mapper.map2Entity(dto);
        if(doesIndexProperly(planeIndex)){
            Optional<Plane> plane = planeRepository.findById(planeIndex);
            if(plane.isEmpty()) {
                throw new NoFoundEntity("plane on index" + planeIndex);
            }
            flightSchedule.setPlane(plane.get());
        }else{
            throw new IllegalIndexEntity("plane index: " + planeIndex.toString());
        }
       repository.save(flightSchedule);
        return mapper.map2To(flightSchedule);
    }

    @Override
    public FlightScheduleDto update(FlightScheduleDto dto) {
        return null;
    }


    @Transactional
    public FlightScheduleDto update(FlightScheduleDto dto, Long planeIndex) {
        if(Objects.isNull(dto)) // || validator.isUpdate(dto)
        {
            throw new IllegalArgumentException();
        }
        Optional<FlightSchedule> flight = repository.findById(dto.getId());
        if(flight.isEmpty()){
            throw new NoFoundEntity("Flight schedules on index: " + dto.getId().toString());
        }

        flight.get().setName(dto.getName());
        flight.get().setStartTime(dto.getStartTime());
        flight.get().setArrive(dto.getArrive());
        flight.get().setDescription(dto.getDescription());
        flight.get().setDestination(dto.getDestination());
        flight.get().setFlyType(dto.getFlyType());

        if(doesIndexProperly(planeIndex)){
            Optional<Plane> plane = planeRepository.findById(planeIndex);
            if(plane.isEmpty()) {
                throw new NoFoundEntity("plane on index" + planeIndex);
            }
            flight.get().setPlane(plane.get());
        }else{
            throw new IllegalIndexEntity("plane index: " + planeIndex.toString());
        }
        return mapper.map2To(flight.get());
    }

    @Transactional
    @Override
    public FlightScheduleDto remove(Long flightIndex) {
        if(!doesIndexProperly(flightIndex)){
            throw new IllegalIndexEntity("Flight schedules on index: " + flightIndex.toString());
        }
        Optional<FlightSchedule> flight = repository.findById(flightIndex);
        if(flight.isEmpty()){
            throw new NoFoundEntity("Flight schedules on index: " + flightIndex.toString());
        }
        FlightScheduleDto flightScheduleDto =  mapper.map2To(flight.get());
        repository.delete(flight.get());
        return flightScheduleDto;
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
