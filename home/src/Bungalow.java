// Bungalow.java
public class Bungalow extends Abitazione {
    private boolean veranda;

    public Bungalow(String indirizzo, double superficie, int numStanze, String categoriaCatastale,
                    boolean veranda) {
        super(indirizzo, superficie, numStanze, categoriaCatastale);
        this.veranda = veranda;
    }

    public double calcolaIMU() {
        return superficie * (veranda ? 1.15 : 1.0);
    }

    @Override
    public String descrizione() {
        return "Bungalow " + (veranda ? "con veranda" : "senza veranda");
    }
}
