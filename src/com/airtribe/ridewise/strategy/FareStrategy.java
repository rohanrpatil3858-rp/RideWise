package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

/**
 * Strategy interface for calculating fare for a ride
 * Implements Strategy Pattern and follows DIP (Dependency Inversion Principle)
 */
public interface FareStrategy {
    /**
     * Calculate the fare amount for a given ride
     * @param ride The ride for which to calculate fare
     * @return The calculated fare amount
     */
    double calculateFare(Ride ride);
}

