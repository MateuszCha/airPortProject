package com.example.airport.domain.entity;

import com.example.airport.domain.enumeration.FlyType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "flight_schedule")
public class FlightSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "arrive_date_time",nullable = false)
    private LocalDateTime arrive;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name  = "destination", nullable = false, length = 86)
    private String destination;

    @Column(name  = "flyType", nullable = false,columnDefinition = "enum('INTERNATIONAL','LOCAL','PRIVET','OTHER')")
    @Enumerated(EnumType.STRING)
    private FlyType flyType;

    @OneToMany(mappedBy = "flightSchedule",fetch = FetchType.EAGER)
    private List<Plane> planes;

    public FlightSchedule() {
    }

    public FlightSchedule(Long id, String name, LocalDateTime startTime, LocalDateTime arrive, String description, String destination, FlyType flyType, List<Plane> planes) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.arrive = arrive;
        this.description = description;
        this.destination = destination;
        this.flyType = flyType;
        this.planes = planes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getArrive() {
        return arrive;
    }

    public void setArrive(LocalDateTime arrive) {
        this.arrive = arrive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public FlyType getFlyType() {
        return flyType;
    }

    public void setFlyType(FlyType flyType) {
        this.flyType = flyType;
    }

    public List<Plane> getPlanes() {
        return planes;
    }

    public void setPlanes(List<Plane> planes) {
        this.planes = planes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightSchedule that = (FlightSchedule) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(arrive, that.arrive) &&
                Objects.equals(description, that.description) &&
                Objects.equals(destination, that.destination) &&
                flyType == that.flyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime, arrive, description, destination, flyType);
    }
}
