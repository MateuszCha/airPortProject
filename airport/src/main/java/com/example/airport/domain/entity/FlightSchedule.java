package com.example.airport.domain.entity;

import com.example.airport.domain.enumeration.FlyType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "flight_schedule")
public class FlightSchedule  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "arrive_date_time",nullable = false)
    private LocalDateTime arriveTime;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name  = "destination", nullable = false, length = 86)
    private String destination;

    @Column(name  = "flyType", nullable = false,columnDefinition = "enum('INTERNATIONAL','LOCAL','PRIVET','OTHER')")
    @Enumerated(EnumType.STRING)
    private FlyType flyType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Plane plane;

    public FlightSchedule() {
    }

    public FlightSchedule(Long id, String name, LocalDateTime startTime, LocalDateTime arriveTime, String description, String destination, FlyType flyType, Plane plane) {
        this.id = id;
        this.name = name;
        if(Objects.nonNull(startTime)) {
            this.startTime = startTime.truncatedTo(ChronoUnit.SECONDS);
        }
        if(Objects.nonNull(arriveTime)) {
            this.arriveTime = arriveTime.truncatedTo(ChronoUnit.SECONDS);
        }
        this.description = description;
        this.destination = destination;
        this.flyType = flyType;
        this.plane = plane;
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
        if(Objects.nonNull(startTime)) {
            this.startTime = startTime.truncatedTo(ChronoUnit.SECONDS);
        }else{
            this.startTime = null;
        }
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        if(Objects.nonNull(arriveTime)) {
            this.arriveTime = arriveTime.truncatedTo(ChronoUnit.SECONDS);
        }else{
            this.arriveTime = null;
        }
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

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightSchedule that = (FlightSchedule) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(arriveTime, that.arriveTime) &&
                Objects.equals(description, that.description) &&
                Objects.equals(destination, that.destination) &&
                flyType == that.flyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime, arriveTime, description, destination, flyType);
    }
}
