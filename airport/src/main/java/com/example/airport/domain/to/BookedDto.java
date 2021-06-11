package com.example.airport.domain.to;

import com.example.airport.domain.enumeration.BookedState;
import com.example.airport.domain.enumeration.SoldType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class BookedDto extends AbstractTo {
    private Long id;
    private Long reservationNumber;
    private Double price;
    private SoldType soldType;
    private BookedState bookedState;
    private LocalDateTime buyingDate;

    public BookedDto() {
    }

    public BookedDto(Long id, Long reservationNumber, Double price, SoldType soldType, BookedState bookedState, LocalDateTime buyingDate,int version) {
        super.setVersion(version);
        this.id = id;
        this.reservationNumber = reservationNumber;
        this.price = price;
        this.soldType = soldType;
        this.bookedState = bookedState;
        if(Objects.nonNull(buyingDate)) {
            this.buyingDate = buyingDate.truncatedTo(ChronoUnit.SECONDS);
        }
    }

    public static BookedDtoBuilder builder(){
        return new BookedDtoBuilder();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(Long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SoldType getSoldType() {
        return soldType;
    }

    public void setSoldType(SoldType soldType) {
        this.soldType = soldType;
    }

    public BookedState getBookedState() {
        return bookedState;
    }

    public void setBookedState(BookedState bookedState) {
        this.bookedState = bookedState;
    }

    public LocalDateTime getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(LocalDateTime buyingDate) {
        if(Objects.nonNull(buyingDate)) {
            this.buyingDate = buyingDate.truncatedTo(ChronoUnit.SECONDS);
        }else {
            this.buyingDate = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookedDto bookedDto = (BookedDto) o;
        return Objects.equals(id, bookedDto.id) &&
                Objects.equals(reservationNumber, bookedDto.reservationNumber) &&
                Objects.equals(price, bookedDto.price) &&
                soldType == bookedDto.soldType &&
                bookedState == bookedDto.bookedState &&
                Objects.equals(buyingDate, bookedDto.buyingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reservationNumber, price, soldType, bookedState, buyingDate);
    }

    public static class BookedDtoBuilder{
        private Long id;
        private Long reservationNumber;
        private Double price;
        private SoldType soldType;
        private BookedState bookedState;
        private LocalDateTime buyingDate;
        private int version;

        public BookedDtoBuilder() {
            //default const...
        }
        public BookedDtoBuilder withId(Long id){
            this.id  = id;
            return this;
        }
        public BookedDtoBuilder withReservationNumber(Long reservationNumber){
            this.reservationNumber = reservationNumber;
            return this;
        }
        public BookedDtoBuilder withPrice(Double price){
            this.price = price;
            return this;
        }
        public BookedDtoBuilder withSoldType(SoldType soldType){
            this.soldType = soldType;
            return this;
        }
        public BookedDtoBuilder withBookedState(BookedState bookedState){
            this.bookedState =  bookedState;
            return this;
        }
        public BookedDtoBuilder withBuyingDate(LocalDateTime buyingDate){
            this.buyingDate = buyingDate;
            return this;
        }

        public BookedDtoBuilder withVersion(int version){
            this.version = version;
            return this;
        }
        public BookedDto build(){
            return new BookedDto(id,reservationNumber,price,soldType,bookedState,buyingDate,version);
        }
    }
}
