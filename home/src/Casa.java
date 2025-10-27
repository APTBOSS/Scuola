// Casa.java
public class Casa extends Abitazione {
    private boolean giardino;
    private boolean garage;
    private boolean terrazzo;
    private Stanza[] stanze;

    public Casa(String indirizzo, double superficie, int numStanze, String categoriaCatastale,
                boolean giardino, boolean garage, boolean terrazzo, Stanza[] stanze) {
        super(indirizzo, superficie, numStanze, categoriaCatastale);
        this.giardino = giardino;
        this.garage = garage;
        this.terrazzo = terrazzo;
        this.stanze = stanze;
    }

    public double calcolaIMU() {
        return superficie * 1.2;
    }

    @Override
    public String descrizione() {
        return "Casa in " + indirizzo + " con " + numStanze + " stanze.";
    }

    @Override
    public int calcolaMq() {
        double somma = 0;
        for (Stanza s : stanze) {
            somma += s.calcolaDim();
        }
        return (int) somma;
    }

    public double calcolaDim() {
        return calcolaMq();
    }
}
