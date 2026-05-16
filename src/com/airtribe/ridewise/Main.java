package com.airtribe.ridewise;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.*;
import com.airtribe.ridewise.service.*;
import com.airtribe.ridewise.strategy.*;
import java.util.*;

/**
 * Main Application - RideWise Ride-Sharing System
 * Console-based menu application for managing riders, drivers, and rides
 */
public class Main {
    
    // Service instances
    private static RiderService riderService;
    private static DriverService driverService;
    private static RideService rideService;
    
    private static Scanner scanner;
    
    public static void main(String[] args) {
        // Initialize services with default strategies
        initializeServices();
        
        scanner = new Scanner(System.in);
        
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     Welcome to RideWise Ride-Sharing     ║");
        System.out.println("║            Modular System MVP             ║");
        System.out.println("╚══════════════════════════════════════════╝\n");
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            String choice = scanner.nextLine().trim();
            
            try {
                switch (choice) {
                    case "1":
                        addRider();
                        break;
                    case "2":
                        addDriver();
                        break;
                    case "3":
                        viewAvailableDrivers();
                        break;
                    case "4":
                        requestRide();
                        break;
                    case "5":
                        completeRide();
                        break;
                    case "6":
                        viewAllRides();
                        break;
                    case "7":
                        changeFareStrategy();
                        break;
                    case "8":
                        changeMatchingStrategy();
                        break;
                    case "9":
                        viewRideDetails();
                        break;
                    case "0":
                        running = false;
                        System.out.println("\nThank you for using RideWise! Goodbye! 👋\n");
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage() + "\n");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Initialize all services with default strategies
     */
    private static void initializeServices() {
        riderService = new RiderService();
        driverService = new DriverService();
        
        // Default: Use NearestDriver strategy and DefaultFare strategy
        RideMatchingStrategy matchingStrategy = new NearestDriverStrategy();
        FareStrategy fareStrategy = new DefaultFareStrategy();
        
        rideService = new RideService(matchingStrategy, fareStrategy, riderService, driverService);
        
        // Load sample data for demonstration
        loadSampleData();
    }
    
    /**
     * Load sample data for testing
     */
    private static void loadSampleData() {
        // Register sample riders
        riderService.registerRider("Rajesh Kumar", "Downtown");
        riderService.registerRider("Priya Singh", "Airport Road");
        
        // Register sample drivers
        driverService.registerDriver("Amit Patel", "Market Square", VehicleType.CAR);
        driverService.registerDriver("Suresh Nair", "Bus Station", VehicleType.AUTO);
        driverService.registerDriver("Vikram Sharma", "Railway Station", VehicleType.BIKE);
        
        System.out.println("✓ Sample data loaded successfully!\n");
    }
    
    // ============ MENU DISPLAY ============
    
    private static void displayMainMenu() {
        System.out.println("════════════════════════════════════════");
        System.out.println("          MAIN MENU");
        System.out.println("════════════════════════════════════════");
        System.out.println("1. Add Rider");
        System.out.println("2. Add Driver");
        System.out.println("3. View Available Drivers");
        System.out.println("4. Request Ride");
        System.out.println("5. Complete Ride");
        System.out.println("6. View All Rides");
        System.out.println("7. Change Fare Strategy");
        System.out.println("8. Change Matching Strategy");
        System.out.println("9. View Ride Details & Fare");
        System.out.println("0. Exit");
        System.out.println("════════════════════════════════════════");
        System.out.print("Enter your choice: ");
    }
    
    // ============ RIDER OPERATIONS ============
    
    private static void addRider() {
        System.out.println("\n--- Add New Rider ---");
        System.out.print("Enter rider name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter location: ");
        String location = scanner.nextLine().trim();
        
        Rider rider = riderService.registerRider(name, location);
        System.out.println("✓ Rider registered successfully!");
        System.out.println("  Rider ID: " + rider.getId());
        System.out.println("  Name: " + rider.getName());
        System.out.println("  Location: " + rider.getLocation() + "\n");
    }
    
    // ============ DRIVER OPERATIONS ============
    
    private static void addDriver() {
        System.out.println("\n--- Add New Driver ---");
        System.out.print("Enter driver name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter current location: ");
        String location = scanner.nextLine().trim();
        
        System.out.println("Select vehicle type:");
        System.out.println("1. BIKE");
        System.out.println("2. AUTO");
        System.out.println("3. CAR");
        System.out.print("Enter choice (1-3): ");
        
        String vehicleChoice = scanner.nextLine().trim();
        VehicleType vehicleType;
        
        switch (vehicleChoice) {
            case "1":
                vehicleType = VehicleType.BIKE;
                break;
            case "2":
                vehicleType = VehicleType.AUTO;
                break;
            case "3":
                vehicleType = VehicleType.CAR;
                break;
            default:
                throw new IllegalArgumentException("Invalid vehicle type selection");
        }
        
        Driver driver = driverService.registerDriver(name, location, vehicleType);
        System.out.println("✓ Driver registered successfully!");
        System.out.println("  Driver ID: " + driver.getId());
        System.out.println("  Name: " + driver.getName());
        System.out.println("  Vehicle: " + driver.getVehicleType());
        System.out.println("  Status: Available\n");
    }
    
    private static void viewAvailableDrivers() {
        System.out.println("\n--- Available Drivers ---");
        List<Driver> availableDrivers = driverService.getAvailableDrivers();
        
        if (availableDrivers.isEmpty()) {
            System.out.println("No drivers available at the moment.\n");
            return;
        }
        
        for (int i = 0; i < availableDrivers.size(); i++) {
            Driver driver = availableDrivers.get(i);
            System.out.println((i + 1) + ". " + driver.getName() +
                    " | ID: " + driver.getId() +
                    " | Vehicle: " + driver.getVehicleType() +
                    " | Location: " + driver.getCurrentLocation() +
                    " | Rides: " + driver.getRidesCompleted());
        }
        System.out.println();
    }
    
    // ============ RIDE OPERATIONS ============
    
    private static void requestRide() throws NoDriverAvailableException {
        System.out.println("\n--- Request a Ride ---");
        
        System.out.print("Enter rider ID: ");
        String riderId = scanner.nextLine().trim();
        
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) {
            System.out.println("❌ Rider not found!\n");
            return;
        }
        
        System.out.print("Enter pickup location: ");
        String pickup = scanner.nextLine().trim();
        
        System.out.print("Enter drop location: ");
        String drop = scanner.nextLine().trim();
        
        System.out.print("Enter distance (in km): ");
        double distance;
        try {
            distance = Double.parseDouble(scanner.nextLine().trim());
            if (distance <= 0) {
                System.out.println("❌ Distance must be positive!\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid distance format!\n");
            return;
        }
        
        // Create ride
        Ride ride = rideService.requestRide(riderId, pickup, drop, distance);
        System.out.println("✓ Ride requested successfully!");
        System.out.println("  Ride ID: " + ride.getId());
        System.out.println("  Rider: " + ride.getRider().getName());
        System.out.println("  Distance: " + ride.getDistance() + " km");
        System.out.println("  Status: " + ride.getStatus());
        
        // Try to assign driver
        try {
            Ride assignedRide = rideService.assignDriver(ride.getId());
            System.out.println("\n✓ Driver assigned!");
            System.out.println("  Driver: " + assignedRide.getDriver().getName());
            System.out.println("  Vehicle: " + assignedRide.getDriver().getVehicleType());
            System.out.println("  Status: " + assignedRide.getStatus());
        } catch (Exception e) {
            System.out.println("⚠ Could not assign driver: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void completeRide() {
        System.out.println("\n--- Complete Ride ---");
        System.out.print("Enter ride ID: ");
        String rideId = scanner.nextLine().trim();
        
        Ride ride = rideService.getRideById(rideId);
        if (ride == null) {
            System.out.println("❌ Ride not found!\n");
            return;
        }
        
        if (ride.getStatus() != RideStatus.ASSIGNED) {
            System.out.println("❌ Only assigned rides can be completed!\n");
            return;
        }
        
        // Complete the ride
        Ride completedRide = rideService.completeRide(rideId);
        System.out.println("✓ Ride completed!");
        System.out.println("  Ride ID: " + completedRide.getId());
        System.out.println("  Status: " + completedRide.getStatus());
        
        // Calculate fare
        FareReceipt receipt = rideService.calculateFare(rideId);
        System.out.println("\n✓ Fare calculated:");
        System.out.println("  Total Amount: ₹" + String.format("%.2f", receipt.getAmount()));
        System.out.println("  Distance: " + ride.getDistance() + " km\n");
    }
    
    private static void viewAllRides() {
        System.out.println("\n--- All Rides ---");
        Collection<Ride> allRides = rideService.getAllRides();
        
        if (allRides.isEmpty()) {
            System.out.println("No rides found.\n");
            return;
        }
        
        for (Ride ride : allRides) {
            String driverInfo = ride.getDriver() != null ? ride.getDriver().getName() : "Not Assigned";
            System.out.println("ID: " + ride.getId() +
                    " | Rider: " + ride.getRider().getName() +
                    " | Driver: " + driverInfo +
                    " | Distance: " + ride.getDistance() + "km" +
                    " | Status: " + ride.getStatus());
        }
        System.out.println();
    }
    
    private static void viewRideDetails() {
        System.out.println("\n--- View Ride Details & Fare ---");
        System.out.print("Enter ride ID: ");
        String rideId = scanner.nextLine().trim();
        
        Ride ride = rideService.getRideById(rideId);
        if (ride == null) {
            System.out.println("❌ Ride not found!\n");
            return;
        }
        
        System.out.println("\n--- Ride Information ---");
        System.out.println("Ride ID: " + ride.getId());
        System.out.println("Rider: " + ride.getRider().getName());
        System.out.println("Driver: " + (ride.getDriver() != null ? ride.getDriver().getName() : "Not Assigned"));
        System.out.println("Pickup: " + ride.getPickupLocation());
        System.out.println("Drop: " + ride.getDropLocation());
        System.out.println("Distance: " + ride.getDistance() + " km");
        System.out.println("Status: " + ride.getStatus());
        
        FareReceipt receipt = rideService.getFareReceipt(rideId);
        if (receipt != null) {
            System.out.println("\n--- Fare Information ---");
            System.out.println(receipt);
        } else {
            System.out.println("\n⚠ Fare not calculated yet.");
        }
        System.out.println();
    }
    
    // ============ STRATEGY CHANGES ============
    
    private static void changeFareStrategy() {
        System.out.println("\n--- Change Fare Calculation Strategy ---");
        System.out.println("1. Default Fare (Base + Distance)");
        System.out.println("2. Peak Hour Fare (1.5x during 8-10 AM & 5-7 PM)");
        System.out.print("Enter choice (1-2): ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                rideService.setFareStrategy(new DefaultFareStrategy());
                System.out.println("✓ Switched to Default Fare Strategy\n");
                break;
            case "2":
                rideService.setFareStrategy(new PeakHourFareStrategy());
                System.out.println("✓ Switched to Peak Hour Fare Strategy\n");
                break;
            default:
                System.out.println("❌ Invalid choice\n");
        }
    }
    
    private static void changeMatchingStrategy() {
        System.out.println("\n--- Change Driver Matching Strategy ---");
        System.out.println("1. Nearest Driver Strategy");
        System.out.println("2. Least Active Driver Strategy");
        System.out.print("Enter choice (1-2): ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                rideService.setMatchingStrategy(new NearestDriverStrategy());
                System.out.println("✓ Switched to Nearest Driver Strategy\n");
                break;
            case "2":
                rideService.setMatchingStrategy(new LeastActiveDriverStrategy());
                System.out.println("✓ Switched to Least Active Driver Strategy\n");
                break;
            default:
                System.out.println("❌ Invalid choice\n");
        }
    }
}

