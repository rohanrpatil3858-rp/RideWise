package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import java.util.List;

/**
 * Strategy interface for finding and matching drivers to riders
 * Implements Strategy Pattern and follows DIP (Dependency Inversion Principle)
 */
public interface RideMatchingStrategy {
    /**
     * Find a suitable driver for the given rider from available drivers
     * @param rider The rider requesting the ride
     * @param availableDrivers List of available drivers
     * @return The matched driver, or null if no suitable driver found
     */
    Driver findDriver(Rider rider, List<Driver> availableDrivers);
}

