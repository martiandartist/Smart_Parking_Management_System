package Header.utils;
import Header.management.*;
public interface PaymentSystem {

    public static final int RESERVATION_COST = 10; // Cost per reservation
    public static final int CANCELLATION_COST = 5; // Cost per cancellation
    public static final int BIKE_RATE = 15; // Cost per hour for two wheeler
    public static final int CAR_RATE = 25; // Cost per hour for four wheeler

    public static int calculatePayment(ParkingSpot parkingSpot) {//calculates payment for the parking spot usage
        int currentTime = (int) (System.currentTimeMillis() / 10000); // Each unit is 10 seconds(assumed as 1 hour here)
        if (parkingSpot.getType().equals("Two Wheeler")) {
            return BIKE_RATE * (currentTime - parkingSpot.getEntryTime());
        } else if (parkingSpot.getType().equals("Four Wheeler")) {
            return CAR_RATE * (currentTime - parkingSpot.getEntryTime());
        } else {
            return 0;
        }
    }

}
