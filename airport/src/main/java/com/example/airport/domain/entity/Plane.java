package com.example.airport.domain.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "plane")
public class Plane implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number" , nullable = false)
    private String serialNumber;

    @Column(name = "name_carrier", nullable = false)
    private String nameCarrier;

    @OneToMany(mappedBy = "plane",orphanRemoval = true,cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private Set<Seat> seats;

    @OneToMany(mappedBy = "plane",fetch = FetchType.LAZY)
    private Set<FlightSchedule> flightSchedules;

//////CONSTRUCTOR
    public Plane() {
    }

    public Plane(Long id, String serialNumber, String nameCarrier, Set<Seat> seats, Set<FlightSchedule> flightSchedules) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.nameCarrier = nameCarrier;
        this.seats = seats;
        this.flightSchedules = flightSchedules;
    }

    //////OWN METHODS
    public void addSeat(Seat seat){
        if(Objects.nonNull(seat)){
            this.seats.add(seat);
        }

    }
    public void addFlightSchedule(FlightSchedule flightSchedule){
        if(Objects.nonNull(flightSchedule)){
            this.flightSchedules.add(flightSchedule);
        }

    }
    public void remove(){
        if(Objects.nonNull(flightSchedules) && !flightSchedules.isEmpty()){
            this.flightSchedules.stream().forEach(t ->  t.setPlane(null));
        }

        if(Objects.nonNull(seats) && !seats.isEmpty()){
            this.seats.stream().forEach(t ->{
                t.remove();
                t.setPlane(null);
            });
        }
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

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }


    public Set<FlightSchedule> getFlightSchedules() {
        return flightSchedules;
    }

    public void setFlightSchedules(Set<FlightSchedule> flightSchedules) {
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
