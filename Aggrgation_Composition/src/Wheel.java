public class Wheel {
    private String brand;
    private String model;
    private String pneumatic_type;
    private String type;

    public Wheel(String brand, String model, String pneumatic_type, String type) {
        this.brand = brand;
        this.model = model;
        this.pneumatic_type = pneumatic_type;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Ruota " + brand + " " + model +
                " (" + pneumatic_type + ", " + type + ")";
    }
}
