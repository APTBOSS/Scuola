public class Motor {
    private int horsePower;
    private double power; // in kW
    private int nCylinders;
    private String  brand;
    private String model;

    public Motor(int horsePower, double power, int nCylinders, String brand, String model) {
        this.horsePower = horsePower;
        this.power = power;
        this.nCylinders = nCylinders;
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toString() {
        return "Motore " + brand + " " + model +
                " (" + horsePower + " CV, " + power + " kW, " + nCylinders + " cilindri)";
    }
}
