package com.example.airport.persistance.service;

import com.example.airport.domain.entity.Client;
import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.enumeration.*;
import com.example.airport.domain.to.FlightScheduleDto;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.repository.BookedRepository;
import com.example.airport.persistance.repository.ClientRepository;
import com.example.airport.persistance.repository.PlaneRepository;
import com.example.airport.persistance.repository.SeatRepository;
import com.example.airport.persistance.service.crud.FlightScheduleService;
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

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlightScheduleServiceImplTest {


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
    public void addShouldAddElement() {
        //given
        List<Plane> planes = planeRepository.findAll();
        FlightScheduleDto expect = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        FlightScheduleDto result = service.add(expect,1L);
        //then
        assertNotNull(result);
        this.compareFlightSchedulesDto(expect,result);
    }
    @Test
    public void addShouldAddElementWhenElementOnIndexExistOnDb() {
        //given
        FlightScheduleDto flightScheduleDto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        FlightScheduleDto expect = this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL);
        //when
        List<FlightScheduleDto> beforeAdd = service.getAll();
        service.add(flightScheduleDto,1L);
        FlightScheduleDto result = service.add(expect,2L);
        List<FlightScheduleDto> afterAdd = service.getAll();
        //then
        assertEquals(0,beforeAdd.size());
        assertEquals(2,afterAdd.size());
        assertNotNull(result);
    }
    @Test(expected = IllegalArgumentException.class)
    public void addShouldThrowExceptionWhenElementIsNull() {
        //given
        FlightScheduleDto dto1 = null;
        //when
        service.add(dto1,1L);
        //then
    }

    @Test
    public void getShouldReturnElementOnIndex() {
        //given
        Long index = 2L;
        FlightScheduleDto flightScheduleDto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        FlightScheduleDto expect = this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL);
        //when
        service.add(flightScheduleDto,1L);
        service.add(expect,1L);
        FlightScheduleDto result = service.get(index);
        //then
        this.checkAllParametersInDtoAreNotNull(result);
        assertEquals(index, result.getId());
        this.compareFlightSchedulesDto(expect,result);
    }

    @Test(expected = NoFoundEntity.class)
    public void getShouldThrowExceptionWhenNotFindElement() {
        //given
        Long index = 14L;
        FlightScheduleDto flightScheduleDto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        service.add(flightScheduleDto,1L);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long index = 0L;
        FlightScheduleDto flightScheduleDto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        service.add(flightScheduleDto,2L);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long index = Long.MIN_VALUE;
        FlightScheduleDto flightScheduleDto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        service.add(flightScheduleDto,2L);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long index = null;
        FlightScheduleDto flightScheduleDto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        service.add(flightScheduleDto,1L);
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
        FlightScheduleDto flightScheduleDto1 = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        FlightScheduleDto flightScheduleDto2 = this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL);
        //when
        service.add(flightScheduleDto1,2L);
        service.add(flightScheduleDto2,2L);
        List<FlightScheduleDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(2,resultList.size());
        assertNotNull(resultList.get(0));
        assertNotNull(resultList.get(1));
        this.compareFlightSchedulesDto(flightScheduleDto1,resultList.get(0));
        this.compareFlightSchedulesDto(flightScheduleDto2,resultList.get(1));
    }
    @Test
    public void getAllShouldReturnZeroWhenDBisEmpty() {
        //given
        // when
        List<FlightScheduleDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(0,resultList.size());
    }

    @Test
    public void removeShouldRemoveElement() {
        //given
        FlightScheduleDto flightScheduleDto1 = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        FlightScheduleDto flightScheduleDto2 = this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL);
        Long removeIndex = 2L;
        //when
        service.add(flightScheduleDto1,2L);
        service.add(flightScheduleDto2,1L);
        List<FlightScheduleDto> beforeRemove = service.getAll();
        FlightScheduleDto result = service.remove(removeIndex);
        List<FlightScheduleDto> afterRemove = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(2,beforeRemove.size());
        assertEquals(1,afterRemove.size());
        this.compareFlightSchedulesDto(beforeRemove.get(0),afterRemove.get(0));
        this.compareFlightSchedulesDto(beforeRemove.get(1),result);
    }

    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenIndexNotExist() {
        //given
        FlightScheduleDto flightScheduleDto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        Long removeIndex = 4L;
        //when
        service.add(flightScheduleDto,2L);
        FlightScheduleDto result = service.remove(removeIndex);
        //then
    }
    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenDBisEmpty() {
        //given
        Long removeIndex = 4L;
        //when
        FlightScheduleDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long removeIndex = 0L;
        //when
        FlightScheduleDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long removeIndex = Long.MIN_VALUE;
        //when
        FlightScheduleDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long removeIndex = null;
        //when
        FlightScheduleDto result = service.remove(removeIndex);
        //then
    }
    @Test
    public void updateShouldUpdateElement() {
        //given
        FlightScheduleDto flightScheduleDto1 = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        FlightScheduleDto flightScheduleDto2 = this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL);
        //when
        service.add(flightScheduleDto1,2L);
        FlightScheduleDto expect = service.add(flightScheduleDto2,2L);
        expect.setArriveTime(LocalDateTime.now().plusHours(15));
        expect.setDescription("lubie placki");
        expect.setDestination("Ale Co sie stalosie xD");
        List<FlightScheduleDto> beforeUpdate = service.getAll();
        FlightScheduleDto result = service.update(expect,null);
        List<FlightScheduleDto> afterUpdate = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(beforeUpdate.size(), afterUpdate.size());
        this.compareFlightSchedulesDto(beforeUpdate.get(0),afterUpdate.get(0));
        this.compareFlightSchedulesDto(afterUpdate.get(1),result);
    }
    @Test(expected = NoFoundEntity.class)
    public void updateShouldThrowExceptionWhenIdNoExist() {
        //given
        FlightScheduleDto flightScheduleDto1 = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        FlightScheduleDto flightScheduleDto2 = this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL);
        //when
        service.add(flightScheduleDto1,2L);
        FlightScheduleDto expect = service.add(flightScheduleDto2,2L);
        expect.setId(5L);
        expect.setFlyType(FlyType.PRIVET);
        service.update(expect,null);
        //then
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowExceptionWhenElementIsNull() {
        //given
        FlightScheduleDto flightScheduleDto1 = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        FlightScheduleDto flightScheduleDto2 = this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL);
        //when
        service.add(flightScheduleDto1,2L);
        service.add(flightScheduleDto2,1L);
        service.update(null,null);
        //then
    }

    private void checkAllParametersInDtoAreNotNull(FlightScheduleDto dto){      
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getStartTime());
        assertNotNull(dto.getArriveTime());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getDestination());
        assertNotNull(dto.getFlyType());
    }
    private void checkAllParametersInDtoAreNull(FlightScheduleDto dto){    
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getStartTime());
        assertNull(dto.getArriveTime());
        assertNull(dto.getDescription());
        assertNull(dto.getDestination());
        assertNull(dto.getFlyType());
    }
    private void compareFlightSchedulesDto(FlightScheduleDto expect, FlightScheduleDto result){
        assertEquals(expect.getName(), result.getName());
        assertEquals(expect.getStartTime(), result.getStartTime());
        assertEquals(expect.getArriveTime(), result.getArriveTime());
        assertEquals(expect.getDescription(), result.getDescription());
        assertEquals(expect.getDestination(), result.getDestination());
        assertEquals(expect.getFlyType(), result.getFlyType());
    }

    private FlightScheduleDto createFlightScheduleDto(Long id, String name, LocalDateTime startTime, LocalDateTime arrive, String description, String destination, FlyType flyType) {
        return FlightScheduleDto.builder().withId(id).withName(name).withStartTime(startTime).withArriveTime(arrive).withDescription(description).withDestination(destination).withFlyType(flyType).build();
    }


}
