package com.example.airport.web.rest;

import com.example.airport.domain.to.ClientDto;
import com.example.airport.persistance.service.crud.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientRestController {

    private final ClientService service;

    @Autowired
    public ClientRestController(ClientService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ClientDto> getClient(@PathVariable(value = "id")Long index){
        ClientDto dto = service.get(index);
        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }
    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients(){
        List<ClientDto> dto = service.getAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    @PutMapping(produces = "application/json",consumes = "application/json" )
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto,UriComponentsBuilder ucb){
        HttpHeaders httpHeaders = new HttpHeaders();
        ClientDto client = service.add(clientDto);
        httpHeaders.setLocation(ucb.path("/client/").path(String.valueOf(client.getId())).build().toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(client);
    }
    @PostMapping(produces = "application/json",consumes = "application/json" )
    public ResponseEntity<ClientDto> updateClient(@RequestBody ClientDto clientDto,UriComponentsBuilder ucb){
        HttpHeaders httpHeaders = new HttpHeaders();
        ClientDto client = service.update(clientDto);
        httpHeaders.setLocation(ucb.path("/client/").path(String.valueOf(client.getId())).build().toUri());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(client);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable(value = "id") Long index){
        ClientDto client  = service.remove(index);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

}
