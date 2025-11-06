import java.util.List;

public class Car {
    private String brand;
    private String model;
    private String plate;
    private int nPlaces;
    private int nDoor;

    //composizione: la macchina possiede il motore
    private Motor motor;
    // aggreggazione: la macchina ha una lista di ruote
    private List <Wheel> wheels;

    public Car(String brand, String model, String plate, int nPlaces, int nDoor, Motor motor, List<Wheel> wheels) {
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.nPlaces = nPlaces;
        this.nDoor = nDoor;
        this.motor = motor;
        this.wheels = wheels;
    }

    public String toString() {
        return "Macchina " + brand + " " + model + " [" + plate + "]\n" +
                "Posti: " + nPlaces + ", Porte: " + nDoor + "\n" +
                motor + "\n" +
                "Ruote: " + (wheels != null ? wheels.size() : 0);
    }
}
