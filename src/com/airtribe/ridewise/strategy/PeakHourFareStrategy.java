package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;
import java.time.LocalDateTime;

/**
 * Concrete implementation of FareStrategy
 * Calculates fare with surge pricing during peak hours
 * Peak hours: 8-10 AM, 5-7 PM (1.5x multiplier)
 */
public class PeakHourFareStrategy implements FareStrategy {
    
    private static final double BASE_FARE = 50.0;          // Base fare in rupees
    private static final double RATE_PER_KM = 15.0;         // Rate per kilometer
    private static final double SURGE_MULTIPLIER = 1.5;     // 50% increase during peak hours
    
    @Override
    public double calculateFare(Ride ride) {
        if (ride == null || ride.getDistance() <= 0) {
            return BASE_FARE;
        }
        
        double distanceFare = ride.getDistance() * RATE_PER_KM;
        double totalFare = BASE_FARE + distanceFare;
        
        // Check if current time is peak hour
        if (isPeakHour()) {
            totalFare *= SURGE_MULTIPLIER;
        }
        
        return totalFare;
    }
    
    /**
     * Check if current time is peak hour
     * Peak hours: 8-10 AM and 5-7 PM
     */
    private boolean isPeakHour() {
        int hour = LocalDateTime.now().getHour();
        return (hour >= 8 && hour < 10) || (hour >= 17 && hour < 19);
    }
}

