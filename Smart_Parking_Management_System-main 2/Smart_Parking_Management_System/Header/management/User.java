package Header.management;
import Header.vehicles.*;
public class User { // User class to manage user details and vehicles
    private static int userCounter = 1;

    private final  String userId;
    private final String name;
    private final String email;
    private final String password;
    private Vehicle[] vehicles;
    private int vehicleCount;
    private double balance;

    public static final int MAX_VEHICLES = 5;

    
    public User(String name, String email, String password) {
        this.userId = "U" + userCounter++;
        this.email = email;
        this.name = name;
        this.password = password;
        this.vehicles = new Vehicle[MAX_VEHICLES];
        this.vehicleCount = 0;
        this.balance = 0.0;
    }

    //to add a vehicle to the user
    public void addVehicle(Vehicle vehicle) {
        if (vehicleCount < MAX_VEHICLES) {
            vehicles[vehicleCount++] = vehicle;
            vehicle.setOwner(name);
        } else {
            System.out.println("Max vehicle limit reached per user (5).");
        }
    }
    // getters and setters
    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance.doubleValue();
    }
    public void setBalance(int balance) {
        this.balance = (double) balance;
    }

}