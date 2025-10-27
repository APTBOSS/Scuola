// Main.java
public class Main {
    public static void main(String[] args) {

        // --- Creazione di alcune stanze per la Casa ---
        Stanza s1 = new Stanza(20.5);
        Stanza s2 = new Stanza(15.3);
        Stanza s3 = new Stanza(10.0);
        Stanza[] stanzeCasa = {s1, s2, s3};

        // --- Casa ---
        Casa casa = new Casa(
                "Via Roma 10",
                120.0,
                3,
                "A2",
                true,
                true,
                false,
                stanzeCasa
        );

        System.out.println("🏠 " + casa.descrizione());
        System.out.println("Superficie (da stanze): " + casa.calcolaMq() + " mq");
        System.out.println("IMU: " + casa.calcolaIMU() + " €\n");

        // --- Appartamento ---
        Stanza[] stanzeApp = {
                new Stanza(25.0),
                new Stanza(18.0),
                new Stanza(12.5),
                new Stanza(10.0)
        };

        Appartamento app = new Appartamento(
                "Corso Italia 25",
                90.0,
                4,
                "A3",
                3,
                "Condominio Sole",
                stanzeApp
        );

        System.out.println("🏢 " + app.descrizione());
        System.out.println("Superficie calcolata (da stanze): " + app.calcolaMq() + " mq");
        System.out.println("IMU: " + Math.round(app.calcolaIMU()) + " €\n");

        // --- Villa ---
        Villa villa = new Villa(
                "Via dei Pini 7",
                250.0,
                6,
                "A1",
                true,
                true
        );

        System.out.println("🏡 " + villa.descrizione());
        System.out.println("IMU: " + villa.calcolaIMU() + " €\n");

        // --- Bungalow ---
        Bungalow bungalow = new Bungalow(
                "Via del Mare 15",
                80.0,
                3,
                "A4",
                true
        );

        System.out.println("🏖️ " + bungalow.descrizione());
        System.out.println("IMU: " + bungalow.calcolaIMU() + " €\n");

        // --- Palazzo ---
        // Creiamo due set di stanze per i due appartamenti del palazzo
        Stanza[] stanzeApp1 = {
                new Stanza(22.0),
                new Stanza(18.5),
                new Stanza(12.0)
        };

        Stanza[] stanzeApp2 = {
                new Stanza(24.0),
                new Stanza(16.0),
                new Stanza(10.0),
                new Stanza(8.5)
        };

        Appartamento[] appartamenti = {
                new Appartamento("Via Verdi 2", 100, 4, "A3", 1, "Condominio Verdi", stanzeApp1),
                new Appartamento("Via Verdi 2", 110, 5, "A3", 2, "Condominio Verdi", stanzeApp2)
        };

        Palazzo palazzo = new Palazzo("Via Verdi 2", 3, appartamenti);

        System.out.println("🏢 " + palazzo.descrizione());
        System.out.println("IMU totale palazzo: " + Math.round(palazzo.calcolaIMU()) + " €\n");

        // --- Schiera ---
        Casa c1 = new Casa("Via del Bosco 3A", 100, 4, "A2", true, false, true, stanzeCasa);
        Casa c2 = new Casa("Via del Bosco 3B", 95, 3, "A2", false, false, true, stanzeCasa);
        Casa[] caseSchiera = {c1, c2};

        Schiera schiera = new Schiera("Via del Bosco", caseSchiera, true);
        System.out.println("🏘️ " + schiera.descrizione());
    }
}
