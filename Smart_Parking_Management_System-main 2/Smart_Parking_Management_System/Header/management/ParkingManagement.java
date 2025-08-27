package Header.management;
import Header.exceptions.*;
import Header.utils.*;
import Header.vehicles.*;



public class ParkingManagement implements PaymentSystem, ReservationSystem, FileLogger {

    private ParkingSpot[] parkingSpots; // array of parking spots
    private User[] users;   // array of users
    int usersCount = 0;
    int parkingSpotsCount = 0;

    final int MAX_PARKING_SPOTS = 10;
    final int MAX_USERS = 10;
    // 4 types of constructors
    public ParkingManagement() {
        parkingSpots = new ParkingSpot[MAX_PARKING_SPOTS];
        users = new User[MAX_USERS];
    }

    public ParkingManagement(ParkingSpot[] parkingSpots, User... users) {
        this.parkingSpots = new ParkingSpot[MAX_PARKING_SPOTS];
        for (int i = 0; i < parkingSpots.length; i++) {
            if (parkingSpots[i] != null) {
                parkingSpotsCount++;
            }
            this.parkingSpots[i] = parkingSpots[i];
        }
        this.users = new User[MAX_USERS];
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                usersCount++;
            }
            this.users[i] = users[i];
        }

    }

    public ParkingManagement(ParkingSpot... parkingSpots) {
        this.parkingSpots = new ParkingSpot[MAX_PARKING_SPOTS];
        for (int i = 0; i < parkingSpots.length; i++) {
            if (parkingSpots[i] != null) {
                parkingSpotsCount++;
            }
            this.parkingSpots[i] = parkingSpots[i];
        }
        users = new User[MAX_USERS];

    }
    
    public ParkingManagement(User... users) {
        this.users = new User[MAX_USERS];
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                usersCount++;
            }
            this.users[i] = users[i];
        }
        parkingSpots = new ParkingSpot[MAX_PARKING_SPOTS];
    }
    

    public boolean reserveParkingSpot(User user, Vehicle vehicle) { // reserves a parking spot for the user and vehicle
        if (vehicle == null || user == null) {
            return false;
        }
        if(vehicle.getParked()) {
            return false;
        }
        if (user.getBalance() > PaymentSystem.RESERVATION_COST) {
            ParkingSpot spot = ReservationSystem.bookSpot(vehicle, user, parkingSpots);
            if (spot!=null) {
                user.setBalance(user.getBalance() - PaymentSystem.RESERVATION_COST);
                FileLogger.logReserve(user.getUserId(), user.getName(), spot.getSpotId() + "");
                return true;
            }
        }
        return false;

    }

    public boolean cancelReservation(User user, Vehicle vehicle) { // cancels the reservation for the user and vehicle
        if (vehicle == null || user == null) {
            return false;
        }
        if(vehicle.getParked()) {
            return false;
        }
        if (user.getBalance() > PaymentSystem.CANCELLATION_COST) {
            ParkingSpot spot = ReservationSystem.cancelReservation(user, vehicle, parkingSpots);
            if (spot!=null) {
                user.setBalance(user.getBalance() - PaymentSystem.CANCELLATION_COST);
                FileLogger.logCancel(user.getUserId(), user.getName(),  spot.getSpotId() + "");
                return true;
            }
        }
        return false;
    }

    public ParkingSpot findSpot(Vehicle vehicle) { // finds a parking spot for the vehicle
        for (int i = 0; i < parkingSpots.length; i++) {
            if (parkingSpots[i]!=null && parkingSpots[i].isAvailable() && parkingSpots[i].getType().equals(vehicle.getType())) {
                return parkingSpots[i];
            }
        }
        return null;
    }

    public boolean parkVehicle(Vehicle vehicle, User user) { // parks the vehicle for the user (reserved or not)
        if (vehicle == null || user == null) {
            return false;
        }
        if(vehicle.getParked()) {
            return false;
        }
        if (parkReservedVehicle(vehicle, user)) {
            return true;
        }
        ParkingSpot spot = findSpot(vehicle);
        if (spot != null) {
            spot.setParkedVehicle(vehicle);
            spot.setAvailable(false);
            spot.setEntryTime((int) (System.currentTimeMillis() / 10000));
            vehicle.setParked(true);
            FileLogger.logPark(user.getUserId(), user.getName(), spot.getSpotId() + "");
            return true;
        }
        return false;
    }

    public boolean parkReservedVehicle(Vehicle vehicle, User user) {//parking method for reserved vehicles automatically called by parkVehicle method
        if (user == null || vehicle == null) {
            return false;
        }
        if (vehicle.getParked()) {
            return false;
        }
        for (int i = 0; i < parkingSpots.length; i++) {
            if (parkingSpots[i] != null && parkingSpots[i].getReservedBy()!=null && parkingSpots[i].getType().equals(vehicle.getType())
                && parkingSpots[i].getParkedVehicle() == null && parkingSpots[i].getReservedBy().equals(user)) {
                parkingSpots[i].setParkedVehicle(vehicle);
                parkingSpots[i].setAvailable(false);
                parkingSpots[i].setEntryTime((int) (System.currentTimeMillis() / 10000));
                vehicle.setParked(true);
                FileLogger.logPark(user.getUserId(), user.getName(), parkingSpots[i].getSpotId() + "");
                return true;
            }
        }
        return false;
    }

    public boolean checkOutVehicle(Vehicle vehicle, User user) {// checks out the vehicle for the user and vehicle
        if (vehicle == null || user == null) {
            return false;
        }
        for (int i = 0; i < parkingSpots.length; i++) {
            if (parkingSpots[i] != null) {
                if (!parkingSpots[i].isAvailable() && parkingSpots[i].getParkedVehicle().equals(vehicle)) {
                    double amount = (double) PaymentSystem.calculatePayment(parkingSpots[i]);
                    if (user.getBalance() > amount) {
                        user.setBalance(user.getBalance() - amount);
                    } else {
                        return false;
                    }
                    parkingSpots[i].setAvailable(true);
                    parkingSpots[i].setParkedVehicle(null);
                    parkingSpots[i].setReservedBy(null);
                    vehicle.setParked(false);
                    FileLogger.logCheckout(user.getUserId(), user.getName(), parkingSpots[i].getSpotId() + "");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addUser(User user) throws UserAlreadyExistsException {// adds a user to the system
        if (user == null) {
            return false;
        }
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].getEmail().equals(user.getEmail())) {
                throw new UserAlreadyExistsException("User with this email already exists.");
            }
        }
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                return true;
            }
        }
        return false;
    }

    public boolean removeUser(User user) { // removes a user from the system
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].equals(user)) {
                users[i] = null;
                return true;
            }
        }
        return false;
    }

    public boolean addParkingSpot(ParkingSpot spot) {// adds a parking spot to the system
        if (spot == null) {
            return false;
        }
        for (int i = 0; i < parkingSpots.length; i++) {
            if (parkingSpots[i] == null) {
                parkingSpots[i] = spot;
                return true;
            }
        }
        return false;
    }

    public boolean removeParkingSpot(ParkingSpot spot) {// removes a parking spot from the system
        for (int i = 0; i < parkingSpots.length; i++) {
            if (parkingSpots[i] != null && parkingSpots[i].equals(spot)) {
                parkingSpots[i] = null;
                return true;
            }
        }
        return false;
    }
    //getters
    public ParkingSpot[] getParkingSpots() {
        return parkingSpots;
    }
     
    public User[] getUsers() {
        return users;
    }

}