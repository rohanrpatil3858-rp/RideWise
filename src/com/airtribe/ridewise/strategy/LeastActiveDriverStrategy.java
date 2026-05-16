package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import java.util.List;

/**
 * Concrete implementation of RideMatchingStrategy
 * Finds the driver with least active rides (less busy)
 * Demonstrates load balancing and fairness
 */
public class LeastActiveDriverStrategy implements RideMatchingStrategy {
    
    @Override
    public Driver findDriver(Rider rider, List<Driver> availableDrivers) {
        if (availableDrivers == null || availableDrivers.isEmpty()) {
            return null;
        }

        // Select driver with least completed rides
        Driver leastActiveDriver = availableDrivers.get(0);
        int minRides = leastActiveDriver.getRidesCompleted();

        for (Driver driver : availableDrivers) {
            if (driver.getRidesCompleted() < minRides) {
                minRides = driver.getRidesCompleted();
                leastActiveDriver = driver;
            }
        }

        return leastActiveDriver;
    }
}

