package com.example.airport.persistance.mapper;

import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.to.PlaneDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PlaneMapperImplTest {
    @Autowired
    private PlaneMapper mapper;

    @Test
    public void map2EntityShouldReturnEntity() {
        //given
        PlaneDto expect = this.createPlaneDto(1L,"123123","Test.Sp.Zoo");  //when
        Plane result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(result,expect);
        compareEntityWithDto(result, expect);
    }
    @Test
    public void map2EntityShouldReturnNullWhenDtoIsNull() {
        //given
        PlaneDto expect = null;
        //when
        Plane result = mapper.map2Entity(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2EntityShouldReturnEntityWhenAllDtoParametersAreNull() {
        //given
        PlaneDto expect = this.createPlaneDto(null,null,null);//when
        Plane result = mapper.map2Entity(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(result,expect);
    }


    @Test
    public void map2DtoShouldReturnDto() {
        //given
        Plane expect = this.createPlane(1L,"123123","Test.Sp.Zoo");
        //when
        PlaneDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNotNull(expect,result);
        compareEntityWithDto(expect, result);
    }
    @Test
    public void map2DtoShouldReturnNullWhenEntityIsNull() {
        //given
        Plane expect  = null;
        //when
        PlaneDto result = mapper.map2To(expect);
        //then
        assertNull(result);
    }
    @Test
    public void map2DtoShouldReturnEntityWhenAllEntityParametersAreNull() {
        //given
        Plane expect = this.createPlane(null,null,null);
        //when
        PlaneDto result = mapper.map2To(expect);
        //then
        checkAllParametersInDtoAndEntityAreNull(expect,result);
    }

    @Test
    public void map2EntitiesShouldReturnListOfEntity() {
        //given
        List<PlaneDto> dtos = new ArrayList<>();
        dtos.add(this.createPlaneDto(1L,"123123","Test.Sp.Zoo"));
        dtos.add(this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo"));
        //when
        List<Plane> result = mapper.map2Entities(dtos);
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
        List<PlaneDto> expect = null;
        //when
        List<Plane> result = mapper.map2Entities(expect);
        //then
        assertTrue(result.isEmpty());
    }
    @Test
    public void map2EntitiesShouldReturnEmptyCollectionWhenDtoesIsEmpty() {
        //given
        List<PlaneDto> expect = new ArrayList<>();
        //when
        List<Plane> result = mapper.map2Entities(expect);
        //then
        assertTrue(result.isEmpty());
    }
    @Test
    public void map2EntitiesShouldReturnDtoesWhenDtoesIncludeElementWithAllNullParameters() {
        //given
        List<PlaneDto> dtos = new ArrayList<>();
        dtos.add(this.createPlaneDto(null,null,null));
        dtos.add( this.createPlaneDto(null,null,null));
        dtos.add( this.createPlaneDto(2L,"123123123","Test.Sp.Zoo"));
        //when
        List<Plane> result = mapper.map2Entities(dtos);
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
        List<PlaneDto> dtos = new ArrayList<>();
        dtos.add(null);
        dtos.add(null);
        //when
        List<Plane> result = mapper.map2Entities(dtos);
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
        List<Plane> entities = new ArrayList<>();
        entities.add(this.createPlane(1L,"123123","Test.Sp.Zoo"));
        entities.add(this.createPlane(2L,"32131231","Test2.Sp.Zoo"));
        //when
        List<PlaneDto> result = mapper.map2Toes(entities);
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
        List<Plane> expect = null;
        //when
        List<PlaneDto> result = mapper.map2Toes(expect);
        //then
        assertTrue(result.isEmpty());
    }
    @Test
    public void map2EToesShouldReturnEmptyCollectionWhenEntitiesIsEmpty() {
        //given
        List<Plane> expect = new ArrayList<>();
        //when
        List<PlaneDto> result = mapper.map2Toes(expect);
        //then
        assertTrue(result.isEmpty());
    }
    @Test
    public void map2ToesShouldReturnDtoWhenEntitiesIncludeElementWithAllNullParameters() {
        //given
        List<Plane> entities = new ArrayList<>();
        entities.add(this.createPlane(null,null,null));
        entities.add( this.createPlane(null,null,null));
        entities.add( this.createPlane(2L,"123123123","Test.Sp.Zoo"));
        //when
        List<PlaneDto> result = mapper.map2Toes(entities);
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
        List<Plane> entities = new ArrayList<>();
        entities.add(null);
        entities.add(null);
        //when
        List<PlaneDto> result = mapper.map2Toes(entities);
        //then
        assertNotNull(entities);
        assertNotNull(result);
        assertEquals(entities.size(),result.size());
        assertNull(entities.get(0));
        assertNull(entities.get(1));
    }


    private void checkAllParametersInDtoAndEntityAreNotNull(Plane entity, PlaneDto dto){
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getSerialNumber());
        assertNotNull(entity.getNameCarrier());
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getSerialNumber());
        assertNotNull(dto.getNameCarrier());
    }
    private void checkAllParametersInDtoAndEntityAreNull(Plane entity, PlaneDto dto){
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getSerialNumber());
        assertNull(entity.getNameCarrier());
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getSerialNumber());
        assertNull(dto.getNameCarrier());
    }
    private void compareEntityWithDto(Plane client, PlaneDto clientDto){
        assertEquals(client.getId(), clientDto.getId());
        assertEquals(client.getSerialNumber(), clientDto.getSerialNumber());
        assertEquals(client.getNameCarrier(), clientDto.getNameCarrier());
    }

    private PlaneDto createPlaneDto(Long id, String serialNumber, String nameCarrier) {
        return PlaneDto.builder().withId(id).withSerialNumber(serialNumber).withNameCarrier(nameCarrier).build();
    }
    private Plane createPlane(Long id, String serialNumber, String nameCarrier) {
        return new Plane(id,serialNumber,nameCarrier,null,null);
    }

}

