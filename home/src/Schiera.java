// Schiera.java
public class Schiera {
    private String indirizzo;
    private Casa[] numCase;
    private boolean giardino;

    public Schiera(String indirizzo, Casa[] numCase, boolean giardino) {
        this.indirizzo = indirizzo;
        this.numCase = numCase;
        this.giardino = giardino;
    }

    public String descrizione() {
        return "Schiera in " + indirizzo + " con " + numCase.length + " case.";
    }
}
