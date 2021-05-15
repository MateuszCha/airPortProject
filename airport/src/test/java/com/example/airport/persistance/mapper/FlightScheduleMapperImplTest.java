package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.FlightSchedule;
import com.example.airport.domain.enumeration.FlyType;
import com.example.airport.domain.to.FlightScheduleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FlightScheduleMapperImplTest {
    
    @Autowired
    private FlightScheduleMapper mapper;

    @Test
    public void map2EntityShouldReturnEntity() {
        //given
        FlightScheduleDto expect = this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        FlightSchedule result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(result,expect);
        compareEntityWithDto(result, expect);
    }
    @Test
    public void map2EntityShouldReturnNullWhenDtoIsNull() {
        //given
        FlightScheduleDto expect = null;
        //when
        FlightSchedule result = mapper.map2Entity(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntityShouldReturnEntityWhenAllDtoParametersAreNull() {
        //given
        FlightScheduleDto expect = this.createFlightScheduleDto(null,null,null,null,null,null,null);
        //when
        FlightSchedule result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(result,expect);
    }


    @Test
    public void map2DtoShouldReturnDto() {
        //given
        FlightSchedule expect = this.createFlightSchedule(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL);
        //when
        FlightScheduleDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(expect,result);
        compareEntityWithDto(expect, result);
    }
    @Test
    public void map2DtoShouldReturnNullWhenEntityIsNull() {
        //given
        FlightSchedule expect  = null;
        //when
        FlightScheduleDto result = mapper.map2To(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2DtoShouldReturnEntityWhenAllEntityParametersAreNull() {
        //given
        FlightSchedule expect = this.createFlightSchedule(null,null,null,null,null,null,null);
        //when
        FlightScheduleDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(expect,result);
    }

    @Test
    public void map2EntitiesShouldReturnListOfEntity() {
        //given
        List<FlightScheduleDto> dtos = new ArrayList<>();
        dtos.add( this.createFlightScheduleDto(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL));
        dtos.add( this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL));
        //when
        List<FlightSchedule> result = mapper.map2Entities(dtos);
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
    public void map2EntitiesShouldReturnNullWhenDtoesIsNull() {
        //given
        List<FlightScheduleDto> expect = null;
        //when
        List<FlightSchedule> result = mapper.map2Entities(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntitiesShouldReturnNullWhenDtoesIsEmpty() {
        //given
        List<FlightScheduleDto> expect = new ArrayList<>();
        //when
        List<FlightSchedule> result = mapper.map2Entities(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntitiesShouldReturnDtoesWhenDtoesIncludeElementWithAllNullParameters() {
        //given
        List<FlightScheduleDto> dtos = new ArrayList<>();
        dtos.add(this.createFlightScheduleDto(null,null,null,null,null,null,null));
        dtos.add( this.createFlightScheduleDto(null,null,null,null,null,null,null));
        dtos.add( this.createFlightScheduleDto(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL));
        //when
        List<FlightSchedule> result = mapper.map2Entities(dtos);
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
        List<FlightScheduleDto> dtos = new ArrayList<>();
        dtos.add(null);
        dtos.add(null);
        //when
        List<FlightSchedule> result = mapper.map2Entities(dtos);
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
        List<FlightSchedule> entities = new ArrayList<>();
        entities.add( this.createFlightSchedule(1L,"alfa",LocalDateTime.now(),LocalDateTime.now().plusHours(12L),"opis","Wroclaw",FlyType.LOCAL));
        entities.add( this.createFlightSchedule(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL));
        //when
        List<FlightScheduleDto> result = mapper.map2Toes(entities);
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
    public void map2EToesShouldReturnNullWhenEntitiesIsNull() {
        //given
        List<FlightSchedule> expect = null;
        //when
        List<FlightScheduleDto> result = mapper.map2Toes(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EToesShouldReturnNullWhenEntitiesIsEmpty() {
        //given
        List<FlightSchedule> expect = new ArrayList<>();
        //when
        List<FlightScheduleDto> result = mapper.map2Toes(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2ToesShouldReturnDtoWhenEntitiesIncludeElementWithAllNullParameters() {
        //given
        List<FlightSchedule> entities = new ArrayList<>();
        entities.add(this.createFlightSchedule(null,null,null,null,null,null,null));
        entities.add( this.createFlightSchedule(null,null,null,null,null,null,null));
        entities.add( this.createFlightSchedule(2L,"beta",LocalDateTime.now(),LocalDateTime.now().plusHours(2L),"opis2","Wroclaw2",FlyType.INTERNATIONAL));
        //when
        List<FlightScheduleDto> result = mapper.map2Toes(entities);
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
        List<FlightSchedule> entities = new ArrayList<>();
        entities.add(null);
        entities.add(null);
        //when
        List<FlightScheduleDto> result = mapper.map2Toes(entities);
        //then
        assertNotNull(entities);
        assertNotNull(result);
        assertEquals(entities.size(),result.size());
        assertNull(entities.get(0));
        assertNull(entities.get(1));
    }


    private void checkAllParametersInDtoAndEntityAreNotNull(FlightSchedule entity, FlightScheduleDto dto){
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getStartTime());
        assertNotNull(entity.getArrive());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getDestination());
        assertNotNull(entity.getFlyType());
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getStartTime());
        assertNotNull(dto.getArrive());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getDestination());
        assertNotNull(dto.getFlyType());
    }
    private void checkAllParametersInDtoAndEntityAreNull(FlightSchedule entity, FlightScheduleDto dto){
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getStartTime());
        assertNull(entity.getArrive());
        assertNull(entity.getDescription());
        assertNull(entity.getDestination());
        assertNull(entity.getFlyType());
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getStartTime());
        assertNull(dto.getArrive());
        assertNull(dto.getDescription());
        assertNull(dto.getDestination());
        assertNull(dto.getFlyType());
    }
    private void compareEntityWithDto(FlightSchedule client, FlightScheduleDto clientDto){
        assertEquals(client.getId(), clientDto.getId());
        assertEquals(client.getName(), clientDto.getName());
        assertEquals(client.getStartTime(), clientDto.getStartTime());
        assertEquals(client.getArrive(), clientDto.getArrive());
        assertEquals(client.getDescription(), clientDto.getDescription());
        assertEquals(client.getDestination(), clientDto.getDestination());
        assertEquals(client.getFlyType(), clientDto.getFlyType());
    }

    private FlightScheduleDto createFlightScheduleDto(Long id, String name, LocalDateTime startTime, LocalDateTime arrive, String description, String destination, FlyType flyType) {
        return FlightScheduleDto.builder().withId(id).withName(name).withStartTime(startTime).withArriveTime(arrive).withDescription(description).withDestination(destination).withFlyType(flyType).build();
    }
    private FlightSchedule createFlightSchedule(Long id, String name, LocalDateTime startTime, LocalDateTime arrive, String description, String destination, FlyType flyType) {
        return new FlightSchedule(id,name,startTime,arrive,description,destination,flyType,null);
    }

}
