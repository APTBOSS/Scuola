import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Motor motor = new Motor(150, 110.0, 4, "Fiat", "Fire");
        List<Wheel> wheels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            wheels.add(new Wheel("Pirelli", "Cinturato", "205/55 R16", "estivo"));
        }

        Car macchina = new Car("Fiat", "Punto", "EV252KN", 5, 3, motor, wheels);

        System.out.println(macchina);
    }
}
