// Abitazione.java
public abstract class Abitazione {
    protected String indirizzo;
    protected double superficie;
    protected int numStanze;
    protected String categoriaCatastale;

    public Abitazione(String indirizzo, double superficie, int numStanze, String categoriaCatastale) {
        this.indirizzo = indirizzo;
        this.superficie = superficie;
        this.numStanze = numStanze;
        this.categoriaCatastale = categoriaCatastale;
    }

    public abstract String descrizione();

    public int calcolaMq() {
        return (int) superficie;
    }
}
