package com.example.airport.persistance.validation;

import com.example.airport.domain.enumeration.CategoryType;
import com.example.airport.domain.enumeration.SeatPosition;
import com.example.airport.domain.to.SeatDto;
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
public class SeatValidatorTest {
    
    @Autowired
    private SeatValidator validator;


    @Test
    public void addValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        SeatDto dto = null;
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }

    @Test
    public void addValidationShouldReturnTrue() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsNull() {
        //given
        SeatDto dto = this.createSeatDto(null,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        SeatDto dto = this.createSeatDto(0L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenRowIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,null,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenRoweIsLessThanZero() {
        //given
        SeatDto dto = this.createSeatDto(1L,-1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenColumnIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,null,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenColumnIsLessThanZero() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,-1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenCategoryIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,1,null,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenSeatPositionIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,null,true);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenEnableIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,null);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        SeatDto dto = null;
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnTrue() {
        //give
        SeatDto dto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertTrue(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenIdIsNull() {
        //given
        SeatDto dto = this.createSeatDto(null,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }

    @Test
    public void updateValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        SeatDto dto = this.createSeatDto(0L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenRowIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,null,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenRoweIsLessThanZero() {
        //given
        SeatDto dto = this.createSeatDto(1L,-1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenColumnIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,null,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenColumnIsLessThanZero() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,-1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenCategoryIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,1,null,SeatPosition.LEFT,true);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenSeatPositionIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,null,true);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenEnableIsNull() {
        //given
        SeatDto dto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,null);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }

    private SeatDto createSeatDto(Long id, Integer row, Integer column, CategoryType categoryType, SeatPosition position, Boolean enable) {
        return SeatDto.builder().withId(id).withRowNumber(row).withColumnNumber(column).withCategoryType(categoryType).withSeatPosition(position).withEnableSeat(enable).build();
    }
}
