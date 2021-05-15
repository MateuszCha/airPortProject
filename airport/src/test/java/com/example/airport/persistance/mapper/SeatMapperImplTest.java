package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.enumeration.CategoryType;
import com.example.airport.domain.enumeration.SeatPosition;
import com.example.airport.domain.to.SeatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SeatMapperImplTest {

    @Autowired
    private SeatMapper mapper;

    @Test
    public void map2EntityShouldReturnEntity() {
        //given
        SeatDto expect = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        Seat result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(result,expect);
        compareEntityWithDto(result, expect);
    }
    @Test
    public void map2EntityShouldReturnNullWhenDtoIsNull() {
        //given
        SeatDto expect = null;
        //when
        Seat result = mapper.map2Entity(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntityShouldReturnEntityWhenAllDtoParametersAreNull() {
        //given
        SeatDto expect = this.createSeatDto(null,null,null,null,null,null);
        //when
        Seat result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(result,expect);
    }


    @Test
    public void map2DtoShouldReturnDto() {
        //given
        Seat expect =this.createSeat(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        SeatDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(expect,result);
        compareEntityWithDto(expect, result);
    }
    @Test
    public void map2DtoShouldReturnNullWhenEntityIsNull() {
        //given
        Seat expect  = null;
        //when
        SeatDto result = mapper.map2To(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2DtoShouldReturnEntityWhenAllEntityParametersAreNull() {
        //given
        Seat expect = this.createSeat(null,null,null,null,null,null);
        //when
        SeatDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(expect,result);
    }

    @Test
    public void map2EntitiesShouldReturnListOfEntity() {
        //given
        List<SeatDto> dtos = new ArrayList<>();
        dtos.add(this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true));
        dtos.add(this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false));

        //when
        List<Seat> result = mapper.map2Entities(dtos);
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
        List<SeatDto> expect = null;
        //when
        List<Seat> result = mapper.map2Entities(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntitiesShouldReturnNullWhenDtoesIsEmpty() {
        //given
        List<SeatDto> expect = new ArrayList<>();
        //when
        List<Seat> result = mapper.map2Entities(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntitiesShouldReturnDtoesWhenDtoesIncludeElementWithAllNullParameters() {
        //given
        List<SeatDto> dtos = new ArrayList<>();
        dtos.add(this.createSeatDto(null,null,null,null,null,null));
        dtos.add( this.createSeatDto(null,null,null,null,null,null));
        dtos.add(this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false));

        //when
        List<Seat> result = mapper.map2Entities(dtos);
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
        List<SeatDto> dtos = new ArrayList<>();
        dtos.add(null);
        dtos.add(null);
        //when
        List<Seat> result = mapper.map2Entities(dtos);
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
        List<Seat> entities = new ArrayList<>();
        entities.add(this.createSeat(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true));
        entities.add(this.createSeat(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false));

        //when
        List<SeatDto> result = mapper.map2Toes(entities);
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
        List<Seat> expect = null;
        //when
        List<SeatDto> result = mapper.map2Toes(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EToesShouldReturnNullWhenEntitiesIsEmpty() {
        //given
        List<Seat> expect = new ArrayList<>();
        //when
        List<SeatDto> result = mapper.map2Toes(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2ToesShouldReturnDtoWhenEntitiesIncludeElementWithAllNullParameters() {
        //given
        List<Seat> entities = new ArrayList<>();
        entities.add(this.createSeat(null,null,null,null,null,null));
        entities.add( this.createSeat(null,null,null,null,null,null));
        entities.add(this.createSeat(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false));

        //when
        List<SeatDto> result = mapper.map2Toes(entities);
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
        List<Seat> entities = new ArrayList<>();
        entities.add(null);
        entities.add(null);
        //when
        List<SeatDto> result = mapper.map2Toes(entities);
        //then
        assertNotNull(entities);
        assertNotNull(result);
        assertEquals(entities.size(),result.size());
        assertNull(entities.get(0));
        assertNull(entities.get(1));
    }


    private void checkAllParametersInDtoAndEntityAreNotNull(Seat entity, SeatDto dto){
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getRow());
        assertNotNull(entity.getColumn());
        assertNotNull(entity.getCategoryType());
        assertNotNull(entity.getPosition());
        assertNotNull(entity.getEnable());
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getRow());
        assertNotNull(dto.getColumn());
        assertNotNull(dto.getCategoryType());
        assertNotNull(dto.getPosition());
        assertNotNull(dto.getEnable());
    }
    private void checkAllParametersInDtoAndEntityAreNull(Seat entity, SeatDto dto){
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getRow());
        assertNull(entity.getColumn());
        assertNull(entity.getCategoryType());
        assertNull(entity.getPosition());
        assertNull(entity.getEnable());
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getRow());
        assertNull(dto.getColumn());
        assertNull(dto.getCategoryType());
        assertNull(dto.getPosition());
        assertNull(dto.getEnable());
    }
    private void compareEntityWithDto(Seat client, SeatDto clientDto){
        assertEquals(client.getId(), clientDto.getId());
        assertEquals(client.getRow(), clientDto.getRow());
        assertEquals(client.getColumn(), clientDto.getColumn());
        assertEquals(client.getCategoryType(), clientDto.getCategoryType());
        assertEquals(client.getPosition(), clientDto.getPosition());
        assertEquals(client.getEnable(), clientDto.getEnable());
    }

    private SeatDto createSeatDto(Long id, Integer row, Integer column, CategoryType categoryType, SeatPosition position, Boolean enable) {
        return SeatDto.builder().withId(id).withRowNumber(row).withColumnNumber(column).withCategoryType(categoryType).withSeatPosition(position).withEnableSeat(enable).build();
    }
    private Seat createSeat(Long id, Integer row, Integer column, CategoryType categoryType, SeatPosition position, Boolean enable) {
        return new Seat(id,row,column,categoryType,position,enable,null,null);
    }
}
