package com.ticketing.service;
import com.ticketing.model.*;
import com.ticketing.repository.DataStore;
import java.util.HashSet;
import java.util.Set;

public class AdminService {
    private DataStore dataStore;
    private EmailService emailService;

    public AdminService(DataStore dataStore, EmailService emailService) {
        this.dataStore = dataStore;
        this.emailService = emailService;
    }

    public void addTrain(Train train) { dataStore.addTrain(train); }

    public void reportDelay(Train train, int minutes) {
        System.out.println("Admin: Reporting " + minutes + " min delay for Train " + train.getTrainId());

        for (TrainStop stop : train.getSchedule()) {
            stop.addDelay(minutes);
        }

        Set<String> notifiedEmails = new HashSet<>();
        for (Booking b : dataStore.getAllBookings()) {
            if (b.getTrain().equals(train) && !notifiedEmails.contains(b.getCustomerEmail())) {
                emailService.sendDelayNotification(b.getCustomerEmail(), train.getTrainId(), minutes);
                notifiedEmails.add(b.getCustomerEmail());
            }
        }
    }

    public void showBookingsForTrain(Train train) {
        System.out.println("\n--- Bookings for Train " + train.getTrainId() + " ---");
        boolean found = false;
        for (Booking b : dataStore.getAllBookings()) {
            if (b.getTrain().equals(train)) {
                System.out.println("User: " + b.getCustomerEmail() + " | Route: " +
                        b.getStartStation().getName() + " -> " + b.getEndStation().getName() +
                        " | Tickets: " + b.getNumberOfTickets());
                found = true;
            }
        }
        if (!found) System.out.println("No bookings found for this train.");
    }
}