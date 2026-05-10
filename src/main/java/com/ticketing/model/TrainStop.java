package com.ticketing.model;
import java.time.LocalTime;

public class TrainStop {
    private Station station;
    private LocalTime arrivalTime;
    private LocalTime departureTime;

    public TrainStop(Station station, LocalTime arrivalTime, LocalTime departureTime) {
        this.station = station;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public Station getStation() { return station; }
    public LocalTime getArrivalTime() { return arrivalTime; }
    public LocalTime getDepartureTime() { return departureTime; }

    public void addDelay(int minutes) {
        if (arrivalTime != null) arrivalTime = arrivalTime.plusMinutes(minutes);
        if (departureTime != null) departureTime = departureTime.plusMinutes(minutes);
    }
}