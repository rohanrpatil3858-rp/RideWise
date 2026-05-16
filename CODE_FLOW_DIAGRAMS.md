# RideWise - Complete Code Flow Diagrams

Visual representations of how everything connects and flows.

---

## 🏗️ System Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                         USER (Console)                           │
│                    Types commands in Main                        │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
                  ┌──────────────────────┐
                  │    Main.java         │
                  │  (UI/Menu Handler)   │
                  └──────────┬───────────┘
                             │
          ┌──────────────────┼──────────────────┐
          │                  │                  │
          ▼                  ▼                  ▼
    ┌─────────────┐  ┌─────────────┐  ┌──────────────┐
    │RiderService │  │DriverService│  │ RideService  │
    │(CRUD)       │  │(CRUD)       │  │(Orchestrate) │
    └─────────────┘  └─────────────┘  └──────┬───────┘
          │                │                  │
          │                │          ┌───────┴────────┐
          │                │          │                │
          │                │          ▼                ▼
          │                │    ┌──────────────┐ ┌──────────┐
          │                │    │   Strategy   │ │Strategy  │
          │                │    │   (Matching) │ │ (Fare)   │
          │                │    └──────────────┘ └──────────┘
          │                │
          └────────────────┴─────────────────────────┐
                                                    │
                                                    ▼
                                      ┌──────────────────────┐
                                      │  Domain Models       │
                                      │  Rider, Driver, Ride │
                                      │  FareReceipt         │
                                      └──────────────────────┘
```

---

## 🔄 Request Ride Flow (Complete)

```
User: "4. Request Ride"
    │
    ▼
Main.requestRide()
    │
    ├─> Scanner: Get riderId, pickup, drop, distance
    │
    ▼
rideService.requestRide(riderId, pickup, drop, distance)
    │
    ├─> riderService.getRiderById(riderId)
    │   └─> Returns: Rider object (or null)
    │
    ├─> Validation: Is rider null? → Throw error
    │
    ├─> Create: new Ride(rideId, rider, pickup, drop, distance)
    │   ├─> ride.id = "RIDE1"
    │   ├─> ride.rider = Rider object
    │   ├─> ride.driver = null
    │   ├─> ride.distance = 15
    │   └─> ride.status = REQUESTED
    │
    ├─> Store: rides.put(rideId, ride)
    │
    ▼
Return: Ride object (REQUESTED status)
    │
    ▼
Main: "Ride requested successfully!"
    │
    ├─> rideService.assignDriver(rideId) [AUTO-ASSIGN]
    │
    │   ├─> Get ride from map
    │   │
    │   ├─> driverService.getAvailableDrivers()
    │   │   └─> Returns: List of drivers with available=true
    │   │
    │   ├─> matchingStrategy.findDriver(rider, availableDrivers)
    │   │   │
    │   │   ├─> [If NearestDriverStrategy]
    │   │   │   ├─> Loop: Calculate distance to each driver
    │   │   │   ├─> Track: Driver with minimum distance
    │   │   │   └─> Return: Nearest driver
    │   │   │
    │   │   └─> [If LeastActiveDriverStrategy]
    │   │       ├─> Loop: Get ridesCompleted from each driver
    │   │       ├─> Track: Driver with minimum rides
    │   │       └─> Return: Least active driver
    │   │
    │   ├─> Update ride:
    │   │   ├─> ride.setDriver(selectedDriver)
    │   │   └─> ride.setStatus(ASSIGNED)
    │   │
    │   ├─> Update driver:
    │   │   └─> driverService.updateAvailability(driverId, false)
    │   │
    │   └─> Return: Ride object (ASSIGNED status)
    │
    ▼
Main: "Driver assigned!"
    └─> Display driver details
```

---

## ✅ Complete Ride Flow (Complete)

```
User: "5. Complete Ride"
    │
    ▼
Main.completeRide()
    │
    ├─> Scanner: Get rideId
    │
    ▼
rideService.completeRide(rideId)
    │
    ├─> Get ride: rides.get(rideId)
    │
    ├─> Validation: Is ride null? → Throw error
    │
    ├─> Validation: Is driver assigned? → Throw error
    │
    ├─> Update ride status:
    │   └─> ride.setStatus(COMPLETED)
    │
    ├─> Update driver:
    │   ├─> driver = ride.getDriver()
    │   ├─> driver.incrementRidesCompleted()
    │   └─> driverService.updateAvailability(driverId, true)
    │
    ▼
Return: Ride object (COMPLETED status)
    │
    ▼
Main: "Ride completed!"
    │
    ├─> rideService.calculateFare(rideId)
    │   │
    │   ├─> Get ride: rides.get(rideId)
    │   │
    │   ├─> fareStrategy.calculateFare(ride)
    │   │   │
    │   │   ├─> [If DefaultFareStrategy]
    │   │   │   ├─> baseFare = 50.0
    │   │   │   ├─> distanceFare = distance * 15.0
    │   │   │   └─> total = baseFare + distanceFare
    │   │   │
    │   │   └─> [If PeakHourFareStrategy]
    │   │       ├─> baseFare = 50.0
    │   │       ├─> distanceFare = distance * 15.0
    │   │       ├─> subtotal = baseFare + distanceFare
    │   │       ├─> Check: Is hour between 8-10 or 17-19? (peak)
    │   │       │   ├─> YES: total = subtotal * 1.5
    │   │       │   └─> NO: total = subtotal
    │   │       └─> Return: total amount
    │   │
    │   ├─> Create: new FareReceipt(rideId, amount, now)
    │   │
    │   ├─> Store: fares.put(rideId, receipt)
    │   │
    │   └─> Return: FareReceipt object
    │
    ▼
Main: "Fare calculated!"
    └─> Display: "₹275"
```

---

## 🎯 Class Relationships

```
┌────────────────────────────────────────────────────────────────┐
│                      DOMAIN MODELS                             │
├────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────┐                ┌──────────┐                      │
│  │  Rider   │                │  Driver  │                      │
│  ├──────────┤                ├──────────┤                      │
│  │ id       │                │ id       │                      │
│  │ name     │                │ name     │                      │
│  │ location │                │ location │                      │
│  └──────────┘                │ available│                      │
│                              │ vehicle  │                      │
│        ▲                      │ rides    │                      │
│        │                      └────┬─────┘                      │
│        │                           │                            │
│        └──────────┬────────────────┘                            │
│                   │                                             │
│                   ▼                                             │
│            ┌────────────┐                                       │
│            │   Ride     │                                       │
│            ├────────────┤                                       │
│            │ id         │                                       │
│            │ rider ────────> Rider                             │
│            │ driver ────────> Driver                           │
│            │ distance   │                                       │
│            │ status     │                                       │
│            │ pickup     │                                       │
│            │ drop       │                                       │
│            │ timestamp  │                                       │
│            └────────────┘                                       │
│                   │                                             │
│                   ▼                                             │
│            ┌────────────────┐                                   │
│            │ FareReceipt    │                                   │
│            ├────────────────┤                                   │
│            │ rideId         │                                   │
│            │ baseFare       │                                   │
│            │ distanceFare   │                                   │
│            │ surgePricing   │                                   │
│            │ amount         │                                   │
│            │ generatedAt    │                                   │
│            └────────────────┘                                   │
│                                                                 │
└────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────┐
│                    STRATEGY INTERFACES                         │
├────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────────────┐   ┌──────────────────┐              │
│  │RideMatchingStrategy  │   │   FareStrategy   │              │
│  ├──────────────────────┤   ├──────────────────┤              │
│  │ findDriver()         │   │ calculateFare()  │              │
│  └──────┬───────────────┘   └──────┬───────────┘              │
│         │                          │                           │
│    ┌────┴─────────┐          ┌─────┴──────────┐              │
│    │              │          │                │              │
│    ▼              ▼          ▼                ▼              │
│  ┌──────────────┐┌──────────────┐ ┌──────────┐┌───────────┐ │
│  │NearestDriver││LeastActiveDr.│ │DefaultFar││PeakHourFar│ │
│  │Strategy     ││Strategy      │ │Strategy  ││Strategy   │ │
│  └──────────────┘└──────────────┘ └──────────┘└───────────┘ │
│                                                                 │
└────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────┐
│                     SERVICE LAYER                              │
├────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌───────────────┐  ┌───────────────┐  ┌─────────────────┐   │
│  │RiderService   │  │DriverService  │  │   RideService   │   │
│  ├───────────────┤  ├───────────────┤  ├─────────────────┤   │
│  │register()     │  │register()     │  │request()        │   │
│  │getById()      │  │getById()      │  │assign()         │   │
│  │getAll()       │  │getAvailable() │  │complete()       │   │
│  │exists()       │  │updateAvail()  │  │calculateFare()  │   │
│  │               │  │updateLoc()    │  │cancel()         │   │
│  │               │  │getAll()       │  │getRide()        │   │
│  │               │  │exists()       │  │getAll()         │   │
│  │               │  │               │  │getFareReceipt() │   │
│  │               │  │               │  │setStrategy()    │   │
│  └───────────────┘  └───────────────┘  └─────────────────┘   │
│                                                                 │
│  KEY: RideService depends on:                                  │
│       - RideMatchingStrategy (injected)                         │
│       - FareStrategy (injected)                                │
│       - RiderService (injected)                                │
│       - DriverService (injected)                               │
│                                                                 │
└────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────┐
│                       MAIN APPLICATION                         │
├────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Uses:                                                          │
│  - RiderService                                                │
│  - DriverService                                               │
│  - RideService                                                 │
│                                                                 │
│  Menu options call appropriate service methods                │
│                                                                 │
└────────────────────────────────────────────────────────────────┘
```

---

## 🔀 Strategy Switching Flow

```
SCENARIO 1: Using NearestDriverStrategy

rideService = new RideService(
    new NearestDriverStrategy(),  ← INJECTED
    new DefaultFareStrategy(),
    riderService,
    driverService
);

Request ride with 3 drivers available:
- Amit (2 km away)
- Suresh (18 km away)
- Vikram (5 km away)

assignDriver() calls:
matchingStrategy.findDriver(rider, [amit, suresh, vikram])

NearestDriverStrategy executes:
- Amit: 2 km ◄── MINIMUM
- Suresh: 18 km
- Vikram: 5 km

Result: Amit is assigned ✓


SCENARIO 2: Change to LeastActiveDriverStrategy

rideService.setMatchingStrategy(new LeastActiveDriverStrategy());

Same 3 drivers available:
- Amit (45 completed rides)
- Suresh (8 completed rides) ◄── MINIMUM
- Vikram (23 completed rides)

assignDriver() calls:
matchingStrategy.findDriver(rider, [amit, suresh, vikram])

LeastActiveDriverStrategy executes:
- Amit: 45 rides
- Suresh: 8 rides ◄── MINIMUM
- Vikram: 23 rides

Result: Suresh is assigned ✓

SAME CODE, DIFFERENT RESULT! (No changes to RideService)
```

---

## 💰 Fare Calculation Flow

```
SCENARIO 1: DefaultFareStrategy at 3:00 PM

ride.distance = 15 km

fareStrategy.calculateFare(ride) calls:
DefaultFareStrategy.calculateFare(ride)

Calculation:
baseFare = 50.0
distanceFare = 15 * 15.0 = 225.0
total = 50.0 + 225.0 = 275.0 ✓


SCENARIO 2: PeakHourFareStrategy at 8:30 AM

Same ride: distance = 15 km

fareStrategy.calculateFare(ride) calls:
PeakHourFareStrategy.calculateFare(ride)

Calculation:
baseFare = 50.0
distanceFare = 15 * 15.0 = 225.0
subtotal = 50.0 + 225.0 = 275.0

isPeakHour() check:
Current hour = 8 (8:30 AM)
Peak hours = 8-10 or 17-19?
YES! → Apply surge

total = 275.0 * 1.5 = 412.50 ✓

SAME RIDE, DIFFERENT TIME, DIFFERENT FARE!
```

---

## 🔄 Ride Status Lifecycle

```
┌─────────────┐
│  REQUESTED  │ ◄── Initial state
└──────┬──────┘     User just requested
       │
       │ rideService.assignDriver()
       │ - Driver matched
       │ - Driver marked unavailable
       │
       ▼
┌─────────────┐
│  ASSIGNED   │ ◄── Driver assigned
└──────┬──────┘     Ride in progress
       │
       ├─────────────────────┐
       │                     │
       │ completeRide()      │ cancelRide()
       │                     │
       ▼                     ▼
┌─────────────┐       ┌──────────────┐
│ COMPLETED   │       │  CANCELLED   │
└─────────────┘       └──────────────┘

After COMPLETED or CANCELLED:
- Driver is marked available
- If COMPLETED:
  * ridesCompleted is incremented
  * Fare is calculated
  * FareReceipt is created
```

---

## 📊 Data Flow Example: Complete Ride Journey

```
STEP 1: Create Ride
┌──────────────────────────────────────┐
│ User: 4. Request Ride                │
│ Input: R1, Downtown, Airport, 15 km  │
└──────────────────────────────────────┘
         │
         ▼
    ┌─────────────────────────────────────────┐
    │ Ride RIDE1 Created                      │
    │ Status: REQUESTED                       │
    │ Rider: R1 (Rajesh)                      │
    │ Driver: null                            │
    │ Distance: 15 km                         │
    └─────────────────────────────────────────┘

STEP 2: Assign Driver
┌──────────────────────────────────────────┐
│ System: Auto-assign driver               │
│ Strategy: NearestDriverStrategy          │
│ Available: [D1-Amit 2km, D2-Suresh 18km] │
└──────────────────────────────────────────┘
         │
         ▼
    ┌──────────────────────────────────────┐
    │ Ride RIDE1 Updated                   │
    │ Status: ASSIGNED                     │
    │ Driver: D1 (Amit)                    │
    │ Distance: 15 km                      │
    └──────────────────────────────────────┘
         │
         ├─→ Driver D1 marked unavailable
         │
         ▼

STEP 3: Complete Ride
┌──────────────────────────────────────┐
│ User: 5. Complete Ride                │
│ Input: RIDE1                          │
└──────────────────────────────────────┘
         │
         ▼
    ┌──────────────────────────────────────┐
    │ Ride RIDE1 Updated                   │
    │ Status: COMPLETED                    │
    │ Driver: D1 (Amit)                    │
    │ Distance: 15 km                      │
    └──────────────────────────────────────┘
         │
         ├─→ D1.ridesCompleted++ (now 1)
         ├─→ D1 marked available
         │
         ▼

STEP 4: Calculate Fare
┌────────────────────────────────────────┐
│ System: Calculate fare                  │
│ Strategy: DefaultFareStrategy           │
│ Ride Distance: 15 km                    │
└────────────────────────────────────────┘
         │
         ▼
    ┌─────────────────────────────────────┐
    │ FareReceipt Created                 │
    │ RideId: RIDE1                       │
    │ BaseFare: ₹50                       │
    │ DistanceFare: ₹225 (15 × 15)        │
    │ SurgePricing: ₹0                    │
    │ Total: ₹275                         │
    │ Time: 2026-05-13 15:30:45           │
    └─────────────────────────────────────┘

FINAL STATE:
┌────────────────────────────────────────┐
│ Ride: RIDE1                            │
│   Rider: Rajesh Kumar                  │
│   Driver: Amit Patel (Now available)   │
│   Status: COMPLETED                    │
│   Distance: 15 km                      │
│   Fare: ₹275                           │
└────────────────────────────────────────┘
```

---

## 🧪 Testing Flows

### Test Flow 1: Basic Ride with Default Fare
```
1. Register: Rider R3, Driver D4
2. Request: R3 asks for 10 km ride
3. Assign: D4 gets assigned (nearest)
4. Complete: Ride is completed
5. Verify: Fare = 50 + (10 × 15) = 200 ✓
```

### Test Flow 2: Strategy Switching
```
1. Register: Rider R5, Driver D5, Driver D6
2. Use: NearestDriverStrategy
3. Request: Ride from R5
4. Verify: Nearest driver assigned
5. Switch: Change to LeastActiveDriverStrategy
6. Request: Another ride from R5
7. Verify: Different driver might be assigned ✓
```

### Test Flow 3: Peak Hour Surge
```
1. Set system time: 8:30 AM (peak hours)
2. Request: Rider wants ride (15 km)
3. Use: PeakHourFareStrategy
4. Complete: Ride finished
5. Verify: Fare = (50 + 225) × 1.5 = 412.5 ✓
6. Change: Time to 3:00 PM (not peak)
7. Request: Same distance ride
8. Verify: Fare = 50 + 225 = 275 ✓
```

---

## 🎯 Key Takeaways from Diagrams

1. **Main calls Services** - UI never touches domain models directly
2. **Services use Strategies** - Algorithms are pluggable
3. **Strategies process Data** - Domain models stay simple
4. **Flow is unidirectional** - No circular dependencies
5. **Each layer has one job** - Single Responsibility Principle
6. **Easy to swap strategies** - Open/Closed Principle
7. **Dependency Injection** - Services passed to where needed
8. **Clear state transitions** - Ride status tracks progress

---

This visual guide should help you trace code execution and understand data flow throughout the system.

