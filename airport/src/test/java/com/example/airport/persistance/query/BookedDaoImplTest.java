package com.example.airport.persistance.query;

import com.example.airport.domain.entity.Booked;
import com.example.airport.domain.entity.Client;
import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.enumeration.*;
import com.example.airport.domain.to.BookedDto;
import com.example.airport.persistance.repository.BookedRepository;
import com.example.airport.persistance.repository.ClientRepository;
import com.example.airport.persistance.repository.PlaneRepository;
import com.example.airport.persistance.repository.SeatRepository;
import com.example.airport.persistance.service.crud.BookedService;
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
public class BookedDaoImplTest {

    @Autowired
    private BookedService service;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private PlaneRepository planeRepository;
    @Autowired
    private BookedRepository repository;

    @Before
    public void setUp(){
        Set<Seat> seats = new HashSet<>();
        Plane plane = new Plane(null,"11111","namePrzewoznika",null,null);
        clientRepository.save(new Client(null,"name1","surname","111111","email","7xC", DocumentType.ID_CARD,null));
        clientRepository.save(new Client(null,"name2","surname","222222","email2","222", DocumentType.VISA,null));
        planeRepository.save(plane);
        Seat seat1 = new Seat(null,1,2, CategoryType.PREMIUM, SeatPosition.RIGHT,true,plane,null);
        Seat seat2 = new Seat(null,13,21, CategoryType.PREMIUM, SeatPosition.LEFT,false,plane,null);
        seatRepository.save(seat1);
        seatRepository.save(seat2);
    }

    @Test
    public void getListOfBookedToRemoveShouldReturnElements() {
        //given
        BookedDto bookedDto1 = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        service.add(bookedDto1,1L,2L);
        service.add(bookedDto2,2L,1L);
        //when
        service.setToRemove(2L);
        List<Booked> seat = repository.getListOfBookedToRemove();
        //then
        assertEquals(1,seat.size());
    }
    @Test
    public void getListOfBookedToRemoveShouldReturnEmptyListWhenNotFoundElement() {
        //given
        BookedDto bookedDto1 = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        service.add(bookedDto1,1L,2L);
        service.add(bookedDto2,2L,1L);
        //when
        List<Booked> seat = repository.getListOfBookedToRemove();
        //then
        assertEquals(0,seat.size());
    }

    @Test
    public void getListOfBookedToRemoveShouldReturnEmptyListWhenDbIsEmpty() {
        //given
        //when
        List<Booked> seat = repository.getListOfBookedToRemove();
        //then
        assertEquals(0,seat.size());
        //TestPropertySource
    }


    private BookedDto createBookedDto(Long id, Long reservationNumber, Double price, SoldType soldType, BookedState bookedState, LocalDateTime buyingDate) {
        return BookedDto.builder().withId(id).withReservationNumber(reservationNumber).withPrice(price).withSoldType(soldType).withBookedState(bookedState).withBuyingDate(buyingDate).build();
    }
}
