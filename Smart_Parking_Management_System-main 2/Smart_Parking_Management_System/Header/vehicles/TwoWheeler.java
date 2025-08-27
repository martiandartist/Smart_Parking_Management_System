package Header.vehicles;
public class TwoWheeler extends Vehicle {// This class represents a two-wheeler vehicle
    private String TwoWheelerType;

    public TwoWheeler(String licensePlate, String owner, String TwoWheelerType) {
        super(licensePlate, "Two Wheeler", owner);
        this.TwoWheelerType = TwoWheelerType;
    }

    public TwoWheeler(String licensePlate, String TwoWheelerType) {
        super(licensePlate, "Two Wheeler");
        this.TwoWheelerType = TwoWheelerType;
    }

    public String getTwoWheelerType() {
        return TwoWheelerType;
    }

}
