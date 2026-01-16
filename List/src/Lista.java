public class Lista {
    private Nodo head;

    public Lista() {
        this.head = null;
    }

    // Metodo helper per aggiungere elementi (utile per testare)
    public void add(int valore) {
        Nodo nuovo = new Nodo(valore);
        if (head == null) {
            head = nuovo;
        } else {
            Nodo current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(nuovo);
        }
    }

    // --- METODI RICHIESTI ---

    /**
     * Ritorna la somma dei valori.
     */
    public int sum() {
        int totale = 0;
        Nodo current = head;

        while (current != null) {
            totale += current.getInfo(); // Accesso tramite getter
            current = current.getNext(); // Scorrimento tramite getter
        }

        return totale;
    }

    /**
     * Ritorna la media SENZA usare size().
     * Calcola somma e conteggio scorrendo la lista una sola volta.
     */
    public int average() {
        if (head == null) return 0;

        int totale = 0;
        int count = 0;
        Nodo current = head;

        while (current != null) {
            totale += current.getInfo();
            count++;
            current = current.getNext();
        }

        return totale / count; // Divisione intera
    }

    /**
     * Ritorna il valore massimo.
     */
    public int max() {
        if (head == null) {
            throw new RuntimeException("Lista vuota: nessun massimo");
        }

        int massimo = head.getInfo();
        Nodo current = head.getNext();

        while (current != null) {
            if (current.getInfo() > massimo) {
                massimo = current.getInfo();
            }
            current = current.getNext();
        }

        return massimo;
    }

    /**
     * Ritorna il valore minimo.
     */
    public int min() {
        if (head == null) {
            throw new RuntimeException("Lista vuota: nessun minimo");
        }

        int minimo = head.getInfo();
        Nodo current = head.getNext();

        while (current != null) {
            if (current.getInfo() < minimo) {
                minimo = current.getInfo();
            }
            current = current.getNext();
        }

        return minimo;
    }
}