package com.example.airport.persistance.validation;

import com.example.airport.domain.to.PlaneDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PlaneValidatorTest {

    @Autowired
    private PlaneValidator validator;

    @Test
    public void addValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        PlaneDto dto = null;
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        PlaneDto dto = null;
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrue() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsNull() {
        //given
        PlaneDto dto =  this.createPlaneDto(null,"123123123","Test.Sp.Zoo");
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        PlaneDto dto =  this.createPlaneDto(0L,"123123123","Test.Sp.Zoo");
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenSerialNumberIsNull() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,null,"Test.Sp.Zoo");
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenSerialNumberIsEmpty() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,"","Test.Sp.Zoo");
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenCarrierNameIsNull() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,"123123123",null);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenCarrierNameIsEmpty() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,"123123123","");
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnTrue() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        //when
        //then
        assertTrue(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenIdIsNull() {
        //given
        PlaneDto dto =  this.createPlaneDto(null,"123123123","Test.Sp.Zoo");
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }

    @Test
    public void updateValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        PlaneDto dto =  this.createPlaneDto(0L,"123123123","Test.Sp.Zoo");
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenSerialNumberNameIsNull() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,null,"Test.Sp.Zoo");
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenSerialNumberIsEmpty() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,"","Test.Sp.Zoo");
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenCarrierNameIsNull() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,"123123123",null);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenCarrierNameIsEmpty() {
        //given
        PlaneDto dto =  this.createPlaneDto(2L,"123123123","");
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }

    private PlaneDto createPlaneDto(Long id, String serialNumber, String nameCarrier) {
        return PlaneDto.builder().withId(id).withSerialNumber(serialNumber).withNameCarrier(nameCarrier).build();
    }
}
