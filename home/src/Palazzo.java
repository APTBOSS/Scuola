// Palazzo.java
public class Palazzo {
    private String indirizzo;
    private int nPiani;
    private Appartamento[] nAppartamenti;

    public Palazzo(String indirizzo, int nPiani, Appartamento[] nAppartamenti) {
        this.indirizzo = indirizzo;
        this.nPiani = nPiani;
        this.nAppartamenti = nAppartamenti;
    }

    public double calcolaIMU() {
        double totale = 0;
        for (Appartamento a : nAppartamenti) {
            totale += a.calcolaIMU();
        }
        return totale;
    }

    public String descrizione() {
        return "Palazzo in " + indirizzo + " con " + nPiani + " piani e " + nAppartamenti.length + " appartamenti.";
    }
}
