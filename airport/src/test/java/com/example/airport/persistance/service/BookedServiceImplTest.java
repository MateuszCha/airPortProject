package com.example.airport.persistance.service;

import com.example.airport.domain.entity.Client;
import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.enumeration.*;
import com.example.airport.domain.to.BookedDto;
import com.example.airport.persistance.exception.DifferentVersion;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookedServiceImplTest {

    @Autowired
    private BookedService service;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private PlaneRepository planeRepository;

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
    public void addShouldAddElement() {
        //given
        List<Plane> planes = planeRepository.findAll();
        BookedDto expect = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
         //when
        BookedDto result = service.add(expect,1L,2L);
        //then
        assertNotNull(result);
        this.compareTwoBookedDto(expect,result);
    }
    @Test
    public void addShouldAddElementWhenElementOnIndexExistOnDb() {
        //given
        BookedDto bookedDto1 = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto expect = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        List<BookedDto> beforeAdd = service.getAll();
        service.add(bookedDto1,1L,2L);
        BookedDto result = service.add(expect,1L,2L);
        List<BookedDto> afterAdd = service.getAll();
        //then
        assertEquals(0,beforeAdd.size());
        assertEquals(2,afterAdd.size());
        assertNotNull(result);
    }
    @Test(expected = IllegalArgumentException.class)
    public void addShouldThrowExceptionWhenElementIsNull() {
        //given
        BookedDto dto1 = null;
        //when
        service.add(dto1,1L,2L);
        //then
    }
    @Test(expected = NoFoundEntity.class)
    public void addShouldThrowExceptionWhenClientIsNotFound() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(dto,3L,2L);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void addShouldThrowExceptionWhenSeatIndexIsLessThanOne() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(dto,1L,0L);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void addShouldThrowExceptionWhenSeatIndexIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(dto,1L,null);
        //then
    }

    @Test(expected = NoFoundEntity.class)
    public void addShouldThrowExceptionWhenSeatIsNotFound() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(dto,3L,16L);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void addShouldThrowExceptionWhenClientIndexIsLessThanOne() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(dto,0L,2L);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void addShouldThrowExceptionWhenClientIndexIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(dto,null,2L);
        //then
    }


    @Test
    public void getShouldReturnElementOnIndex() {
        //given
        Long index = 2L;
        BookedDto bookedDto1 = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto expect =  this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,1L,1L);
        service.add(expect,1L,2L);
        BookedDto result = service.get(index);
        //then
        this.checkAllParametersInDtoAreNotNull(result);
        assertEquals(index, result.getId());
        this.compareTwoBookedDto(expect,result);
    }

    @Test(expected = NoFoundEntity.class)
    public void getShouldThrowExceptionWhenNotFindElement() {
        //given
        Long index = 14L;
        BookedDto bookedDto =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(bookedDto,1L,1L);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long index = 0L;
        BookedDto bookedDto =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(bookedDto,2L,2L);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long index = Long.MIN_VALUE;
        BookedDto bookedDto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(bookedDto,1L,2L);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long index = null;
        BookedDto bookedDto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        service.add(bookedDto,1L,2L);
        service.get(index);
        //then
    }
    @Test(expected = NoFoundEntity.class)
    public void getShouldThrowExceptionWhenDBisEmpty() {
        //given
        Long index = 1L;
        //when
        service.get(index);
        //then
    }

    @Test
    public void getAllShouldReturnAllElement() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,2L,2L);
        service.add(bookedDto2,1L,2L);
        List<BookedDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(2,resultList.size());
        assertNotNull(resultList.get(0));
        assertNotNull(resultList.get(1));
        this.compareTwoBookedDto(bookedDto1,resultList.get(0));
        this.compareTwoBookedDto(bookedDto2,resultList.get(1));
    }
    @Test
    public void getAllShouldReturnZeroWhenDBisEmpty() {
        //given
        // when
        List<BookedDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(0,resultList.size());
    }

    @Test
    public void removeShouldRemoveElement() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        Long removeIndex = 2L;
        //when
        service.add(bookedDto1,2L,2L);
        service.add(bookedDto2,2L,2L);
        List<BookedDto> beforeRemove = service.getAll();
        BookedDto result = service.remove(removeIndex);
        List<BookedDto> afterRemove = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(2,beforeRemove.size());
        assertEquals(1,afterRemove.size());
        this.compareTwoBookedDto(beforeRemove.get(0),afterRemove.get(0));
        this.compareTwoBookedDto(beforeRemove.get(1),result);
    }

    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenIndexNotExist() {
        //given
        BookedDto bookedDto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        Long removeIndex = 4L;
        //when
        service.add(bookedDto,2L,2L);
        BookedDto result = service.remove(removeIndex);
        //then
    }
    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenDBisEmpty() {
        //given
        Long removeIndex = 4L;
        //when
        BookedDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long removeIndex = 0L;
        //when
        BookedDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long removeIndex = Long.MIN_VALUE;
        //when
        BookedDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long removeIndex = null;
        //when
        BookedDto result = service.remove(removeIndex);
        //then
    }
    @Test
    public void updateShouldUpdateElement() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,1L,2L);
        BookedDto expect = service.add(bookedDto2,1L,2L);
        expect.setBookedState(BookedState.TO_REMOVED);
        expect.setPrice(777.77);
        expect.setSoldType(SoldType.SOLD_AIRPORT);
        List<BookedDto> beforeUpdate = service.getAll();
        BookedDto result = service.update(expect,null,null);
        List<BookedDto> afterUpdate = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(beforeUpdate.size(), afterUpdate.size());
        this.compareTwoBookedDto(beforeUpdate.get(0),afterUpdate.get(0));
        this.compareTwoBookedDto(afterUpdate.get(1),result);
    }
    @Test(expected = NoFoundEntity.class)
    public void updateShouldThrowExceptionWhenIdNoExist() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,1L,2L);
        BookedDto expect = service.add(bookedDto2,1L,2L);
        expect.setId(5L);
        expect.setPrice(777.77);
        expect.setSoldType(SoldType.SOLD_AIRPORT);
        service.update(expect,null,null);
        //then
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowExceptionWhenElementIsNull() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,1L,2L);
        service.add(bookedDto2,1L,1L);
        service.update(null,null,null);
        //then
    }
    @Test(expected = NoFoundEntity.class)
    public void updateShouldThrowExceptionWhenNoFoundClient() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,1L,2L);
        BookedDto expect = service.add(bookedDto2,1L,1L);
        expect.setPrice(9999D);
        service.update(expect,14L,2L);
        //then
    }
    @Test(expected = NoFoundEntity.class)
    public void updateShouldThrowExceptionWhenNoFoundSeat() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,1L,2L);
        BookedDto expect = service.add(bookedDto2,1L,1L);
        expect.setPrice(9999D);
        service.update(expect,1L,21L);
        //then
    }

    @Test
    public void updateShouldUpdateElementWhenIndexClientAndSeatAreSet() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,2L,1L);
        BookedDto expect = service.add(bookedDto2,1L,1L);
        expect.setPrice(9999D);
        BookedDto result = service.update(expect,1L,2L);
        //then
        this.compareTwoBookedDto(expect,result);
        assertEquals(expect.getVersion(),result.getVersion());
    }
    @Test
    public void updateShouldChangeVersionWhenUpdateExistObject() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,1L,2L);
        BookedDto expect = service.add(bookedDto2,2L,2L);
        expect.setPrice(1.27D);
        List<BookedDto> beforeUpdate = service.getAll();
        service.update(expect,null,null);
        List<BookedDto> afterUpdate = service.getAll();
        BookedDto result = service.get(expect.getId());
        //then
        assertNotNull(result);
        assertEquals(beforeUpdate.size(), afterUpdate.size());
        this.compareTwoBookedDto(beforeUpdate.get(0),afterUpdate.get(0));
        this.compareTwoBookedDto(afterUpdate.get(1),result);
        assertEquals(expect.getVersion() + 1, result.getVersion());
    }
    @Test(expected = DifferentVersion.class)
    public void updateShouldThrowExceptionWhenVersionIsDifferent() {
        //given
        BookedDto bookedDto1 =  this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        //when
        service.add(bookedDto1,1L,2L);
        BookedDto expect = service.add(bookedDto2,2L,2L);
        expect.setVersion(23);
        service.update(expect,null,null);
    }

    @Test
    public void setToRemoveShouldSetElementToRemove() {
        //given
        BookedDto bookedDto1 = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        service.add(bookedDto1,1L,2L);
        service.add(bookedDto2,2L,1L);
        //when
        service.setToRemove(2L);
        List<BookedDto> expect = service.getAll();
        //then
        assertEquals(2,expect.size());
        assertEquals(false,expect.get(0).isRemove());
        assertEquals(true,expect.get(1).isRemove());
    }
    @Test(expected = IllegalIndexEntity.class)
    public void setToRemoveShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        BookedDto bookedDto1 = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        service.add(bookedDto1,1L,2L);
        service.add(bookedDto2,2L,1L);
        //when
        service.setToRemove(0L);

    }
    @Test(expected = IllegalIndexEntity.class)
    public void setToRemoveShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        BookedDto bookedDto1 = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        service.add(bookedDto1,1L,2L);
        service.add(bookedDto2,2L,1L);
        //when
        service.setToRemove(-1L);

    }
    @Test(expected = NoFoundEntity.class)
    public void setToRemoveShouldThrowExceptionWhenIEntityNotFound() {
        //given
        BookedDto bookedDto1 = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        BookedDto bookedDto2 = this.createBookedDto(2L,1002L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10));
        service.add(bookedDto1,1L,2L);
        service.add(bookedDto2,2L,1L);
        //when
        service.setToRemove(3L);

    }


    private void checkAllParametersInDtoAreNotNull(BookedDto dto){
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getReservationNumber());
        assertNotNull(dto.getPrice());
        assertNotNull(dto.getSoldType());
        assertNotNull(dto.getBookedState());
        assertNotNull(dto.getBuyingDate());
    }
    private void checkAllParametersInDtoAreNull(BookedDto dto){
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getReservationNumber());
        assertNull(dto.getPrice());
        assertNull(dto.getSoldType());
        assertNull(dto.getBookedState());
        assertNull(dto.getBuyingDate());
    }
    private void compareTwoBookedDto(BookedDto expect, BookedDto result){
        assertEquals(expect.getReservationNumber(), result.getReservationNumber());
        assertEquals(expect.getPrice(), result.getPrice());
        assertEquals(expect.getSoldType(), result.getSoldType());
        assertEquals(expect.getBookedState(), result.getBookedState());
        assertEquals(expect.getBuyingDate(), result.getBuyingDate());
    }

    private BookedDto createBookedDto(Long id, Long reservationNumber, Double price, SoldType soldType, BookedState bookedState, LocalDateTime buyingDate) {
        return BookedDto.builder().withId(id).withReservationNumber(reservationNumber).withPrice(price).withSoldType(soldType).withBookedState(bookedState).withBuyingDate(buyingDate).build();
    }
}
