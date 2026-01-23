public class Lista {
    private Nodo head;

    public Lista() {
        this.head = null;
    }

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

    // --- NUOVO METODO: REVERSE ---
    /**
     * Inverte l'ordine dei nodi nella lista.
     * Modifica fisicamente i puntatori.
     */
    public void reverse() {
        Nodo prev = null;
        Nodo current = head;
        Nodo next = null;

        while (current != null) {
            next = current.getNext(); // 1. Salva il prossimo nodo
            current.setNext(prev);    // 2. Inverte il puntatore (punta indietro)
            prev = current;           // 3. Avanza 'prev'
            current = next;           // 4. Avanza 'current'
        }

        // Alla fine, 'prev' sarà la nuova testa della lista
        head = prev;
    }

    // --- Metodo utile per stampare la lista e vedere il risultato ---
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo current = head;
        while (current != null) {
            sb.append(current.getInfo()).append(" -> ");
            current = current.getNext();
        }
        sb.append("//");
        return sb.toString();
    }
    
    public int sum() {
        int somma = 0;
        Nodo current = head;
        while (current != null) {
            somma += current.getInfo();
            current = current.getNext();
        }
        return somma;
    }

    // -- METODO PER CALCOLARE LA MEDIA DEI NUMERI DELLA LISTA
    public int average() {
        if (head == null) return 0;
        int somma = 0;
        int count = 0;
        Nodo current = head;
        while (current != null) {
            somma += current.getInfo();
            count++;
            current = current.getNext();
        }
        return somma / count;
    }

    // -- METODO PER TROVARE IL MASSIMO NELLA LISTA
    public int max() {
        if (head == null) throw new RuntimeException("Lista vuota");
        int maxVal = head.getInfo();
        Nodo current = head.getNext();
        while (current != null) {
            if (current.getInfo() > maxVal) maxVal = current.getInfo();
            current = current.getNext();
        }
        return maxVal;
    }

    // --METODO PER TROVARE IL MINIMO NELLA LISTA
    public int min() {
        if (head == null) throw new RuntimeException("Lista vuota");
        int minVal = head.getInfo();
        Nodo current = head.getNext();
        while (current != null) {
            if (current.getInfo() < minVal) minVal = current.getInfo();
            current = current.getNext();
        }
        return minVal;
    }

    // -- RITORNA GLI ULTIMI DUE ELEMENTI DELLA LISTA
    public Integer[] lastAndSecondToLast() {
        if (head == null || head.getNext() == null) return null;
        Nodo current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        return new Integer[]{current.getInfo(), current.getNext().getInfo()};
    }
}