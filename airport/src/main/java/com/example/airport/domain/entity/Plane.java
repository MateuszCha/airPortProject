package com.example.airport.domain.entity;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plane")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number" , nullable = false)
    private String serialNumber;

    @Column(name = "name_carrier", nullable = false)
    private String nameCarrier;

    @OneToMany(mappedBy = "plane",orphanRemoval = true,cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private List<Seat> seats;

    @OneToMany(mappedBy = "plane",fetch = FetchType.EAGER)
    private List<FlightSchedule> flightSchedules;

//////CONSTRUCTOR
    public Plane() {
    }

    public Plane(Long id, String serialNumber, String nameCarrier, List<Seat> seats, List<FlightSchedule> flightSchedules) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.nameCarrier = nameCarrier;
        this.seats = seats;
        this.flightSchedules = flightSchedules;
    }

    //////OWN METHODS
    public void add(){
        //TODO

    }
    public void remove(){
        //TODO

    }
///GETTER AND SETTER
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNameCarrier() {
        return nameCarrier;
    }

    public void setNameCarrier(String nameCarrier) {
        this.nameCarrier = nameCarrier;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }


    public List<FlightSchedule> getFlightSchedules() {
        return flightSchedules;
    }

    public void setFlightSchedules(List<FlightSchedule> flightSchedules) {
        this.flightSchedules = flightSchedules;
    }

    //////OVERRIDE METHOD
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return Objects.equals(id, plane.id) &&
                Objects.equals(serialNumber, plane.serialNumber) &&
                Objects.equals(nameCarrier, plane.nameCarrier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, nameCarrier);
    }
}
