package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import java.util.List;

/**
 * Concrete implementation of RideMatchingStrategy
 * Finds the nearest driver based on location
 */
public class NearestDriverStrategy implements RideMatchingStrategy {
    
    @Override
    public Driver findDriver(Rider rider, List<Driver> availableDrivers) {
        if (availableDrivers == null || availableDrivers.isEmpty()) {
            return null;
        }

        // Simple distance calculation (mock implementation)
        Driver nearestDriver = null;
        double minDistance = Double.MAX_VALUE;

        for (Driver driver : availableDrivers) {
            double distance = calculateDistance(rider.getLocation(), driver.getCurrentLocation());
            if (distance < minDistance) {
                minDistance = distance;
                nearestDriver = driver;
            }
        }

        return nearestDriver;
    }

    /**
     * Calculate distance between two locations (mock implementation)
     * In real system, this would use GPS coordinates
     */
    private double calculateDistance(String location1, String location2) {
        // Mock implementation: generate random distance between 0.5 to 5 km
        return Math.random() * 4.5 + 0.5;
    }
}

