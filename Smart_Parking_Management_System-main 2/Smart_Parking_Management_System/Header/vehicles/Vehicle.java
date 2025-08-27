package Header.vehicles;
public abstract class Vehicle {

    private String licensePlate;
    private String type; 
    private String owner;
    private boolean isParked = false; 

    public Vehicle(String licensePlate, String type, String owner) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.owner = owner;
    }

    public Vehicle(String licensePlate, String type) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.owner = null;

    }
    //getters and setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public String getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean getParked() {
        return isParked;
    }
    public void setParked(boolean isParked) {
        this.isParked = isParked;
    }

}