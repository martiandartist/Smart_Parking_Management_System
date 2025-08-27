package Header.main;

import Header.exceptions.*;
import Header.management.*;
import Header.utils.*;
import Header.vehicles.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
    private static ParkingManagement pm = new ParkingManagement();
    private static User currentUser = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static String msg = "";

    public static void main(String[] args) throws UserAlreadyExistsException, InvalidInputException {
        MenuFunction.initializeSampleData();
        MenuFunction.showMainMenu();
    }
    //This class contains the main menu and user menu functions.
    //Wanted to create an admin menu but couldn't get to it.
    public class MenuFunction {
        private static void initializeSampleData() { // adding existing spots to play around with
            pm.addParkingSpot(new ParkingSpot(1, "Two Wheeler"));
            pm.addParkingSpot(new ParkingSpot(2, "Four Wheeler"));
            pm.addParkingSpot(new ParkingSpot(3, "Two Wheeler"));
        }
    
        private static void showMainMenu() throws UserAlreadyExistsException, InvalidInputException {
            while(true) {
                clearScreen();
                System.out.println(msg);
                System.out.println("\n=== Parking Management System ==="); 
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                
                switch(scanner.nextInt()) {
                    case 1:
                        handleLogin();
                        break;
                    case 2:
                        handleRegistration();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        // System.out.println("Invalid option!");
                        msg = "Invalid Input";
                }
            }
        }
    
        private static void handleLogin() throws UserAlreadyExistsException, InvalidInputException {
            System.out.print("\nEnter email: ");
            String email = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();
    
            for(User user : pm.getUsers()) {
                if(user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    currentUser = user;
                    showUserMenu();
                    return;
                }
            }
            // System.out.println("Invalid credentials!");
            msg = "Invalid Credentials";
        }
    
        private static void handleRegistration() throws UserAlreadyExistsException {
            System.out.print("\nEnter name: ");
            String name = scanner.next();
            System.out.print("Enter email: ");
            String email = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();
    
            User newUser = new User(name, email, password);
            try {
                if(pm.addUser(newUser)) {
                    // System.out.println("Registration successful! You can now login.");
                    msg = "Registration successful! You can now login.";
                } else {
                    // System.out.println("Registration failed!");
                    msg = "Registration failed!";
                }
            } catch (UserAlreadyExistsException e) {
                // System.out.println(e.getMessage());
                msg = e.getMessage();
            }
        }
    
        private static void showUserMenu() throws InvalidInputException {
            while(currentUser != null) {
                clearScreen();
                System.out.println(msg);
                System.out.println("\n=== User Menu (" + currentUser.getName() + ") ===");
                System.out.println("1. Park Immediately");
                System.out.println("2. Reserve Spot");
                System.out.println("3. Cancel Reservation");
                System.out.println("4. Checkout Vehicle");
                System.out.println("5. Add Vehicle");
                System.out.println("6. Add Balance");
                System.out.println("7. View Balance");
                System.out.println("8. View Vehicles");
                System.out.println("9. Logout");
                System.out.print("Choose option: ");
    
                switch(scanner.nextInt()) { // all different functions handling for each option
                    case 1:
                        handleParkNow();
                        break;
                    case 2:
                        handleReserve();
                        break;
                    case 3:
                        handleCancelReservation();
                        break;
                    case 4:
                        handleCheckout();
                        break;
                    case 5:
                        handleAddVehicle();
                        break;
                    case 6:
                        handleAddBalance();
                        break;
                    case 7:
                        // System.out.println("Current balance: $" + currentUser.getBalance());
                        msg = "Current balance: $" + currentUser.getBalance();
                        break;
                    case 8:
                        listVehicles();
                        break;
                    case 9:
                        currentUser = null;
                        return;
                    default:
                        // System.out.println("Invalid option!");
                        msg = "Invalid Option";
                }
            }
        }
    
        private static void handleParkNow() {//parks the vehicle (reservered or not)
            if(currentUser.getVehicleCount() == 0) {
                // System.out.println("No vehicles registered!");
                msg = "No vehicles registered!";
                return;
            }
    
            listVehicles();
            System.out.print("Select vehicle: ");
            int index = scanner.nextInt() - 1;
            
            if(index < 0 || index >= currentUser.getVehicleCount()) {
                // System.out.println("Invalid selection!\n");
                msg = "Invalid Selection";
                return;
            }
    
            Vehicle vehicle = currentUser.getVehicles()[index];
            if(vehicle.getParked()) {
                // System.out.println("Vehicle already parked!\n");
                msg = "Vehicle already parked";
                return; 
            }
            if(pm.parkVehicle(vehicle, currentUser)) {
                // System.out.println("Vehicle parked successfully!\n");
                msg = "Vehicle parked successfully";
            } else {
                // System.out.println("Parking failed! No available spots.\n");
                msg = "Parking failed! No available spots";
            }
        }
    
        private static void handleReserve() {//reserves a parking spot for the vehicle
            if(currentUser.getVehicleCount() == 0) {
                // System.out.println("No vehicles registered!");
                msg = "No vehicles registered!";
                return;
            }
    
            listVehicles();
            System.out.print("Select vehicle: ");
            int index = scanner.nextInt() - 1;
            
            if(index < 0 || index >= currentUser.getVehicleCount()) {
                // System.out.println("Invalid selection!");
                msg = "Invalid selection";
                return;
            }
            Vehicle vehicle = currentUser.getVehicles()[index];
            if(vehicle.getParked()) {
                // System.out.println("Vehicle already parked!");
                msg = "Vehicle already parked";
                return;
            }
            if(pm.reserveParkingSpot(currentUser, vehicle)) {
                // System.out.println("Spot reserved! Reservation cost deducted: $" + PaymentSystem.RESERVATION_COST);
                msg = "Spot reserved! Reservation cost deducted: $" + PaymentSystem.RESERVATION_COST;
            } else {
                // System.out.println("Reservation failed! Check balance or availability.");
                msg = "Reservation failed! Check balance or availability";
            }
        }
    
        private static void handleCheckout() {//checks out the vehicle and calculates the payment
            if(currentUser.getVehicleCount() == 0) {
                // System.out.println("No vehicles registered!");
                msg = "No vehicles registered";
                return;
            }
    
            listVehicles();
            System.out.print("Select vehicle: ");
            int index = scanner.nextInt() - 1;
            
            if(index < 0 || index >= currentUser.getVehicleCount()) {
                // System.out.println("Invalid selection!");
                msg = "Invalid Selection";
                return;
            }
    
            Vehicle vehicle = currentUser.getVehicles()[index];
            if(pm.checkOutVehicle(vehicle, currentUser)) {
                // System.out.println("Checkout successful!");
                msg = "Checkout successful";
            } else {
                // System.out.println("Checkout failed! Vehicle not parked or insufficient balance.");
                msg = "Checkout failed! Vehicle not parked or insufficient balance";
            }
        }
    
        private static void handleCancelReservation() { //cancels the reservation of a vehicle
            if(currentUser.getVehicleCount() == 0) {
                // System.out.println("No vehicles registered!");
                msg = "No vehicle registered";
                return;
            }
    
            listVehicles();
            System.out.print("Select vehicle: ");
            int index = scanner.nextInt() - 1;
            
            if(index < 0 || index >= currentUser.getVehicleCount()) {
                // System.out.println("Invalid selection!");
                msg = "Invalid selection";
                return;
            }
    
            Vehicle vehicle = currentUser.getVehicles()[index];
            if(pm.cancelReservation(currentUser, vehicle)) {
                // System.out.println("Reservation cancelled! Cost: $" + PaymentSystem.CANCELLATION_COST);
                msg = "Reservation cancelled! Cost: $" + PaymentSystem.CANCELLATION_COST;
            } else {
                // System.out.println("Cancellation failed! No active reservation or insufficient balance.");
                msg = "Cancellation failed! No active reservation or insufficient balance.";
            }
        }
    
        private static void handleAddVehicle() {//adds a vehicle to the user account
            if(currentUser.getVehicleCount() >= User.MAX_VEHICLES) {
                System.out.println("Maximum vehicle limit reached!");
                msg = "Maximum vehicle limit reached";
                return;
            }
    
            System.out.print("Enter vehicle type (Bike/Car): ");
            String type = scanner.next();
            System.out.print("Enter license plate: ");
            String license = scanner.next();
    
            Vehicle vehicle;
            if(type.equalsIgnoreCase("Bike")) {
                vehicle = new Bike(license, currentUser.getName());
            } else if(type.equalsIgnoreCase("Car")) {
                vehicle = new Car(license, currentUser.getName());
            } else {
                // System.out.println("Invalid vehicle type!");
                msg = "Invalid vehicle type";
                return;
            }
    
            currentUser.addVehicle(vehicle);
            // System.out.println("Vehicle added successfully!");
            msg = "vehicle added successfully";
        }
    
        private static void listVehicles() { //lists the vehicles registered by the user
            System.out.println("\nRegistered Vehicles:");
            for (int i = 0; i < currentUser.getVehicleCount(); i++) {
                Vehicle v = currentUser.getVehicles()[i];
                System.out.printf("%d. %s - License Plate : %s%n",
                        i + 1, v.getType(), v.getLicensePlate());
            }
        }
        
        private static void handleAddBalance() throws InvalidInputException { //adds balance to the user account
            try {
                System.out.print("Enter amount to add: ");
                double amount = scanner.nextDouble();
                if (amount <= 0) {
                    throw new InvalidInputException("Amount must be positive.");
                }
                currentUser.setBalance(currentUser.getBalance() + amount);
                // System.out.println("Balance updated! Current balance: $" + currentUser.getBalance());
                msg = "Balance updated! Current balance: $" + currentUser.getBalance();
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                return;
            } catch (InputMismatchException e) {
                // System.out.println("Invalid input! Please enter a valid number.");
                msg = "Invalid input! Please enter a valid number.";
                return;
            }
        }

        public static void clearScreen() {
            try {
                String os = System.getProperty("os.name");
                if (os.contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
    }
}