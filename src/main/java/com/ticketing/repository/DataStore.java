package com.ticketing.repository;
import com.ticketing.model.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private List<Train> trains = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public void addTrain(Train train) { trains.add(train); }
    public void removeTrain(Train train) { trains.remove(train); }
    public List<Train> getAllTrains() { return trains; }

    public void addBooking(Booking booking) { bookings.add(booking); }
    public List<Booking> getAllBookings() { return bookings; }
}