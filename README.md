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
