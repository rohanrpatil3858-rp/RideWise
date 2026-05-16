# RideWise - Requirements Document

## Project Overview
RideWise is a simplified, console-based Ride-Sharing System demonstrating high-quality Low-Level Design (LLD) and design principles.

## Functional Requirements

### A. Core Features

#### 1. Rider Management
- Register riders with name and location
- Retrieve rider information by ID
- View all registered riders

#### 2. Driver Management
- Register drivers with name, location, and vehicle type
- List available drivers
- Update driver availability status
- Track rides completed by each driver
- Update driver location

#### 3. Ride Management
- Request a ride (Rider initiates ride request)
- Automatic driver assignment using configurable strategies
- Track ride status through lifecycle (REQUESTED → ASSIGNED → COMPLETED → CANCELLED)
- Complete rides and calculate fares
- Cancel rides

#### 4. Fare Calculation
- Calculate fare using configurable pricing strategies
- Generate fare receipts with breakdown
- Support multiple fare strategies (default, surge pricing)

### B. Non-Functional Requirements

1. **Extensibility**: Easily add new driver matching strategies without modifying core logic
2. **Strategy Selection**: Change pricing algorithms without code changes
3. **Low Coupling**: Services depend on interfaces, not concrete implementations
4. **High Cohesion**: Related functionality grouped in cohesive classes
5. **Maintainability**: Clear separation of concerns and single responsibility

## Domain Model

### Entities

#### Rider
- id: String (unique identifier)
- name: String
- location: String
- Operations: register, retrieve

#### Driver
- id: String (unique identifier)
- name: String
- currentLocation: String
- available: boolean
- vehicleType: VehicleType (BIKE, AUTO, CAR)
- ridesCompleted: int
- Operations: register, retrieve, update availability, update location

#### Ride
- id: String (unique identifier)
- rider: Rider (association)
- driver: Driver (association, nullable initially)
- distance: double (in km)
- status: RideStatus (REQUESTED, ASSIGNED, COMPLETED, CANCELLED)
- pickupLocation: String
- dropLocation: String
- createdAt: long (timestamp)
- completedAt: long (timestamp, 0 if not completed)

#### FareReceipt
- rideId: String
- amount: double (total fare)
- baseFare: double
- distanceFare: double
- surgePricing: double
- generatedAt: LocalDateTime

### Enums

#### RideStatus
- REQUESTED: Rider just requested, awaiting assignment
- ASSIGNED: Driver matched and assigned
- COMPLETED: Ride successfully completed
- CANCELLED: Ride was cancelled

#### VehicleType
- BIKE: Two-wheeler (bicycle/motorcycle)
- AUTO: Auto-rickshaw
- CAR: Car

## Strategies

### RideMatchingStrategy Interface
Methods:
- `Driver findDriver(Rider rider, List<Driver> availableDrivers)`

Implementations:
1. **NearestDriverStrategy**: Finds the driver closest to the rider
2. **LeastActiveDriverStrategy**: Load balancing - assigns to driver with fewer completed rides

### FareStrategy Interface
Methods:
- `double calculateFare(Ride ride)`

Implementations:
1. **DefaultFareStrategy**: Base fare (₹50) + Distance fare (₹15/km)
2. **PeakHourFareStrategy**: Applies 1.5x surge multiplier during peak hours (8-10 AM, 5-7 PM)

## Services

### RiderService
Responsibilities:
- Register new riders
- Retrieve riders by ID
- Check if rider exists
- Get all riders

### DriverService
Responsibilities:
- Register new drivers
- Retrieve drivers by ID
- Get list of available drivers
- Update driver availability
- Update driver location
- Get all drivers

### RideService
Responsibilities:
- Request new ride
- Assign driver using RideMatchingStrategy
- Complete ride and update driver status
- Cancel ride
- Calculate fare using FareStrategy
- Retrieve rides by ID
- Get all rides
- Change strategies at runtime

## Design Principles & Patterns

### SOLID Principles

1. **Single Responsibility Principle (SRP)**
   - Each class has ONE reason to change
   - RiderService only manages riders
   - DriverService only manages drivers
   - RideService only orchestrates rides

2. **Open/Closed Principle (OCP)**
   - System is OPEN for extension (add new strategies)
   - System is CLOSED for modification (no changes to RideService when adding strategies)

3. **Liskov Substitution Principle (LSP)**
   - All RideMatchingStrategy implementations are interchangeable
   - All FareStrategy implementations are interchangeable

4. **Interface Segregation Principle (ISP)**
   - Small, focused interfaces (RideMatchingStrategy, FareStrategy)
   - Clients don't depend on methods they don't use

5. **Dependency Inversion Principle (DIP)**
   - High-level modules (RideService) depend on abstractions (interfaces)
   - NOT on low-level modules (concrete strategy implementations)

### Design Patterns

1. **Strategy Pattern**
   - Encapsulates algorithms in separate classes
   - Enables runtime algorithm switching
   - Used for driver matching and fare calculation

2. **Dependency Injection**
   - Strategies are injected into RideService constructor
   - Enables loose coupling
   - Makes code testable

3. **Service Layer Pattern**
   - Business logic separated from UI
   - Services act as facades
   - Easy to reuse across different interfaces (CLI, API, Web)

4. **Repository Pattern**
   - Services manage in-memory collections
   - Can be easily replaced with database access

### Other Principles

1. **DRY (Don't Repeat Yourself)**: Eliminate code duplication
2. **KISS (Keep It Simple)**: Simple, understandable design
3. **Law of Demeter**: Services communicate directly with collaborators
4. **Composition over Inheritance**: Use strategies instead of subclassing

## Relationships

| From | To | Type | Description |
|------|-----|------|-------------|
| Rider | Ride | Association | One rider can have multiple rides |
| Driver | Ride | Association | One driver can have multiple rides |
| Ride | FareReceipt | Composition | Each ride has exactly one fare receipt |
| RideService | RideMatchingStrategy | Composition | Strategy injected, owned by service |
| RideService | FareStrategy | Composition | Strategy injected, owned by service |

## Exception Handling

### NoDriverAvailableException
- Thrown when no drivers are available for assignment
- Extends Exception
- Provides meaningful error message

## User Interface

Console-based menu with following options:
1. Add Rider
2. Add Driver
3. View Available Drivers
4. Request Ride
5. Complete Ride
6. View All Rides
7. Change Fare Strategy
8. Change Matching Strategy
9. View Ride Details & Fare
0. Exit

## Success Criteria

- ✅ All SOLID principles correctly applied
- ✅ Design patterns properly implemented
- ✅ Low coupling, high cohesion
- ✅ Easy to add new strategies without modifying core
- ✅ Clean, readable, well-documented code
- ✅ Proper exception handling
- ✅ Interactive console application
- ✅ Sample data pre-loaded

## Future Enhancements

- Database persistence (replacing in-memory HashMap)
- Real GPS-based distance calculation
- Rating system for riders and drivers
- Payment processing
- REST API endpoints
- Web/Mobile interface
- Unit tests and integration tests

