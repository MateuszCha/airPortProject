package com.example.airport.persistance.service.crud.impl;

import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.to.PlaneDto;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.mapper.PlaneMapper;
import com.example.airport.persistance.repository.PlaneRepository;
import com.example.airport.persistance.service.crud.AbstractCrudService;
import com.example.airport.persistance.service.crud.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlaneServiceImpl implements AbstractCrudService<Long, PlaneDto>, PlaneService {

private final PlaneRepository repository;
private final PlaneMapper mapper;
//private final PlaneValidator validator;


    @Autowired
    public PlaneServiceImpl(PlaneRepository repository, PlaneMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public PlaneDto get(Long planeIndex) {
        if(!doesIndexProperly(planeIndex)){
            throw new IllegalIndexEntity("plane index :" + planeIndex);
        }
        Optional<Plane> plane = repository.findById(planeIndex);
        if(plane.isEmpty()){
            throw new NoFoundEntity("plane index: " + planeIndex);
        }
        return mapper.map2To(plane.get());
    }

    @Transactional
    @Override
    public List<PlaneDto> getAll() {
        List<Plane> planes = repository.findAll();
        return mapper.map2Toes(planes);
    }

    @Transactional
    @Override
    public PlaneDto add(PlaneDto dto) {
        if(Objects.isNull(dto)){ // || validator.isAdd(dto))
            throw new IllegalArgumentException();
        }
        dto.setId(null);
        Plane plane = mapper.map2Entity(dto);
        repository.save(plane);
        return mapper.map2To(plane);
      }


    @Transactional
    @Override
    public PlaneDto update(PlaneDto dto) {
        if(Objects.isNull(dto))// || validate.isUpdate(dto))
        {
            throw new IllegalArgumentException();
        }
        Optional<Plane> plane = repository.findById(dto.getId());
        if(plane.isEmpty()){
            throw new NoFoundEntity("plane on index :" + dto.getId());
        }
        plane.get().setSerialNumber(dto.getSerialNumber());
        plane.get().setNameCarrier(dto.getNameCarrier());
        repository.save(plane.get());
        return mapper.map2To(plane.get());
    }

    @Transactional
    @Override
    public PlaneDto remove(Long planeIndex) {
        if(!doesIndexProperly(planeIndex)){
            throw new IllegalIndexEntity("plane on index :" + planeIndex);
        }
        Optional<Plane> plane = repository.findById(planeIndex);
        if(plane.isEmpty()){
            throw new NoFoundEntity("plane on index:" + planeIndex);
        }
        plane.get().remove();
        repository.delete(plane.get());
        PlaneDto planeDto = mapper.map2To(plane.get());
        return planeDto;
    }
    /**
     *  check parameter index is properly. This means that is more than one and object is not null;
     * @param index description index in database
     * @return true if index is properly, otherwise false.
     */
    private boolean doesIndexProperly(Long index) {
        return !(Objects.isNull(index) || index < 1);
    }
}
