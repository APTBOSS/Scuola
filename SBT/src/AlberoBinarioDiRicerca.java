public class AlberoBinarioDiRicerca {
    private NodoSBT radice;
    private int dimensione;

    public AlberoBinarioDiRicerca() {
        this.radice = null;
        this.dimensione = 0;
    }

    // ── INSERISCI ──────────────────────────────────────────────
    public void inserisci(int info) {
        radice = inserisciRicorsivo(radice, null, info);
        dimensione++;
    }

    private NodoSBT inserisciRicorsivo(NodoSBT nodo, NodoSBT padre, int info) {
        if (nodo == null) {
            NodoSBT nuovo = new NodoSBT(info);
            nuovo.setPadre(padre);
            return nuovo;
        }
        if (info < nodo.getInfo()) {
            nodo.setSinistro(inserisciRicorsivo(nodo.getSinistro(), nodo, info));
        } else if (info > nodo.getInfo()) {
            nodo.setDestro(inserisciRicorsivo(nodo.getDestro(), nodo, info));
        }
        // info == nodo.getInfo(): duplicato ignorato
        aggiornaAltezza(nodo);
        return nodo;
    }

    // ── CANCELLA ───────────────────────────────────────────────
    public void cancella(int info) {
        NodoSBT nodo = cerca(info);
        if (nodo == null) return;
        cancellaНodo(nodo);
        dimensione--;
    }

    private void cancellaНodo(NodoSBT nodo) {
        // Caso 1: nodo foglia
        if (nodo.getSinistro() == null && nodo.getDestro() == null) {
            sostituisci(nodo, null);
        }
        // Caso 2: solo figlio destro
        else if (nodo.getSinistro() == null) {
            sostituisci(nodo, nodo.getDestro());
        }
        // Caso 3: solo figlio sinistro
        else if (nodo.getDestro() == null) {
            sostituisci(nodo, nodo.getSinistro());
        }
        // Caso 4: due figli → sostituisci con successore
        else {
            NodoSBT succ = minimoNodo(nodo.getDestro());
            nodo.setInfo(succ.getInfo());
            cancellaНodo(succ);
        }
    }

    private void sostituisci(NodoSBT nodo, NodoSBT sostituto) {
        if (nodo.getPadre() == null) {
            radice = sostituto;
        } else if (nodo == nodo.getPadre().getSinistro()) {
            nodo.getPadre().setSinistro(sostituto);
        } else {
            nodo.getPadre().setDestro(sostituto);
        }
        if (sostituto != null) {
            sostituto.setPadre(nodo.getPadre());
        }
    }

    // ── CERCA ──────────────────────────────────────────────────
    public NodoSBT cerca(int info) {
        return cercaRicorsivo(radice, info);
    }

    private NodoSBT cercaRicorsivo(NodoSBT nodo, int info) {
        if (nodo == null) return null;
        if (info == nodo.getInfo()) return nodo;
        if (info < nodo.getInfo()) return cercaRicorsivo(nodo.getSinistro(), info);
        return cercaRicorsivo(nodo.getDestro(), info);
    }

    // ── MINIMO / MASSIMO ───────────────────────────────────────
    public NodoSBT minimo() {
        if (radice == null) return null;
        return minimoNodo(radice);
    }

    private NodoSBT minimoNodo(NodoSBT nodo) {
        while (nodo.getSinistro() != null) {
            nodo = nodo.getSinistro();
        }
        return nodo;
    }

    public NodoSBT massimo() {
        if (radice == null) return null;
        return massimoNodo(radice);
    }

    private NodoSBT massimoNodo(NodoSBT nodo) {
        while (nodo.getDestro() != null) {
            nodo = nodo.getDestro();
        }
        return nodo;
    }

    // ── PREDECESSORE ───────────────────────────────────────────
    public NodoSBT predecessore(NodoSBT nodo) {
        if (nodo == null) return null;
        if (nodo.getSinistro() != null) {
            return massimoNodo(nodo.getSinistro());
        }
        NodoSBT p = nodo.getPadre();
        while (p != null && nodo == p.getSinistro()) {
            nodo = p;
            p = p.getPadre();
        }
        return p;
    }

    // ── SUCCESSORE ─────────────────────────────────────────────
    public NodoSBT successore(NodoSBT nodo) {
        if (nodo == null) return null;
        if (nodo.getDestro() != null) {
            return minimoNodo(nodo.getDestro());
        }
        NodoSBT p = nodo.getPadre();
        while (p != null && nodo == p.getDestro()) {
            nodo = p;
            p = p.getPadre();
        }
        return p;
    }

    // ── VISITA IN ORDINE (inorder) ─────────────────────────────
    public void visita() {
        visitaInOrder(radice);
        System.out.println();
    }

    private void visitaInOrder(NodoSBT nodo) {
        if (nodo == null) return;
        visitaInOrder(nodo.getSinistro());
        System.out.print(nodo.getInfo() + " ");
        visitaInOrder(nodo.getDestro());
    }

    // ── ALTEZZA ────────────────────────────────────────────────
    public int altezza() {
        return altezzaNodo(radice);
    }

    private int altezzaNodo(NodoSBT nodo) {
        if (nodo == null) return 0;
        return nodo.getAltezza();
    }

    private void aggiornaAltezza(NodoSBT nodo) {
        int altSx = (nodo.getSinistro() == null) ? 0 : nodo.getSinistro().getAltezza();
        int altDx = (nodo.getDestro()   == null) ? 0 : nodo.getDestro().getAltezza();
        nodo.setAltezza(1 + Math.max(altSx, altDx));
    }

    // ── DIMENSIONE ─────────────────────────────────────────────
    public int getDimensione() {
        return dimensione;
    }
}