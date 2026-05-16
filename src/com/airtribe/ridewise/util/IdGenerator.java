package com.airtribe.ridewise.util;

/**
 * Utility class for generating unique IDs
 */
public class IdGenerator {
    private static int riderCounter = 1;
    private static int driverCounter = 1;
    private static int rideCounter = 1;

    /**
     * Generate a unique rider ID
     */
    public static String generateRiderId() {
        return "R" + (riderCounter++);
    }

    /**
     * Generate a unique driver ID
     */
    public static String generateDriverId() {
        return "D" + (driverCounter++);
    }

    /**
     * Generate a unique ride ID
     */
    public static String generateRideId() {
        return "RIDE" + (rideCounter++);
    }

    /**
     * Reset all counters (useful for testing)
     */
    public static void reset() {
        riderCounter = 1;
        driverCounter = 1;
        rideCounter = 1;
    }
}

