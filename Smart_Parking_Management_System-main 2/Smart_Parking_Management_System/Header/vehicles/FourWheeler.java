package Header.vehicles;
public class FourWheeler extends Vehicle { // This class represents a four-wheeler vehicle
    private String fourWheelerType;

    public FourWheeler(String licensePlate, String owner, String fourWheelerType) {
        super(licensePlate, "Four Wheeler", owner);
        this.fourWheelerType = fourWheelerType;
    }

    public FourWheeler(String licensePlate, String fourWheelerType) {
        super(licensePlate, "Four Wheeler");
        this.fourWheelerType = fourWheelerType;
    }

    public String getCarType() {
        return fourWheelerType;
    }

}