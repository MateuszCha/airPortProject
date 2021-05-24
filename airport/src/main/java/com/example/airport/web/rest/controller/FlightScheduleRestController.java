package com.example.airport.web.rest.controller;

import com.example.airport.domain.to.FlightScheduleDto;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.service.crud.FlightScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class FlightScheduleRestController {

    private final FlightScheduleService service;

    @Autowired
    public FlightScheduleRestController(FlightScheduleService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<FlightScheduleDto> getPlane(@PathVariable("id") Long scheduleID){
        FlightScheduleDto flightSchedule = service.get(scheduleID);
        return ResponseEntity.status(HttpStatus.OK).body(flightSchedule);
    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FlightScheduleDto>> getPlane(){
        List<FlightScheduleDto> listOfSchedule = service.getAll();
        if(listOfSchedule.isEmpty()){
            throw new NoFoundEntity("list of flight schedule ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listOfSchedule);
    }
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<FlightScheduleDto> createPlane(@RequestBody FlightScheduleDto flightScheduleDto,
                                                         @RequestParam(value = "planeId") Long planeId,
                                                         UriComponentsBuilder ucb){
        FlightScheduleDto flightSchedule = service.add(flightScheduleDto,planeId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("/schedule/").path(String.valueOf(flightSchedule.getId())).build().toUri());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(flightSchedule);
    }
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<FlightScheduleDto> updatePlane(@RequestBody FlightScheduleDto flightScheduleDto,
                                                         @RequestParam(value = "planeId") Long planeId,
                                                         UriComponentsBuilder ucb){
        FlightScheduleDto flightSchedule = service.update(flightScheduleDto,planeId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("/plane/").path(String.valueOf(flightSchedule.getId())).build().toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(flightSchedule);
    }
    @DeleteMapping(value ="/{id}")
    public ResponseEntity<FlightScheduleDto> deletePlane(@PathVariable(value = "id") Long scheduleId){
        FlightScheduleDto flightSchedule = service.remove(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(flightSchedule);
    }


}
