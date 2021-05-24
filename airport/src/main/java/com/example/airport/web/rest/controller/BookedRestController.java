package com.example.airport.web.rest.controller;

import com.example.airport.domain.to.BookedDto;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.service.crud.BookedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/booked")
public class BookedRestController {

    private final BookedService bookedService;


    @Autowired
    public BookedRestController(BookedService bookedService) {
        this.bookedService = bookedService;

    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BookedDto> getBooked(@PathVariable("id") Long id){
            BookedDto dto = bookedService.get(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BookedDto>> getListOfBooked(){
        List<BookedDto> listOfBooked = bookedService.getAll();
        if(listOfBooked.isEmpty()){
            throw new NoFoundEntity("no found Booked");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listOfBooked);
    }
    @PostMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<BookedDto> update(@RequestBody BookedDto bookedDto,
                                            @RequestParam(value = "seatId") Long seatId,
                                            @RequestParam(value = "clientId") Long clientId,
                                            UriComponentsBuilder ucb){
        BookedDto booked = bookedService.update(bookedDto,clientId,seatId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucb.path("/booked/").path(String.valueOf(booked.getId())).build().toUri());
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(booked);
    }
    @PutMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<BookedDto> createBooked(@RequestBody BookedDto bookedDto,
                                                  @RequestParam(value = "clientId") Long clientId,
                                                  @RequestParam(value = "seatId") Long seatId,
                                                  UriComponentsBuilder ucb){
        BookedDto booked = bookedService.add(bookedDto,clientId,seatId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("/booked/").path(String.valueOf(booked.getId())).build().toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(booked);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BookedDto> removeBooked(@PathVariable("id")Long id){
        BookedDto booked = bookedService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).body(booked);
    }
}
