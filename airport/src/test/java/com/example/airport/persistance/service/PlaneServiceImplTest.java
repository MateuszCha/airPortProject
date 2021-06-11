package com.example.airport.persistance.service;

import com.example.airport.domain.to.PlaneDto;
import com.example.airport.persistance.exception.DifferentVersion;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.repository.FlightScheduleRepository;
import com.example.airport.persistance.repository.SeatRepository;
import com.example.airport.persistance.service.crud.PlaneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PlaneServiceImplTest {
    
    @Autowired
    private PlaneService service;
    
    @Test
    public void addShouldAddElement() {
        //given
        PlaneDto expect = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");   
        //when
        PlaneDto result = service.add(expect);
        //then
        assertNotNull(result);
        this.comparePlaneDtoObject(expect,result);
    }
    @Test
    public void addShouldAddElementWhenElementOnIndexExistOnDb() {
        //given
        PlaneDto dto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto expect = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        //when
        List<PlaneDto> beforeAdd = service.getAll();
        service.add(dto1);
        PlaneDto result = service.add(expect);
        List<PlaneDto> afterAdd = service.getAll();
        //then
        assertEquals(0,beforeAdd.size());
        assertEquals(2,afterAdd.size());
        assertNotNull(result);
    }
    @Test(expected = IllegalArgumentException.class)
    public void addShouldThrowExceptionWhenElementIsNull() {
        //given
        PlaneDto dto1 = null;
        //when
        service.add(dto1);
        //then
    }

    @Test
    public void getShouldReturnElementOnIndex() {
        //given
        Long index = 2L;
        PlaneDto dto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto expect = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        //when
        service.add(dto1);
        service.add(expect);
        PlaneDto result = service.get(index);
        //then
        this.checkAllParametersInDtoAreNotNull(result);
        assertEquals(index, result.getId());
        this.comparePlaneDtoObject(expect,result);
    }

    @Test(expected = NoFoundEntity.class)
    public void getShouldThrowExceptionWhenNotFindElement() {
        //given
        Long index = 14L;
        PlaneDto planeDto = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        //when
        service.add(planeDto);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long index = 0L;
        PlaneDto planeDto = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        //when
        service.add(planeDto);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long index = Long.MIN_VALUE;
        PlaneDto planeDto = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        //when
        service.add(planeDto);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long index = null;
        PlaneDto planeDto = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        //when
        service.add(planeDto);
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
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        //when
        service.add(planeDto1);
        service.add(planeDto2);
        List<PlaneDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(2,resultList.size());
        assertNotNull(resultList.get(0));
        assertNotNull(resultList.get(1));
        this.comparePlaneDtoObject(planeDto1,resultList.get(0));
        this.comparePlaneDtoObject(planeDto2,resultList.get(1));
    }
    @Test
    public void getAllShouldReturnZeroWhenDBisEmpty() {
        //given
        // when
        List<PlaneDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(0,resultList.size());
    }

    @Test
    public void removeShouldRemoveElement() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        Long removeIndex = 2L;
        //when
        service.add(planeDto1);
        service.add(planeDto2);
        List<PlaneDto> beforeRemove = service.getAll();
        PlaneDto result = service.remove(removeIndex);
        List<PlaneDto> afterRemove = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(2,beforeRemove.size());
        assertEquals(1,afterRemove.size());
        this.comparePlaneDtoObject(beforeRemove.get(0),afterRemove.get(0));
        this.comparePlaneDtoObject(beforeRemove.get(1),result);
    }

    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenIndexNotExist() {
        //given
        PlaneDto planeDto = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        Long removeIndex = 4L;
        //when
        service.add(planeDto);
        PlaneDto result = service.remove(removeIndex);
        //then
    }
    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenDBisEmpty() {
        //given
        Long removeIndex = 4L;
        //when
        PlaneDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long removeIndex = 0L;
        //when
        PlaneDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long removeIndex = Long.MIN_VALUE;
        //when
        PlaneDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long removeIndex = null;
        //when
        PlaneDto result = service.remove(removeIndex);
        //then
    }
    @Test
    public void updateShouldUpdateElement() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        //when
        service.add(planeDto1);
        PlaneDto expect = service.add(planeDto2);
        expect.setNameCarrier("Polska.SA");
        expect.setSerialNumber("333333");
        List<PlaneDto> beforeUpdate = service.getAll();
        PlaneDto result = service.update(expect);
        List<PlaneDto> afterUpdate = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(beforeUpdate.size(), afterUpdate.size());
        this.comparePlaneDtoObject(beforeUpdate.get(0),afterUpdate.get(0));
        this.comparePlaneDtoObject(afterUpdate.get(1),result);
    }
    @Test(expected = NoFoundEntity.class)
    public void updateShouldThrowExceptionWhenIdNoExist() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        //when
        service.add(planeDto1);
        PlaneDto expect = service.add(planeDto2);
        expect.setId(5L);
        expect.setNameCarrier("Polska.SA");
        expect.setSerialNumber("333333");
        service.update(expect);
        //then
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowExceptionWhenElementIsNull() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        //when
        service.add(planeDto1);
        service.add(planeDto2);
        service.update(null);
        //then
    }
    @Test
    public void updateShouldChangeVersionWhenUpdateExistObject() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        //when
        service.add(planeDto1);
        PlaneDto expect = service.add(planeDto2);
        expect.setNameCarrier("14");
        List<PlaneDto> beforeUpdate = service.getAll();
        service.update(expect);
        List<PlaneDto> afterUpdate = service.getAll();
        PlaneDto result = service.get(expect.getId());
        //then
        assertNotNull(result);
        assertEquals(beforeUpdate.size(), afterUpdate.size());
        this.comparePlaneDtoObject(beforeUpdate.get(0),afterUpdate.get(0));
        this.comparePlaneDtoObject(afterUpdate.get(1),result);
        assertEquals(expect.getVersion() + 1, result.getVersion());
    }
    @Test(expected = DifferentVersion.class)
    public void updateShouldThrowExceptionWhenVersionIsDifferent() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        //when
        service.add(planeDto1);
        PlaneDto expect = service.add(planeDto2);
        expect.setVersion(23);
        PlaneDto result = service.update(expect);
    }
    @Test
    public void setToRemoveShouldSetElementToRemove() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        service.add(planeDto1);
        service.add(planeDto2);
        //when
        service.setToRemove(2L);
        List<PlaneDto> expect = service.getAll();
        //then
        assertEquals(2,expect.size());
        assertEquals(false,expect.get(0).isRemove());
        assertEquals(true,expect.get(1).isRemove());
    }
    @Test(expected = IllegalIndexEntity.class)
    public void setToRemoveShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        service.add(planeDto1);
        service.add(planeDto2);
        //when
        service.setToRemove(0L);

    }
    @Test(expected = IllegalIndexEntity.class)
    public void setToRemoveShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        service.add(planeDto1);
        service.add(planeDto2);
        //when
        service.setToRemove(-1L);

    }
    @Test(expected = NoFoundEntity.class)
    public void setToRemoveShouldThrowExceptionWhenIEntityNotFound() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        service.add(planeDto1);
        service.add(planeDto2);
        //when
        service.setToRemove(3L);
    }


    private void checkAllParametersInDtoAreNotNull(PlaneDto dto){
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getSerialNumber());
        assertNotNull(dto.getNameCarrier());
    }
    private void checkAllParametersInDtoAreNull(PlaneDto dto){
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getSerialNumber());
        assertNull(dto.getNameCarrier());
    }
    private void comparePlaneDtoObject(PlaneDto expect, PlaneDto result){
        assertEquals(expect.getSerialNumber(), result.getSerialNumber());
        assertEquals(expect.getNameCarrier(), result.getNameCarrier());
    }

    private PlaneDto createPlaneDto(Long id, String serialNumber, String nameCarrier) {
        return PlaneDto.builder().withId(id).withSerialNumber(serialNumber).withNameCarrier(nameCarrier).build();
    }
}
