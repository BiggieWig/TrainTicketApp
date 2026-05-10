# Java Train Ticketing Application

A robust, console-based Java application designed to manage train routes, prevent ticket overbooking, calculate direct and changeover routes, and simulate automated email notifications. 

This project was built to satisfy all mandatory requirements (Problem 1) using standard, pure Java.

## 🏗️ Architecture & Design Principles

The application relies on core Object-Oriented Programming (OOP) principles with zero external dependencies (no Spring Boot, no external databases). 

* **Domain Models:** `Station`, `Train`, `TrainStop`, `Booking` and `JourneyLeg` represent the core entities.
* **Service Layer (Single Responsibility):**
  * `BookingService`: Handles complex capacity algorithms. Capacity is checked *per segment* of a journey. If Train A travels X -> Y -> Z, a passenger alighting at Y frees up a seat for the Y -> Z segment.
  * `RouteService`: Utilizes a **Depth-First Search (DFS)** graph traversal algorithm to find both direct routes and connecting nodes (changeovers).
  * `AdminService`: Handles schedule modifications, querying, and triggers the `EmailService`.
* **Repository Layer:** `DataStore` acts as a centralized in-memory database to hold application state (Trains and Bookings).

## 🚀 How to Run

Ensure you have **Java 17+** installed. You can run this directly from your IDE (IntelliJ, Eclipse, VS Code) by executing `Main.java`, or via the terminal from the root directory:

```bash
# Compile the application
javac -d bin src/main/java/com/ticketing/**/*.java src/main/java/com/ticketing/*.java

# Run the application
java -cp bin com.ticketing.Main
```

---

## 📋 Features, Inputs & Output Examples

The `Main.java` class is pre-configured with a simulation that demonstrates all required functionalities.

### 1. Finding Routes (Direct & Changeover)
The application calculates routes using train schedules. It supports direct routes, changeovers, and properly rejects impossible routes.
* **Input:** User searches for a route from **New York** to **Miami**, and then from **Miami** to **New York**.
* **Output:**
```text
--- Searching routes from New York to Miami ---
Route Option 1:
  -> Take Train TRN-001 from New York (08:00) to Philadelphia (09:30)
  -> Take Train TRN-001 from Philadelphia (09:45) to Washington DC (11:00)
  -> Take Train TRN-002 from Washington DC (12:00) to Miami (18:00)
Route Option 2:
  -> Take Train TRN-001 from New York (08:00) to Washington DC (11:00)
  -> Take Train TRN-002 from Washington DC (12:00) to Miami (18:00)

--- Searching routes from Miami to New York ---
Error: No possible link between the stations Miami and New York.
```
*(Note: The algorithm successfully maps a changeover at Washington DC, transferring the user from TRN-001 to TRN-002).*

### 2. Booking Tickets & Preventing Overbooking
The application sends a simulated confirmation email upon success. Overbooking is dynamically calculated based on current active segments.
* **Input:** User 1 books 2 tickets on a 50-capacity train. User 2 then attempts to book 50 tickets on the exact same train.
* **Output:**
```text
--- Attempting Bookings ---

[EMAIL SENT to john@example.com]
Subject: Booking Confirmation
Body: You booked 2 tickets on train TRN-001 from New York to Washington DC.

Booking failed: Not enough capacity on train TRN-001
```
*(Notice the second booking was correctly rejected because the 50-seat train already had 2 seats claimed for the requested travel segment).*

### 3. Administrator Operations
Admins can view active bookings for specific trains and report delays. Applying a delay automatically updates the timetable and notifies affected users via simulated email.
* **Input:** Admin queries bookings for `TRN-001`, then applies a 30-minute delay to the train.
* **Output:**
```text
--- Bookings for Train TRN-001 ---
User: john@example.com | Route: New York -> Washington DC | Tickets: 2

Admin: Reporting 30 min delay for Train TRN-001

[EMAIL SENT to john@example.com]
Subject: Train Delay Alert
Body: Be advised, Train TRN-001 is delayed by 30 minutes.
```
