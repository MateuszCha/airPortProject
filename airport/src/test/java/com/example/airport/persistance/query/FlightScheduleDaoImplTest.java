package com.example.airport.persistance.query;

import com.example.airport.domain.entity.Client;
import com.example.airport.domain.entity.FlightSchedule;
import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.enumeration.CategoryType;
import com.example.airport.domain.enumeration.DocumentType;
import com.example.airport.domain.enumeration.FlyType;
import com.example.airport.domain.enumeration.SeatPosition;
import com.example.airport.domain.to.FlightScheduleDto;
import com.example.airport.persistance.repository.*;
import com.example.airport.persistance.service.crud.FlightScheduleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("hsql")
@TestPropertySource(properties = "custom.var.earlie.days=0")
public class FlightScheduleDaoImplTest {

    @Autowired
    private FlightScheduleService service;
    @Autowired
    private BookedRepository bookedRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private PlaneRepository planeRepository;
    @Autowired
    private FlightScheduleRepository repository;

    @Before
    public void setUp(){
        Set<Seat> seats = new HashSet<>();
        Plane plane = new Plane(null,"11111","namePrzewoznika",null,null);
        Plane plane2 = new Plane(null,"222222","namePrzewoznika2",null,null);
        clientRepository.save(new Client(null,"name1","surname","111111","email","7xC", DocumentType.ID_CARD,null));
        clientRepository.save(new Client(null,"name2","surname","222222","email2","222", DocumentType.VISA,null));
        planeRepository.save(plane);
        planeRepository.save(plane2);
        Seat seat1 = new Seat(null,1,2, CategoryType.PREMIUM, SeatPosition.RIGHT,true,plane,null);
        Seat seat2 = new Seat(null,13,21, CategoryType.PREMIUM, SeatPosition.LEFT,false,plane,null);
        Seat seat3 = new Seat(null,99,99, CategoryType.PREMIUM, SeatPosition.RIGHT,true,plane2,null);
        seatRepository.save(seat1);
        seatRepository.save(seat2);
        seatRepository.save(seat3);
    }

    @Test
    public void getSeatToRemoveShouldReturnElements() {
        //given
        FlightScheduleDto flightScheduleDto1 = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        FlightScheduleDto flightScheduleDto2 = this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL);
        service.add(flightScheduleDto1,1L);
        service.add(flightScheduleDto2,1L);
        //when
        service.setToRemove(2L);
        List<FlightSchedule> seat = repository.getSchedulesToRemove();
        //then
        assertEquals(1,seat.size());
    }
    @Test
    public void getSeatToRemoveShouldReturnEmptyListWhenNotFoundElement() {
        //given
        FlightScheduleDto flightScheduleDto1 = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        FlightScheduleDto flightScheduleDto2 = this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL);
        service.add(flightScheduleDto1,1L);
        service.add(flightScheduleDto2,1L);
        //when
        List<FlightSchedule> seat = repository.getSchedulesToRemove();
        //then
        assertEquals(0,seat.size());
    }

    @Test
    public void getSeatToRemoveShouldReturnEmptyListWhenDbIsEmpty() {
        //given
        //when
        List<FlightSchedule> seat = repository.getSchedulesToRemove();
        //then
        assertEquals(0,seat.size());
        //TestPropertySource
    }
    private FlightScheduleDto createFlightScheduleDto(Long id, String name, LocalDateTime startTime, LocalDateTime arrive, String description, String destination, FlyType flyType) {
        return FlightScheduleDto.builder().withId(id).withName(name).withStartTime(startTime).withArriveTime(arrive).withDescription(description).withDestination(destination).withFlyType(flyType).build();
    }

}
