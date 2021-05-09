package com.example.airport.persistance.to;

import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.enumeration.CategoryType;
import com.example.airport.domain.enumeration.SeatPosition;

import java.util.Objects;

public class SeatDto {
    private Long id;
    private Integer row;
    private Integer column;
    private CategoryType categoryType;
    private SeatPosition position;
    private Boolean enable;

    public SeatDto() {
    }

    public SeatDto(Long id, Integer row, Integer column, CategoryType categoryType, SeatPosition position, Boolean enable) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.categoryType = categoryType;
        this.position = position;
        this.enable = enable;
    }

    public static SeatDtoBuilder builder(){
        return new SeatDtoBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public SeatPosition getPosition() {
        return position;
    }

    public void setPosition(SeatPosition position) {
        this.position = position;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatDto seatDto = (SeatDto) o;
        return Objects.equals(id, seatDto.id) &&
                Objects.equals(row, seatDto.row) &&
                Objects.equals(column, seatDto.column) &&
                categoryType == seatDto.categoryType &&
                position == seatDto.position &&
                Objects.equals(enable, seatDto.enable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, column, categoryType, position, enable);
    }

    public static class SeatDtoBuilder{
        private Long id;
        private Integer row;
        private Integer column;
        private CategoryType categoryType;
        private SeatPosition position;
        private Boolean enable;

        public SeatDtoBuilder() {
        }
        public SeatDtoBuilder withId(Long id){
            this.id = id;
            return this;
        }
        public SeatDtoBuilder withRowNumber(Integer rowNumber){
            this.row = rowNumber;
            return this;
        }
        public SeatDtoBuilder withColumnNumber(Integer columnNumber){
            this.column = columnNumber;
            return this;
        }
        public SeatDtoBuilder withCategoryType(CategoryType categoryType){
            this.categoryType = categoryType;
            return this;
        }
        public  SeatDtoBuilder withSeatPosition(SeatPosition seatPosition ){
            this.position = seatPosition;
            return this;
        }
        public SeatDtoBuilder withEnableSeat(Boolean enableSeat){
            this.enable = enableSeat;
            return this;
        }
        public SeatDto build(){
            return new SeatDto(id,row,column,categoryType,position,enable);
        }
    }
}
