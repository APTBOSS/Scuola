public class NodoSBT {
    private int info;
    private NodoSBT sinistro;
    private NodoSBT destro;
    private NodoSBT padre;
    private int altezza;

    public NodoSBT(int info) {
        this.info = info;
        this.sinistro = null;
        this.destro = null;
        this.padre = null;
        this.altezza = 1;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public NodoSBT getSinistro() {
        return sinistro;
    }

    public void setSinistro(NodoSBT sinistro) {
        this.sinistro = sinistro;
    }

    public NodoSBT getDestro() {
        return destro;
    }

    public void setDestro(NodoSBT destro) {
        this.destro = destro;
    }

    public NodoSBT getPadre() {
        return padre;
    }

    public void setPadre(NodoSBT padre) {
        this.padre = padre;
    }

    public int getAltezza() {
        return altezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }
}