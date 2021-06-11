package com.example.airport.domain.to;

import com.example.airport.domain.enumeration.FlyType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class FlightScheduleDto extends AbstractTo{
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime arriveTime;
    private String description;
    private String destination;
    private FlyType flyType;

    public FlightScheduleDto() {
    }

    private FlightScheduleDto(Long id, String name, LocalDateTime startTime, LocalDateTime arriveTime, String description, String destination, FlyType flyType,int version) {
        super.setVersion(version);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightScheduleDto that = (FlightScheduleDto) o;
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

    public static class FlightScheduleDtoBuilder {
        private Long id;
        private String name;
        private LocalDateTime startTime;
        private LocalDateTime arriveTime;
        private String description;
        private String destination;
        private FlyType flyType;
        private int version;

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
            this.arriveTime = arriveTime;
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

        public FlightScheduleDtoBuilder withVersion(int version){
            this.version = version;
            return this;
        }
        public FlightScheduleDto build(){
            return new FlightScheduleDto(id,name,startTime, arriveTime,description,destination,flyType,version);
        }
    }
}
