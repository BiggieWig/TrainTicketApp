package com.ticketing.model;
import java.util.List;

public class Train {
    private String trainId;
    private int capacity;
    private List<TrainStop> schedule; // Ordered list of stops

    public Train(String trainId, int capacity, List<TrainStop> schedule) {
        this.trainId = trainId;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getTrainId() { return trainId; }
    public int getCapacity() { return capacity; }
    public List<TrainStop> getSchedule() { return schedule; }
}