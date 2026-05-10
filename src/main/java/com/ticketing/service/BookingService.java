package com.ticketing.service;
import com.ticketing.model.*;
import com.ticketing.repository.DataStore;

import java.util.List;
import java.util.UUID;

public class BookingService {
    private DataStore dataStore;
    private EmailService emailService;

    public BookingService(DataStore dataStore, EmailService emailService) {
        this.dataStore = dataStore;
        this.emailService = emailService;
    }

    public boolean bookTicket(String email, Train train, Station start, Station end, int tickets) {
        if (!hasCapacity(train, start, end, tickets)) {
            System.out.println("Booking failed: Not enough capacity on train " + train.getTrainId());
            return false;
        }

        Booking booking = new Booking(UUID.randomUUID().toString(), email, train, start, end, tickets);
        dataStore.addBooking(booking);
        emailService.sendBookingConfirmation(booking);
        return true;
    }

    private boolean hasCapacity(Train train, Station start, Station end, int requestedTickets) {
        int startIndex = -1, endIndex = -1;
        List<TrainStop> schedule = train.getSchedule();

        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getStation().equals(start)) startIndex = i;
            if (schedule.get(i).getStation().equals(end)) endIndex = i;
        }

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) return false;

        for (int i = startIndex; i < endIndex; i++) {
            int currentPassengers = countPassengersOnSegment(train, schedule.get(i).getStation(), schedule.get(i+1).getStation());
            if (currentPassengers + requestedTickets > train.getCapacity()) {
                return false;
            }
        }
        return true;
    }

    private int countPassengersOnSegment(Train train, Station segStart, Station segEnd) {
        int count = 0;
        for (Booking b : dataStore.getAllBookings()) {
            if (b.getTrain().equals(train)) {
                int bStart = getStationIndex(train, b.getStartStation());
                int bEnd = getStationIndex(train, b.getEndStation());
                int segStartIndex = getStationIndex(train, segStart);
                int segEndIndex = getStationIndex(train, segEnd);

                if (segStartIndex >= bStart && segEndIndex <= bEnd) {
                    count += b.getNumberOfTickets();
                }
            }
        }
        return count;
    }

    private int getStationIndex(Train train, Station station) {
        for (int i = 0; i < train.getSchedule().size(); i++) {
            if (train.getSchedule().get(i).getStation().equals(station)) return i;
        }
        return -1;
    }
}