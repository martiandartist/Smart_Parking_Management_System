package Header.utils;
import Header.management.*;
import Header.vehicles.*;

public interface ReservationSystem {

    public static ParkingSpot bookSpot(Vehicle vehicle, User user, ParkingSpot... parkingSpots) {
        //This method is used to reserve a parking spot for a vehicle for a user (Only checks vehicle type).
        for (int i = 0; i < parkingSpots.length; i++) {
            if(parkingSpots[i]!=null){
                if (parkingSpots[i].getParkedVehicle() == vehicle) {
                    return null;
                }
            }
        }

        if (vehicle instanceof Bike) {
            for (int i = 0; i < parkingSpots.length; i++) { 
                if(parkingSpots[i]!=null){
                    if (parkingSpots[i].getType().equals("Two Wheeler") && parkingSpots[i].isAvailable()) {
                        parkingSpots[i].reserve(user);
                        return parkingSpots[i];
                    }

                }
            }
        }

        if (vehicle instanceof Car) {
            for (int i = 0; i < parkingSpots.length; i++) { 
                if(parkingSpots[i]!=null){
                    if (parkingSpots[i].getType().equals("Four Wheeler") && parkingSpots[i].isAvailable()) {
                        parkingSpots[i].reserve(user);
                        return parkingSpots[i];
                    }
                }
            }
        }
        return null;
    }

    public static ParkingSpot cancelReservation(User user, Vehicle vehicle, ParkingSpot... parkingSpots) {
        //This method is used to cancel the reservation of a parking spot for a vehicle for a user.
        for (int i = 0; i < parkingSpots.length; i++) {
            if (parkingSpots[i]!=null && parkingSpots[i].getReservedBy().equals(user) && parkingSpots[i].getType().equals(vehicle.getType())) {
                parkingSpots[i].setAvailable();
                return parkingSpots[i];
            }
        }
        return null;
    }

}