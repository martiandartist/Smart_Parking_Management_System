package Header.management;
import Header.vehicles.*;

public class ParkingSpot {
    private int spotId;
    private boolean isAvailable;
    private String type; // Two Wheeler, Four Wheeler
    private User reservedBy;
    private Vehicle parkedVehicle;
    private int entryTime;

    public ParkingSpot(int spotId, String type) {
        this.spotId = spotId;
        this.type = type;
        this.isAvailable = true;
        this.reservedBy = null;
        this.parkedVehicle = null;
    }

    //getters and setters
    public int getSpotId() {
        return spotId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable() {
        this.isAvailable = true;
        this.reservedBy = null;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;

    }

    public String getType() {
        return type;
    }

    public void reserve(User user) {
        this.isAvailable = false;
        this.reservedBy = user;
    }

    public User getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(User reservedBy) {
        this.reservedBy = reservedBy;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public void setParkedVehicle(Vehicle parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
    }

    public void setEntryTime(int entryTime) {
        this.entryTime = entryTime;
    }

    public int getEntryTime() {
        return entryTime;
    }

}