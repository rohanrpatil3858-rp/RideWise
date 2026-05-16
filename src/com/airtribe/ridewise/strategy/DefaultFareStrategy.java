package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

/**
 * Concrete implementation of FareStrategy
 * Calculates fare based on standard rate: base fare + distance fare
 */
public class DefaultFareStrategy implements FareStrategy {
    
    private static final double BASE_FARE = 50.0;          // Base fare in rupees
    private static final double RATE_PER_KM = 15.0;         // Rate per kilometer
    
    @Override
    public double calculateFare(Ride ride) {
        if (ride == null || ride.getDistance() <= 0) {
            return BASE_FARE;
        }
        
        double distanceFare = ride.getDistance() * RATE_PER_KM;
        return BASE_FARE + distanceFare;
    }
}

