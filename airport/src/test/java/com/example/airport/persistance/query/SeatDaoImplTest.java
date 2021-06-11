package com.example.airport.persistance.query;

import com.example.airport.domain.entity.Booked;
import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.enumeration.BookedState;
import com.example.airport.domain.enumeration.CategoryType;
import com.example.airport.domain.enumeration.SeatPosition;
import com.example.airport.domain.enumeration.SoldType;
import com.example.airport.domain.to.SeatDto;
import com.example.airport.persistance.repository.BookedRepository;
import com.example.airport.persistance.repository.PlaneRepository;
import com.example.airport.persistance.repository.SeatRepository;
import com.example.airport.persistance.service.crud.SeatService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("hsql")
@TestPropertySource(properties = "custom.var.earlie.days=0")
public class SeatDaoImplTest {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private PlaneRepository planeRepository;
    @Autowired
    private BookedRepository bookedRepository;
    @Autowired
    private SeatService seatService;


    @Before
    public void setUp(){
        planeRepository.save(new Plane(null,"111111","Name1",null,null));
        planeRepository.save(new Plane(null,"222222","Name2",null,null));
        bookedRepository.save(new Booked(null,123123L,7012d, SoldType.SOLD_AIRPORT, BookedState.SOLD, LocalDateTime.now(),null,null));
        bookedRepository.save(new Booked(null,22222L,7012d, SoldType.SOLD_AIRPORT, BookedState.SOLD, LocalDateTime.now(),null,null));
    }


    @Test
    public void getSeatToRemoveShouldReturnElements() {
        //given
        SeatDto dto1 = this.createSeatDto(1L,1,1, CategoryType.BUSINESS, SeatPosition.LEFT,true);
        SeatDto dto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        SeatDto returnedDto1  = seatService.add(dto1,1L);
        SeatDto returnedDto2 = seatService.add(dto2,1L);
        //when
        seatService.setToRemove(2L);
        seatService.getAll();
        List<Seat> seat = seatRepository.getSeatToRemove();
        //then
        assertEquals(1,seat.size());
    }
    @Test
    public void getSeatToRemoveShouldReturnEmptyListWhenNotFoundElement() {
        //given
        SeatDto dto1 = this.createSeatDto(1L,1,1, CategoryType.BUSINESS, SeatPosition.LEFT,true);
        SeatDto dto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        seatService.add(dto1,1L);
        seatService.add(dto2,1L);
        //when
        seatService.getAll();
        List<Seat> seat = seatRepository.getSeatToRemove();
        //then
        assertEquals(0,seat.size());
    }

    @Test
    public void getSeatToRemoveShouldReturnEmptyListWhenDbIsEmpty() {
        //given
        //when
        seatService.getAll();
        List<Seat> seat = seatRepository.getSeatToRemove();
        //then
        assertEquals(0,seat.size());
        //TestPropertySource
    }
    private void compareSeatDto(SeatDto expect, SeatDto result){
        Assertions.assertEquals(expect.getRow(), result.getRow());
        Assertions.assertEquals(expect.getColumn(), result.getColumn());
        Assertions.assertEquals(expect.getCategoryType(), result.getCategoryType());
        Assertions.assertEquals(expect.getPosition(), result.getPosition());
        Assertions.assertEquals(expect.getEnable(), result.getEnable());
    }

    private SeatDto createSeatDto(Long id, Integer row, Integer column, CategoryType categoryType, SeatPosition position, Boolean enable) {
        return SeatDto.builder().withId(id).withRowNumber(row).withColumnNumber(column).withCategoryType(categoryType).withSeatPosition(position).withEnableSeat(enable).build();
    }
}
