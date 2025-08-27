package Header.vehicles;

public class Bike extends TwoWheeler {

    public Bike(String licensePlate, String owner) {
        super(licensePlate, owner, "Bike");

    }

    // no owner
    public Bike(String licensePlate) {
        super(licensePlate, "Bike");

    }

}