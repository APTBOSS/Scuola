// Appartamento.java
public class Appartamento extends Abitazione {
    private int piano;
    private String condominio;
    private Stanza[] stanze;

    public Appartamento(String indirizzo, double superficie, int numStanze, String categoriaCatastale,
                        int piano, String condominio, Stanza[] stanze) {
        super(indirizzo, superficie, numStanze, categoriaCatastale);
        this.piano = piano;
        this.condominio = condominio;
        this.stanze = stanze;
    }

    public double calcolaIMU() {
        return superficie * 1.1;
    }

    @Override
    public String descrizione() {
        return "Appartamento al piano " + piano + " in " + condominio + " (" + indirizzo + ")";
    }

    @Override
    public int calcolaMq() {
        double totale = 0;
        for (Stanza s : stanze) {
            totale += s.calcolaDim();
        }
        return (int) totale;
    }
}
