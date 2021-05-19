package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.Booked;
import com.example.airport.domain.enumeration.BookedState;
import com.example.airport.domain.enumeration.SoldType;
import com.example.airport.domain.to.BookedDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookedMapperImplTest {
    
    @Autowired 
    private BookedMapper mapper;

    @Test
    public void map2EntityShouldReturnEntity() {
        //given
        BookedDto expect = this.createBookedDto(1L,34L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        Booked result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(result,expect);
        compareEntityWithDto(result, expect);
    }
    @Test
    public void map2EntityShouldReturnNullWhenDtoIsNull() {
        //given
        BookedDto expect = null;
        //when
        Booked result = mapper.map2Entity(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntityShouldReturnEntityWhenAllDtoParametersAreNull() {
        //given
        BookedDto expect = this.createBookedDto(null,null,null,null,null,null);
        //when
        Booked result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(result,expect);
    }


    @Test
    public void map2DtoShouldReturnDto() {
        //given
        Booked expect = this.createBooked(1L,34L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        BookedDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(expect,result);
        compareEntityWithDto(expect, result);
    }
    @Test
    public void map2DtoShouldReturnNullWhenEntityIsNull() {
        //given
        Booked expect  = null;
        //when
        BookedDto result = mapper.map2To(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2DtoShouldReturnEntityWhenAllEntityParametersAreNull() {
        //given
        Booked expect = this.createBooked(null,null,null,null,null,null);
        //when
        BookedDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(expect,result);
    }

    @Test
    public void map2EntitiesShouldReturnListOfEntity() {
        //given
        List<BookedDto> dtos = new ArrayList<>();
        dtos.add( this.createBookedDto(1L,34L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now()));
        dtos.add( this.createBookedDto(2L,12L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10)));
        //when
        List<Booked> result = mapper.map2Entities(dtos);
        //then
        assertNotNull(dtos);
        assertNotNull(result);
        assertEquals(dtos.size(),result.size());
        checkAllParametersInDtoAndEntityAreNotNull(result.get(0),dtos.get(0));
        checkAllParametersInDtoAndEntityAreNotNull(result.get(1),dtos.get(1));
        compareEntityWithDto(result.get(0), dtos.get(0));
        compareEntityWithDto(result.get(1), dtos.get(1));
    }
    @Test
    public void map2EntitiesShouldReturnEmptyCollectionWhenDtoesIsNull() {
        //given
        List<BookedDto> expect = null;
        //when
        List<Booked> result = mapper.map2Entities(expect);
        //then
        assertTrue(result.isEmpty());
    }
    @Test
    public void map2EntitiesShouldReturnEmptyCollectionWhenDtoesIsEmpty() {
        //given
        List<BookedDto> expect = new ArrayList<>();
        //when
        List<Booked> result = mapper.map2Entities(expect);
        //then
        assertTrue(result.isEmpty());
    }
    @Test
    public void map2EntitiesShouldReturnDtoesWhenDtoesIncludeElementWithAllNullParameters() {
        //given
        List<BookedDto> dtos = new ArrayList<>();
        dtos.add(this.createBookedDto(null,null,null,null,null,null));
        dtos.add(this.createBookedDto(null,null,null,null,null,null));
        dtos.add( this.createBookedDto(1L,34L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now()));
        //when
        List<Booked> result = mapper.map2Entities(dtos);
        //then
        assertNotNull(dtos);
        assertNotNull(result);
        assertEquals(dtos.size(),result.size());
        checkAllParametersInDtoAndEntityAreNull(result.get(0),dtos.get(0));
        checkAllParametersInDtoAndEntityAreNull(result.get(1),dtos.get(1));
        checkAllParametersInDtoAndEntityAreNotNull(result.get(2),dtos.get(2));
        compareEntityWithDto(result.get(2), dtos.get(2));
    }
    @Test
    public void map2EntitiesShouldReturnDtoWhenDtoesIncludeNullElement() {
        //given
        List<BookedDto> dtos = new ArrayList<>();
        dtos.add(null);
        dtos.add(null);
        //when
        List<Booked> result = mapper.map2Entities(dtos);
        //then
        assertNotNull(dtos);
        assertNotNull(result);
        assertEquals(dtos.size(),result.size());
        assertNull(dtos.get(0));
        assertNull(dtos.get(1));
    }

    @Test
    public void map2ToesShouldReturnListOfDto() {
        //given
        List<Booked> entities = new ArrayList<>();
        entities.add( this.createBooked(1L,34L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now()));
        entities.add( this.createBooked(2L,12L,11.0,SoldType.SOLD_INTERNET,BookedState.RETURNED,LocalDateTime.now().minusHours(10)));
        //when
        List<BookedDto> result = mapper.map2Toes(entities);
        //then
        assertNotNull(entities);
        assertNotNull(result);
        assertEquals(entities.size(),result.size());
        checkAllParametersInDtoAndEntityAreNotNull(entities.get(0),result.get(0));
        checkAllParametersInDtoAndEntityAreNotNull(entities.get(1),result.get(1));
        compareEntityWithDto(entities.get(0), result.get(0));
        compareEntityWithDto(entities.get(1), result.get(1));
    }
    @Test
    public void map2EToesShouldReturnEmptyCollectionWhenEntitiesIsNull() {
        //given
        List<Booked> expect = null;
        //when
        List<BookedDto> result = mapper.map2Toes(expect);
        //then
        assertTrue(result.isEmpty());
    }
    @Test
    public void map2EToesShouldReturnEmptyCollectionWhenEntitiesIsEmpty() {
        //given
        List<Booked> expect = new ArrayList<>();
        //when
        List<BookedDto> result = mapper.map2Toes(expect);
        //then
        assertTrue(result.isEmpty());
    }
    @Test
    public void map2ToesShouldReturnDtoWhenEntitiesIncludeElementWithAllNullParameters() {
        //given
        List<Booked> entities = new ArrayList<>();
        entities.add(this.createBooked(null,null,null,null,null,null));
        entities.add(this.createBooked(null,null,null,null,null,null));
        entities.add( this.createBooked(1L,34L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now()));
        //when
        List<BookedDto> result = mapper.map2Toes(entities);
        //then
        assertNotNull(entities);
        assertNotNull(result);
        assertEquals(entities.size(),result.size());
        checkAllParametersInDtoAndEntityAreNull(entities.get(0),result.get(0));
        checkAllParametersInDtoAndEntityAreNull(entities.get(1),result.get(1));
        checkAllParametersInDtoAndEntityAreNotNull(entities.get(2),result.get(2));
        compareEntityWithDto(entities.get(2), result.get(2));
    }
    @Test
    public void map2ToesShouldReturnDToesWhenEntitiesIncludeNullElement() {
        //given
        List<Booked> entities = new ArrayList<>();
        entities.add(null);
        entities.add(null);
        //when
        List<BookedDto> result = mapper.map2Toes(entities);
        //then
        assertNotNull(entities);
        assertNotNull(result);
        assertEquals(entities.size(),result.size());
        assertNull(entities.get(0));
        assertNull(entities.get(1));
    }


    private void checkAllParametersInDtoAndEntityAreNotNull(Booked entity,BookedDto dto){
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getReservationNumber());
        assertNotNull(entity.getPrice());
        assertNotNull(entity.getSoldType());
        assertNotNull(entity.getBookedState());
        assertNotNull(entity.getBuyingDate());
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getReservationNumber());
        assertNotNull(dto.getPrice());
        assertNotNull(dto.getSoldType());
        assertNotNull(dto.getBookedState());
        assertNotNull(dto.getBuyingDate());
    }
    private void checkAllParametersInDtoAndEntityAreNull(Booked entity,BookedDto dto){
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getReservationNumber());
        assertNull(entity.getPrice());
        assertNull(entity.getSoldType());
        assertNull(entity.getBookedState());
        assertNull(entity.getBuyingDate());
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getReservationNumber());
        assertNull(dto.getPrice());
        assertNull(dto.getSoldType());
        assertNull(dto.getBookedState());
        assertNull(dto.getBuyingDate());
    }
    private void compareEntityWithDto(Booked client, BookedDto clientDto){
        assertEquals(client.getId(), clientDto.getId());
        assertEquals(client.getReservationNumber(), clientDto.getReservationNumber());
        assertEquals(client.getPrice(), clientDto.getPrice());
        assertEquals(client.getSoldType(), clientDto.getSoldType());
        assertEquals(client.getBookedState(), clientDto.getBookedState());
        assertEquals(client.getBuyingDate(), clientDto.getBuyingDate());
    }

    private BookedDto createBookedDto(Long id, Long reservationNumber, Double price, SoldType soldType, BookedState bookedState, LocalDateTime buyingDate) {
        return BookedDto.builder().withId(id).withReservationNumber(reservationNumber).withPrice(price).withSoldType(soldType).withBookedState(bookedState).withBuyingDate(buyingDate).build();
    }
    private Booked createBooked(Long id, Long reservationNumber, Double price, SoldType soldType, BookedState bookedState, LocalDateTime buyingDate) {
        return new Booked(id,reservationNumber,price,soldType,bookedState,buyingDate,null,null);
    }
}
