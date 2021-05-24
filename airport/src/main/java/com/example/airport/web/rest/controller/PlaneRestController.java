package com.example.airport.web.rest.controller;

import com.example.airport.domain.to.PlaneDto;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.service.crud.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/plane")
public class PlaneRestController {

    private final PlaneService service;

    @Autowired
    public PlaneRestController(PlaneService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PlaneDto> getPlane(@PathVariable("id") Long planeId){
        PlaneDto plane = service.get(planeId);
        return ResponseEntity.status(HttpStatus.OK).body(plane);
    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PlaneDto>> getPlane(){
        List<PlaneDto> listOfPlane = service.getAll();
        if(listOfPlane.isEmpty()){
            throw new NoFoundEntity("list of plane ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listOfPlane);
    }
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlaneDto> createPlane(@RequestBody PlaneDto planeDto){
        PlaneDto plane = service.add(planeDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(plane);
    }
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlaneDto> updatePlane(@RequestBody PlaneDto planeDto,
                                                UriComponentsBuilder ucb){
        PlaneDto plane = service.update(planeDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("/plane/").path(String.valueOf(plane.getId())).build().toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(plane);
    }
    @DeleteMapping(value ="/{id}")
    public ResponseEntity<PlaneDto> deletePlane(@PathVariable(value = "id") Long planeId){
        PlaneDto plane = service.remove(planeId);
        return ResponseEntity.status(HttpStatus.OK).body(plane);
    }
}
