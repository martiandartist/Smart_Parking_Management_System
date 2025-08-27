package Header.vehicles;
public class Car extends FourWheeler {

    public Car(String licensePlate, String owner) {
        super(licensePlate, owner, "Car");

    }

    public Car(String licensePlate) {
        super(licensePlate, "Car");
    }

}