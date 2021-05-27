package com.example.airport.persistance.validation;

import com.example.airport.domain.enumeration.BookedState;
import com.example.airport.domain.enumeration.SoldType;
import com.example.airport.domain.to.BookedDto;
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
public class BookedValidatorTest {

    @Autowired
    private BookedValidator validator;

    @Test
    public void addValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        BookedDto dto = null;
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }

    @Test
    public void addValidationShouldReturnTrue() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsNull() {
        //given
        BookedDto dto = this.createBookedDto(null,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        BookedDto dto = this.createBookedDto(0L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertTrue(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenReservationNumberIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,null,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenReservationNumberIsLessThan1000() {
        //given
        BookedDto dto = this.createBookedDto(1L,990L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenReservationNumberIsNegative() {
        //given
        BookedDto dto = this.createBookedDto(1L,990L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenPriceIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,null,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenPriceIsNegative() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,-0.1,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenSoldTypeIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,null,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenDBookedStateIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,null,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenBuyingDataIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,null);
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void addValidationShouldReturnFalseWhenBuyingTimeIsAfterCurrentTime() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now().plusHours(3L));
        //when
        //then
        assertFalse(validator.addValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenDtoIsNull() {
        //given
        BookedDto dto = null;
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnTrue() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertTrue(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenIdIsNull() {
        //given
        BookedDto dto = this.createBookedDto(null,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }

    @Test
    public void updateValidationShouldReturnTrueWhenIdIsLessThanOne() {
        //given
        BookedDto dto = this.createBookedDto(0L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenReservationNumberIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,null,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenReservationNumberIsLessThan1000() {
        //given
        BookedDto dto = this.createBookedDto(1L,990L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenReservationNumberIsNegative() {
        //given
        BookedDto dto = this.createBookedDto(1L,990L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenPriceIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,null,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenPriceIsNegative() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,-0.1,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenSoldTypeIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,null,BookedState.SOLD,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnfalseWhenDBookedStateIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,null,LocalDateTime.now());
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenBuyingDataIsNull() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,null);
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }
    @Test
    public void updateValidationShouldReturnFalseWhenBuyingTimeIsAfterCurrentTime() {
        //given
        BookedDto dto = this.createBookedDto(1L,1001L,34.0,SoldType.SOLD_AIRPORT,BookedState.SOLD,LocalDateTime.now().plusHours(3L));
        //when
        //then
        assertFalse(validator.updateValidate(dto));
    }

    private BookedDto createBookedDto(Long id, Long reservationNumber, Double price, SoldType soldType, BookedState bookedState, LocalDateTime buyingDate) {
        return BookedDto.builder().withId(id).withReservationNumber(reservationNumber).withPrice(price).withSoldType(soldType).withBookedState(bookedState).withBuyingDate(buyingDate).build();
    }
}

