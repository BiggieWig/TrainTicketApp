package com.ticketing.service;

import com.ticketing.model.*;
import com.ticketing.repository.DataStore;

import java.time.LocalTime;
import java.util.*;

public class RouteService {
    private DataStore dataStore;

    public RouteService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public static class JourneyLeg {
        Train train; Station from; Station to; LocalTime dep; LocalTime arr;
        public JourneyLeg(Train train, Station from, Station to, LocalTime dep, LocalTime arr) {
            this.train = train; this.from = from; this.to = to; this.dep = dep; this.arr = arr;
        }
        @Override
        public String toString() {
            return "Take Train " + train.getTrainId() + " from " + from.getName() + " (" + dep + ") to " + to.getName() + " (" + arr + ")";
        }
    }

    public void searchRoutes(Station start, Station end, LocalTime afterTime) {
        System.out.println("\n--- Searching routes from " + start.getName() + " to " + end.getName() + " ---");
        List<List<JourneyLeg>> validRoutes = new ArrayList<>();
        findPaths(start, end, afterTime, new ArrayList<>(), new HashSet<>(), validRoutes);

        if (validRoutes.isEmpty()) {
            System.err.println("Error: No possible link between the stations " + start.getName() + " and " + end.getName() + ".");
        } else {
            for (int i = 0; i < validRoutes.size(); i++) {
                System.out.println("Route Option " + (i + 1) + ":");
                for (JourneyLeg leg : validRoutes.get(i)) {
                    System.out.println("  -> " + leg.toString());
                }
            }
        }
    }

    private void findPaths(Station current, Station destination, LocalTime currentTime,
                           List<JourneyLeg> currentPath, Set<Station> visited, List<List<JourneyLeg>> validRoutes) {
        if (current.equals(destination)) {
            validRoutes.add(new ArrayList<>(currentPath));
            return;
        }

        visited.add(current);

        for (Train train : dataStore.getAllTrains()) {
            List<TrainStop> schedule = train.getSchedule();
            int currentIndex = getStationIndex(schedule, current);

            if (currentIndex != -1 && schedule.get(currentIndex).getDepartureTime() != null &&
                    !schedule.get(currentIndex).getDepartureTime().isBefore(currentTime)) {
                LocalTime departureTime = schedule.get(currentIndex).getDepartureTime();


                for (int i = currentIndex + 1; i < schedule.size(); i++) {
                    TrainStop nextStop = schedule.get(i);
                    if (!visited.contains(nextStop.getStation())) {

                        currentPath.add(new JourneyLeg(train, current, nextStop.getStation(), departureTime, nextStop.getArrivalTime()));

                        findPaths(nextStop.getStation(), destination, nextStop.getArrivalTime(), currentPath, visited, validRoutes);

                        currentPath.remove(currentPath.size() - 1);
                    }
                }
            }
        }
        visited.remove(current);
    }

    private int getStationIndex(List<TrainStop> schedule, Station station) {
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getStation().equals(station)) return i;
        }
        return -1;
    }
}