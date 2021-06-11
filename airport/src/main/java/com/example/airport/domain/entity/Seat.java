package com.example.airport.domain.entity;

import com.example.airport.domain.entity.query.QueryName;
import com.example.airport.domain.enumeration.BookedState;
import com.example.airport.domain.enumeration.CategoryType;
import com.example.airport.domain.enumeration.SeatPosition;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
@NamedQueries({
        @NamedQuery(name = QueryName.GET_SEAT_TO_REMOVE,
                query = "SELECT s FROM Seat AS s " +
                "WHERE s.isRemove = true AND s.updateTime <= :earlieDays")
})
@Entity
@Table(name = "seat")
public class Seat extends AbstractEntity  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_number", nullable = false)
    private Integer row;

    @Column(name = "column_number",nullable = false)
    private Integer column;

    @Column(name = "category_type",nullable = false,columnDefinition = "enum('BUSINESS','ECONOMIC','PREMIUM','OTHER')")
    @Enumerated(value = EnumType.STRING)
    private CategoryType categoryType;

    @Column(name = "position", nullable = false, columnDefinition = "enum('LEFT', 'MIDDLE', 'RIGHT')")
    @Enumerated(value = EnumType.STRING)
    private  SeatPosition position;

    @Column(name = "enable",nullable = false)
    private Boolean enable;

    @ManyToOne(fetch = FetchType.LAZY)
    private Plane plane;

    @OneToMany(mappedBy = "seat" ,fetch = FetchType.EAGER)
    private Set<Booked> booked;

    public Seat() {
    }

    public Seat(Long id, Integer row, Integer column, CategoryType categoryType, SeatPosition position, Boolean enable, Plane plane, Set<Booked> booked) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.categoryType = categoryType;
        this.position = position;
        this.enable = enable;
        this.plane = plane;
        this.booked = booked;
    }

    public void remove(){
        if(Objects.nonNull(booked) && !booked.isEmpty()){
            booked.stream().forEach(t->{
                    t.setBookedState(BookedState.TO_REMOVED);
                    t.setSeat(null);
            });
        }
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

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Set<Booked> getBooked() {
        return booked;
    }

    public void setBooked(Set<Booked> booked) {
        this.booked = booked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id) &&
                Objects.equals(row, seat.row) &&
                Objects.equals(column, seat.column) &&
                categoryType == seat.categoryType &&
                position == seat.position &&
                Objects.equals(enable, seat.enable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, column, categoryType, position, enable);
    }
}
