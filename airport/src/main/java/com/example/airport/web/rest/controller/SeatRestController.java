package com.example.airport.web.rest.controller;

import com.example.airport.domain.to.SeatDto;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.service.crud.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/seat")
public class SeatRestController {

    private final SeatService seatService;

    @Autowired
    public SeatRestController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SeatDto> getSeat(@PathVariable(value = "id")Long index){
        SeatDto seat = seatService.get(index);
        return ResponseEntity.status(HttpStatus.OK).body(seat);
    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SeatDto>> getSeats(){
        List<SeatDto> seats = seatService.getAll();
        if(seats.isEmpty()){
            throw new NoFoundEntity("seats ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(seats);
    }
    @PostMapping(consumes = "application/json" , produces = "application/json")
    public ResponseEntity<SeatDto> updateSeat(@RequestBody SeatDto seatDto,
                                              @RequestParam(value = "planeId") Long planeId,
                                              UriComponentsBuilder ucb){

        SeatDto seat = seatService.update(seatDto,planeId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("/seat/").path(String.valueOf(seat.getId())).build().toUri());
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(seat);
    }
    @PutMapping(consumes = "application/json" , produces = "application/json")
    public ResponseEntity<SeatDto> createSeat(@RequestBody SeatDto seatDto,
                                              @RequestParam(value = "planeId") Long planeId,
                                              UriComponentsBuilder ucb){
        SeatDto seat = seatService.add(seatDto,planeId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("/seat/").path(String.valueOf(seat.getId())).build().toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(seat);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SeatDto> deleteSeat(@PathVariable(value = "id") Long seatId){
        SeatDto seat = seatService.remove(seatId);
        return ResponseEntity.status(HttpStatus.OK).body(seat);
    }

}
