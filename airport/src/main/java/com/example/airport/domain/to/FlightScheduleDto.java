package com.example.airport.domain.to;

import com.example.airport.domain.enumeration.FlyType;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightScheduleDto {
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime arrive;
    private String description;
    private String destination;
    private FlyType flyType;

    public FlightScheduleDto() {
    }

    public FlightScheduleDto(Long id, String name, LocalDateTime startTime, LocalDateTime arrive, String description, String destination, FlyType flyType) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.arrive = arrive;
        this.description = description;
        this.destination = destination;
        this.flyType = flyType;
    }

    public static FlightScheduleDtoBuilder builder(){
        return new FlightScheduleDtoBuilder();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightScheduleDto that = (FlightScheduleDto) o;
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
    public static class FlightScheduleDtoBuilder {
        private Long id;
        private String name;
        private LocalDateTime startTime;
        private LocalDateTime arrive;
        private String description;
        private String destination;
        private FlyType flyType;

        public FlightScheduleDtoBuilder() {
            //default const...
        }

        public FlightScheduleDtoBuilder withId(Long id){
            this.id = id;
            return this;
        }
        public FlightScheduleDtoBuilder withName(String name){
            this.name = name;
            return this;
        }
        public FlightScheduleDtoBuilder withStartTime(LocalDateTime startTime){
            this.startTime = startTime;
            return this;
        }
        public FlightScheduleDtoBuilder withArriveTime(LocalDateTime arriveTime){
            this.arrive = arriveTime;
            return this;
        }
        public FlightScheduleDtoBuilder withDescription(String description){
            this.description = description;
            return this;
        }
        public FlightScheduleDtoBuilder withDestination(String destination) {
            this.destination = destination;
            return this;
        }
        public FlightScheduleDtoBuilder withFlyType(FlyType flyType){
            this.flyType = flyType;
            return this;
        }
        public FlightScheduleDto build(){
            return new FlightScheduleDto(id,name,startTime,arrive,description,destination,flyType);
        }
    }
}
