package com.example.airport.persistance.service.crud.impl;

import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.to.PlaneDto;
import com.example.airport.persistance.exception.DifferentVersion;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.mapper.PlaneMapper;
import com.example.airport.persistance.repository.PlaneRepository;
import com.example.airport.persistance.service.crud.AbstractCrudService;
import com.example.airport.persistance.service.crud.PlaneService;
import com.example.airport.persistance.validation.PlaneValidator;
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
    private final PlaneValidator validator;
    public static final String INDEX_EXCEPTION_MSG = "Plane on index :";

    @Autowired
    public PlaneServiceImpl(PlaneRepository repository, PlaneMapper mapper, PlaneValidator validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Transactional
    @Override
    public PlaneDto get(Long planeIndex) {
        if(!doesIndexProperly(planeIndex)){
            throw new IllegalIndexEntity(INDEX_EXCEPTION_MSG + planeIndex);
        }
        Optional<Plane> plane = repository.findById(planeIndex);
        if(plane.isEmpty()){
            throw new NoFoundEntity(INDEX_EXCEPTION_MSG + planeIndex);
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
        if(! validator.addValidate(dto)){
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
        if(!validator.updateValidate(dto)){
            throw new IllegalArgumentException();
        }
        Optional<Plane> plane = repository.findById(dto.getId());
        if(plane.isEmpty()){
            throw new NoFoundEntity(INDEX_EXCEPTION_MSG + dto.getId());
        }
        this.doesTheSameVersion(dto.getVersion(),plane.get().getVersion());
        plane.get().setSerialNumber(dto.getSerialNumber());
        plane.get().setNameCarrier(dto.getNameCarrier());
        repository.save(plane.get());
        return mapper.map2To(plane.get());
    }

    @Transactional
    @Override
    public PlaneDto remove(Long planeIndex) {
        if(!doesIndexProperly(planeIndex)){
            throw new IllegalIndexEntity(INDEX_EXCEPTION_MSG + planeIndex);
        }
        Optional<Plane> plane = repository.findById(planeIndex);
        if(plane.isEmpty()){
            throw new NoFoundEntity(INDEX_EXCEPTION_MSG + planeIndex);
        }
        plane.get().remove();
        repository.delete(plane.get());
        return mapper.map2To(plane.get());
    }

    @Override
    public PlaneDto setToRemove(Long planeIndex) {
        if(!doesIndexProperly(planeIndex)){
            throw new IllegalIndexEntity(INDEX_EXCEPTION_MSG + planeIndex);
        }
        Optional<Plane> plane = repository.findById(planeIndex);
        if(plane.isEmpty()){
            throw new NoFoundEntity(INDEX_EXCEPTION_MSG + planeIndex);
        }
        plane.get().setRemove(true);
        repository.save(plane.get());
        return mapper.map2To(plane.get());
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
