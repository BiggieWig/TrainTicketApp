package com.ticketing.model;

public class Booking {
    private String id;
    private String customerEmail;
    private Train train;
    private Station startStation;
    private Station endStation;
    private int numberOfTickets;

    public Booking(String id, String customerEmail, Train train, Station startStation, Station endStation, int numberOfTickets) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.train = train;
        this.startStation = startStation;
        this.endStation = endStation;
        this.numberOfTickets = numberOfTickets;
    }

    public Train getTrain() { return train; }
    public Station getStartStation() { return startStation; }
    public Station getEndStation() { return endStation; }
    public int getNumberOfTickets() { return numberOfTickets; }
    public String getCustomerEmail() { return customerEmail; }
}