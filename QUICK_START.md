# RideWise - Quick Start Guide

## 🚀 Getting Started in 5 Minutes

### Step 1: Verify All Files Are Created

Navigate to `D:\Airtribe\Ridewise\src` and you should see these **17 Java files**:

**Enums (2 files):**
- `RideStatus.java`
- `VehicleType.java`

**Domain Models (4 files):**
- `Rider.java`
- `Driver.java`
- `Ride.java`
- `FareReceipt.java`

**Strategy Interfaces (2 files):**
- `RideMatchingStrategy.java`
- `FareStrategy.java`

**Strategy Implementations (4 files):**
- `NearestDriverStrategy.java`
- `LeastActiveDriverStrategy.java`
- `DefaultFareStrategy.java`
- `PeakHourFareStrategy.java`

**Services (3 files):**
- `RiderService.java`
- `DriverService.java`
- `RideService.java`

**Main Application (1 file):**
- `Main.java`

**Documentation (3 files):**
- `README.md`
- `STUDY_GUIDE.md`
- `QUICK_START.md` (this file)

### Step 2: Compile the Code

Open terminal in `D:\Airtribe\Ridewise` and run:

```bash
javac -d bin src/*.java
```

**Expected:** No errors should appear.

### Step 3: Run the Application

```bash
java -cp bin Main
```

**Expected Output:**
```
✓ Sample data loaded successfully!

╔══════════════════════════════════════════╗
║     Welcome to RideWise Ride-Sharing     ║
║            Modular System MVP             ║
╚══════════════════════════════════════════╝

════════════════════════════════════════
          MAIN MENU
════════════════════════════════════════
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
════════════════════════════════════════
Enter your choice:
```

### Step 4: Test the Application

**Try this flow:**

```
1. Press 3 → View Available Drivers (see pre-loaded drivers)
2. Press 4 → Request Ride (use rider R1, distance 15 km)
3. Press 6 → View All Rides (see your new ride)
4. Press 5 → Complete Ride (complete the ride)
5. Press 9 → View Ride Details & Fare (see calculated fare)
6. Press 8 → Change Matching Strategy (try Least Active)
7. Press 7 → Change Fare Strategy (try Peak Hour)
8. Press 0 → Exit
```

---

## 📊 What Was Implemented

### Architecture Layers

```
┌──────────────────────────────────┐
│  Console UI (Main.java)          │  ← User interacts here
├──────────────────────────────────┤
│  Services Layer                  │  ← Business logic
│  (Ride, Rider, Driver Services)  │
├──────────────────────────────────┤
│  Strategy Layer                  │  ← Pluggable algorithms
│  (Matching & Fare Strategies)    │
├──────────────────────────────────┤
│  Domain Models                   │  ← Data entities
│  (Rider, Driver, Ride, Receipt)  │
└──────────────────────────────────┘
```

### Sample Data Pre-Loaded

**Riders:**
- R1: Rajesh Kumar (Downtown)
- R2: Priya Singh (Airport Road)

**Drivers:**
- D1: Amit Patel (Market Square, CAR, 0 rides)
- D2: Suresh Nair (Bus Station, AUTO, 0 rides)
- D3: Vikram Sharma (Railway Station, BIKE, 0 rides)

---

## 📚 Study Plan

### **Phase 1: Understand the Architecture (1 hour)**

1. **Read:** README.md (15 min)
   - Understand project overview
   - Review SOLID principles
   - See architecture diagram

2. **Read:** STUDY_GUIDE.md Foundational Concepts (20 min)
   - Learn OOP basics
   - Understand why each decision was made
   - See system architecture flow

3. **Draw:** Create a simple diagram (25 min)
   - Draw relationships between Rider, Driver, Ride
   - Add Service layer boxes
   - Add Strategy boxes

### **Phase 2: Deep Dive into Code (2 hours)**

1. **Read Domain Models (30 min):**
   - Understand Rider.java
   - Understand Driver.java
   - Understand Ride.java (lifecycle)
   - Understand FareReceipt.java

2. **Read Strategy Interfaces & Implementations (30 min):**
   - Understand RideMatchingStrategy interface
   - Trace through NearestDriverStrategy logic
   - Trace through LeastActiveDriverStrategy logic
   - Understand FareStrategy interface
   - Trace through DefaultFareStrategy logic
   - Trace through PeakHourFareStrategy logic

3. **Read Services (40 min):**
   - Understand RiderService (simple CRUD)
   - Understand DriverService (with availability)
   - **Focus on RideService:**
     - Constructor (dependency injection)
     - requestRide() method (creates new ride)
     - assignDriver() method (uses strategy)
     - calculateFare() method (uses strategy)
     - completeRide() method (updates state)

4. **Read Main.java (20 min):**
   - Understand initialization
   - Trace one complete menu option
   - See how it calls services

### **Phase 3: Hands-On Learning (2-3 hours)**

1. **Create a Test Scenario (30 min):**
   - Run the app
   - Add a rider manually
   - Request a ride
   - Complete the ride
   - View the fare
   - Note down exactly what happened at each step

2. **Experiment with Strategies (20 min):**
   - Request multiple rides
   - Switch matching strategy
   - See different drivers get assigned
   - Request rides and switch fare strategy
   - See fare changes based on time

3. **Trace Code Execution (30 min):**
   - While using the app, trace execution in your head:
   - What method is called?
   - What variables are updated?
   - What object is returned?

4. **Close IDE and Write Pseudocode (30 min):**
   - Without looking at code
   - Write pseudocode for each major flow:
     - Request Ride Flow
     - Assign Driver Flow
     - Complete Ride Flow
     - Calculate Fare Flow

### **Phase 4: Implementation Practice (3-4 hours)**

**Now implement from scratch without looking at the code:**

#### Sub-Phase 4.1: Enums & Domain Models (30 min)
```
TODO:
- Create RideStatus enum
- Create VehicleType enum
- Create Rider class
- Create Driver class
- Create Ride class
- Create FareReceipt class
```

#### Sub-Phase 4.2: Service Layer (60 min)
```
TODO:
- Create RiderService
- Create DriverService
- Create RideService with dependency injection
```

#### Sub-Phase 4.3: Strategies (45 min)
```
TODO:
- Create RideMatchingStrategy interface
- Create NearestDriverStrategy
- Create LeastActiveDriverStrategy
- Create FareStrategy interface
- Create DefaultFareStrategy
- Create PeakHourFareStrategy
```

#### Sub-Phase 4.4: Main Application (45 min)
```
TODO:
- Create Main class
- Initialize services
- Create menu system
- Implement menu options
- Call services appropriately
```

#### Sub-Phase 4.5: Test & Refine (30 min)
```
TODO:
- Compile and fix errors
- Test each feature
- Verify all flows work
- Add error handling
```

---

## 🔍 Code Tracing Exercise

### Exercise 1: Trace a Ride Request

**Scenario:** User selects "4. Request Ride" and enters:
- Rider ID: R1
- Pickup: Downtown
- Drop: Airport
- Distance: 15

**Trace Questions:**
1. Which method in Main.java handles this?
2. What service method is called first?
3. What is created and stored?
4. What status does the ride have?
5. Is the driver assigned immediately?

**Answer (Don't peek until you think about it):**
1. `requestRide()` in Main.java
2. `rideService.requestRide()`
3. A Ride object is created with status REQUESTED
4. Status = REQUESTED
5. Yes, `assignDriver()` is called immediately in Main

### Exercise 2: Trace Driver Assignment

**Scenario:** `rideService.assignDriver("RIDE1")` is called with NearestDriverStrategy

**Trace Questions:**
1. What available drivers list is retrieved?
2. Which strategy method is called?
3. How does NearestDriverStrategy find the driver?
4. What happens to the selected driver?
5. What happens to the ride?

**Answer:**
1. `driverService.getAvailableDrivers()` returns list of available drivers
2. `matchingStrategy.findDriver(rider, availableDrivers)`
3. Loops through all drivers, calculates distance, picks one with min distance
4. Driver becomes unavailable (setAvailable(false))
5. Ride gets the driver assigned, status becomes ASSIGNED

### Exercise 3: Trace Fare Calculation

**Scenario:** `rideService.calculateFare("RIDE1")` with DefaultFareStrategy

**Trace Questions:**
1. What ride is retrieved?
2. Which strategy method is called?
3. What is the calculation?
4. What is created and stored?
5. What is returned?

**Answer:**
1. Ride with distance 15 km
2. `fareStrategy.calculateFare(ride)`
3. 50 + (15 * 15) = 50 + 225 = 275
4. FareReceipt object with amount 275
5. The FareReceipt object

---

## 💡 Key Concepts to Understand

### Dependency Injection
```java
// Bad: Services create their own dependencies
public class RideService {
    private NearestDriverStrategy strategy = new NearestDriverStrategy();
}

// Good: Dependencies are passed in
public class RideService {
    private RideMatchingStrategy strategy;
    
    public RideService(RideMatchingStrategy strategy) {
        this.strategy = strategy;
    }
}
```

**Why?** Can change strategies at runtime without modifying RideService.

### Strategy Pattern
```java
// Instead of:
if (strategyType.equals("NEAREST")) { ... }
else if (strategyType.equals("LEAST_ACTIVE")) { ... }

// We use:
RideMatchingStrategy strategy = new NearestDriverStrategy();
Driver driver = strategy.findDriver(rider, drivers);
```

**Why?** Easy to add new strategies without modifying existing code.

### Null Safety
```java
// In Ride.java
private Driver driver;  // Initially null

// Before assigning, we check:
if (ride.getDriver() == null) {
    throw new Exception("Driver not assigned yet");
}
```

**Why?** Prevent null pointer exceptions.

### State Management
```java
// Ride has lifecycle:
REQUESTED → ASSIGNED → COMPLETED

// Each state transition updates the ride:
ride.setStatus(RideStatus.ASSIGNED);
```

**Why?** Track ride progress clearly.

---

## 🧪 Testing Checklist

After implementing yourself, test these scenarios:

- [ ] Register a rider
- [ ] Register multiple drivers
- [ ] View available drivers
- [ ] Request a ride (auto-assign with nearest)
- [ ] Complete a ride
- [ ] Calculate fare (default strategy)
- [ ] Switch to least active driver strategy
- [ ] Request another ride (least active assigns)
- [ ] Switch to peak hour fare strategy
- [ ] Complete ride (fare is 1.5x if peak hour)
- [ ] View ride details and fare
- [ ] Try to complete a non-existent ride (error handling)
- [ ] Try to complete a ride that's not ASSIGNED (error handling)

---

## 🎯 Success Criteria

You'll know you understand this project when you can:

1. ✅ Explain what dependency injection is
2. ✅ Explain why we use interfaces for strategies
3. ✅ Trace through a complete ride flow manually
4. ✅ Add a new matching strategy without modifying RideService
5. ✅ Explain all 5 SOLID principles with examples
6. ✅ Implement the entire system from scratch
7. ✅ Answer "Why?" for each design decision
8. ✅ Extend the system with new features

---

## 📞 Quick Reference

### File Purposes

| File | Purpose |
|------|---------|
| `RideStatus.java` | Defines ride states |
| `VehicleType.java` | Defines vehicle types |
| `Rider.java` | Data model for riders |
| `Driver.java` | Data model for drivers |
| `Ride.java` | Data model for rides |
| `FareReceipt.java` | Data model for fares |
| `RideMatchingStrategy.java` | Interface for matching algorithms |
| `NearestDriverStrategy.java` | Find nearest driver |
| `LeastActiveDriverStrategy.java` | Find least experienced driver |
| `FareStrategy.java` | Interface for fare algorithms |
| `DefaultFareStrategy.java` | Standard pricing |
| `PeakHourFareStrategy.java` | Surge pricing during peak hours |
| `RiderService.java` | Manages riders (CRUD) |
| `DriverService.java` | Manages drivers (CRUD + availability) |
| `RideService.java` | Orchestrates ride lifecycle |
| `Main.java` | Console UI and menu |

### Common Tasks

**Add a rider:**
```java
Rider rider = riderService.registerRider("John", "Downtown");
```

**Get available drivers:**
```java
List<Driver> drivers = driverService.getAvailableDrivers();
```

**Request a ride:**
```java
Ride ride = rideService.requestRide("R1", "Downtown", "Airport", 15);
```

**Assign driver:**
```java
rideService.assignDriver("RIDE1");
```

**Complete ride:**
```java
rideService.completeRide("RIDE1");
```

**Calculate fare:**
```java
FareReceipt receipt = rideService.calculateFare("RIDE1");
```

**Switch strategy:**
```java
rideService.setMatchingStrategy(new LeastActiveDriverStrategy());
```

---

## ⏱️ Time Estimate

- **Understanding (Read + Trace):** 3-4 hours
- **Hands-On (Experimenting with app):** 1 hour
- **Implementation (Code from scratch):** 3-4 hours
- **Total:** 7-9 hours

---

## 🚀 What's Next?

After mastering RideWise, consider:

1. **Add Ratings System**
   - Rating interface for riders & drivers
   - Update driver rating after each ride
   - Filter drivers by minimum rating

2. **Add Payments**
   - PaymentStrategy interface
   - Cash, Card, Wallet implementations
   - Process payment when ride completes

3. **Add Persistence**
   - Replace HashMap with Database
   - Use JDBC or JPA
   - No change to service interfaces!

4. **Add REST API**
   - Convert to Spring Boot
   - Expose services via HTTP endpoints
   - Frontend application

5. **Add Tests**
   - Unit tests with JUnit
   - Mock services with Mockito
   - Integration tests

---

## 📖 Documentation Files

- **README.md** - Complete project overview and SOLID principles
- **STUDY_GUIDE.md** - Detailed explanation of every component
- **QUICK_START.md** - This file, quick reference and study plan

---

## 💬 FAQ

**Q: Do I need to understand all the code before implementing?**
A: Read STUDY_GUIDE.md first, then look at each component. Don't memorize, understand WHY.

**Q: Can I use different variable names in my implementation?**
A: Yes! Use names that make sense to you. The important part is the structure.

**Q: What if my implementation doesn't compile?**
A: Check interfaces match. Check method signatures. Check imports.

**Q: How do I know if I'm doing it right?**
A: If the core flows work (request → assign → complete → fare), you're on the right track.

**Q: Should I add error handling?**
A: Yes, especially for null checks and invalid inputs. Keep it simple initially.

---

## 🎓 Final Advice

1. **Don't just read code** - Type it out
2. **Don't just implement** - Understand why
3. **Don't memorize** - Learn the patterns
4. **Don't copy-paste** - Write it yourself
5. **Don't give up** - It gets easier
6. **Do experiment** - Try new strategies
7. **Do test thoroughly** - Every feature
8. **Do ask questions** - To yourself first

---

**Ready to learn?** Start with Phase 1 of the study plan above! 🚀

Good luck! 📚

