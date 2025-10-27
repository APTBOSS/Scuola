// Villa.java
public class Villa extends Abitazione {
    private boolean piscina;
    private boolean dependance;

    public Villa(String indirizzo, double superficie, int numStanze, String categoriaCatastale,
                 boolean piscina, boolean dependance) {
        super(indirizzo, superficie, numStanze, categoriaCatastale);
        this.piscina = piscina;
        this.dependance = dependance;
    }

    public double calcolaIMU() {
        double coeff = 1.3;
        if (piscina) coeff += 0.2;
        if (dependance) coeff += 0.1;
        return superficie * coeff;
    }

    @Override
    public String descrizione() {
        return "Villa in " + indirizzo + (piscina ? " con piscina" : "") +
                (dependance ? " e dependance" : "");
    }
}
