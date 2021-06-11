package com.example.airport.domain.entity.query;

public abstract class QueryName {
    public static final String GET_BOOKED_TO_REMOVE = "Booked.getAllToRemove";
    public static final String GET_CLIENT_TO_REMOVE = "Client.getAllToRemove";
    public static final String GET_SEAT_TO_REMOVE = "Seat.getAllToRemove";
    public static final String GET_PLANE_TO_REMOVE = "Plane.getAllToRemove";
    public static final String GET_SCHEDULE_TO_REMOVE = "FlightSchedule.getAllToRemove";
    private QueryName(){}

}
