package com.example.airport.domain.entity;

import com.example.airport.domain.entity.query.QueryName;
import com.example.airport.domain.enumeration.BookedState;
import com.example.airport.domain.enumeration.SoldType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
@NamedQueries({
        @NamedQuery(name = QueryName.GET_BOOKED_TO_REMOVE,
                query = "SELECT b FROM Booked AS b " +
                        "WHERE b.isRemove = true AND b.updateTime <= :earlieDays")
})
@Entity
@Table(name = "Booked")
public class Booked extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_number", nullable = false, unique = true)
    private Long reservationNumber;

    @Column(name = "price",nullable = false)
    private Double price;

    @Column(name = "sold_type", nullable = false,columnDefinition = "enum('SOLD_INTERNET','SOLD_AIRPORT')")
    @Enumerated(EnumType.STRING)
    private SoldType soldType;

    @Column(name = "status", nullable = false, columnDefinition = "enum('SOLD','DELETED','RETURNED','TO_REMOVED')")
    @Enumerated(EnumType.STRING)
    private BookedState bookedState;

    @Column(name = "buy_time")
    private LocalDateTime buyingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat;

    public Booked() {
    }

    public Booked(Long id, Long reservationNumber, Double price, SoldType soldType, BookedState bookedState, LocalDateTime buyingDate, Client client, Seat seat) {
        this.id = id;
        this.reservationNumber = reservationNumber;
        this.price = price;
        this.soldType = soldType;
        this.bookedState = bookedState;
        if(Objects.nonNull(buyingDate)) {
            this.buyingDate = buyingDate.truncatedTo(ChronoUnit.SECONDS);
        }
        this.client = client;
        this.seat = seat;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booked booked = (Booked) o;
        return Objects.equals(id, booked.id) &&
                Objects.equals(reservationNumber, booked.reservationNumber) &&
                Objects.equals(price, booked.price) &&
                soldType == booked.soldType &&
                bookedState == booked.bookedState &&
                Objects.equals(buyingDate, booked.buyingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reservationNumber, price, soldType, bookedState, buyingDate);
    }
}
