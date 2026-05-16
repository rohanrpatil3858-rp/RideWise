package com.airtribe.ridewise.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Domain model representing a Fare Receipt
 * Encapsulates fare calculation and payment information
 */
public class FareReceipt {
    private String rideId;
    private double amount;
    private LocalDateTime generatedAt;
    private double baseFare;
    private double distanceFare;
    private double surgePricing;

    public FareReceipt(String rideId, double amount, LocalDateTime generatedAt) {
        this.rideId = rideId;
        this.amount = amount;
        this.generatedAt = generatedAt;
        this.baseFare = 0;
        this.distanceFare = 0;
        this.surgePricing = 0;
    }

    public FareReceipt(String rideId, double baseFare, double distanceFare, double surgePricing) {
        this.rideId = rideId;
        this.baseFare = baseFare;
        this.distanceFare = distanceFare;
        this.surgePricing = surgePricing;
        this.amount = baseFare + distanceFare + surgePricing;
        this.generatedAt = LocalDateTime.now();
    }

    // Getters
    public String getRideId() {
        return rideId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public double getDistanceFare() {
        return distanceFare;
    }

    public double getSurgePricing() {
        return surgePricing;
    }

    @Override
    public String toString() {
        return "FareReceipt{" +
                "rideId='" + rideId + '\'' +
                ", baseFare=" + String.format("%.2f", baseFare) +
                ", distanceFare=" + String.format("%.2f", distanceFare) +
                ", surgePricing=" + String.format("%.2f", surgePricing) +
                ", totalAmount=" + String.format("%.2f", amount) +
                ", generatedAt=" + generatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }
}

