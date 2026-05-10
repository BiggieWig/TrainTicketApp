package com.ticketing;

import com.ticketing.model.*;
import com.ticketing.repository.DataStore;
import com.ticketing.service.*;

import java.time.LocalTime;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        DataStore dataStore = new DataStore();
        EmailService emailService = new EmailService();
        BookingService bookingService = new BookingService(dataStore, emailService);
        AdminService adminService = new AdminService(dataStore, emailService);
        RouteService routeService = new RouteService(dataStore);


        Station newYork = new Station("New York");
        Station philly = new Station("Philadelphia");
        Station dc = new Station("Washington DC");
        Station miami = new Station("Miami");


        Train train1 = new Train("TRN-001", 50, Arrays.asList(
                new TrainStop(newYork, null, LocalTime.of(8, 0)),
                new TrainStop(philly, LocalTime.of(9, 30), LocalTime.of(9, 45)),
                new TrainStop(dc, LocalTime.of(11, 0), null)
        ));

        Train train2 = new Train("TRN-002", 100, Arrays.asList(
                new TrainStop(dc, null, LocalTime.of(12, 0)),
                new TrainStop(miami, LocalTime.of(18, 0), null)
        ));

        adminService.addTrain(train1);
        adminService.addTrain(train2);

        // --- REQUIREMENT B: Find Departure/Arrival times (Direct & Changeover) ---
        // Should find a changeover at DC
        routeService.searchRoutes(newYork, miami, LocalTime.of(7, 0));
        // Should throw the required Error message
        routeService.searchRoutes(miami, newYork, LocalTime.of(7, 0));

        // --- REQUIREMENT A: Book tickets & prevent overbooking ---
        System.out.println("\n--- Attempting Bookings ---");
        bookingService.bookTicket("john@example.com", train1, newYork, dc, 2);

        // Attempt to overbook TRN-001 (Capacity is 50, trying to book 50 more when 2 are already booked)
        bookingService.bookTicket("jane@example.com", train1, newYork, philly, 50);

        // --- REQUIREMENT C: Admin Operations ---
        adminService.showBookingsForTrain(train1);

        // Delay train 1, should email John
        adminService.reportDelay(train1, 30);
    }
}