package com.example.airport.persistance.validation;

import com.example.airport.domain.enumeration.FlyType;
import com.example.airport.domain.to.FlightScheduleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FlightSchedulesValidatorTest {

    @Autowired
    private FlightScheduleValidator  validator;

    @Test
    public void addValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        FlightScheduleDto dto = null;
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        FlightScheduleDto dto = null;
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrue() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(null,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(0L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenNameIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,null,LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenNameIsEmpty() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenStartDateIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",null,LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenArriveDateIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),null,"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenArriveTimeIsBeforeStartTime() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(0L,"alfa",LocalDateTime.now(),LocalDateTime.now().minusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenDescriptionIsEmpty() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenDescriptionIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),null,"Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenDestinationIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis",null,FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenDestinationIsEmpty() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenFlyTypeIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",null);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnTrue() {
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertTrue(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenIdIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(null,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }

    @Test
    public void updateValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(0L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenNameIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,null,LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenNameIsEmpty() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenStartDateIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",null,LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenArriveDateIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),null,"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenArriveTimeIsBeforeStartTime() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(0L,"alfa",LocalDateTime.now(),LocalDateTime.now().minusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnTrueWhenDescriptionIsEmpty() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"","Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertTrue(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnTrueWhenDescriptionIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),null,"Wroclaw",FlyType.LOCAL);
        //when
        //then
        assertTrue(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDestinationIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis",null,FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDestinationIsEmpty() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","",FlyType.LOCAL);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenFlyTypeIsNull() {
        //given
        FlightScheduleDto dto = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",null);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }


    private FlightScheduleDto createFlightScheduleDto(Long id, String name, LocalDateTime startTime, LocalDateTime arrive, String description, String destination, FlyType flyType) {
        return FlightScheduleDto.builder().withId(id).withName(name).withStartTime(startTime).withArriveTime(arrive).withDescription(description).withDestination(destination).withFlyType(flyType).build();
    }
}
