package com.ticketing.service;
import com.ticketing.model.Booking;

public class EmailService {
    public void sendBookingConfirmation(Booking booking) {
        System.out.println("\n[EMAIL SENT to " + booking.getCustomerEmail() + "]");
        System.out.println("Subject: Booking Confirmation");
        System.out.println("Body: You booked " + booking.getNumberOfTickets() + " tickets on train " +
                booking.getTrain().getTrainId() + " from " + booking.getStartStation().getName() +
                " to " + booking.getEndStation().getName() + ".\n");
    }

    public void sendDelayNotification(String email, String trainId, int delayMinutes) {
        System.out.println("\n[EMAIL SENT to " + email + "]");
        System.out.println("Subject: Train Delay Alert");
        System.out.println("Body: Be advised, Train " + trainId + " is delayed by " + delayMinutes + " minutes.\n");
    }
}