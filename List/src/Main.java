public class Main {
    public static void main(String[] args) {
        Lista miaLista = new Lista();

        miaLista.add(10);
        miaLista.add(5);
        miaLista.add(20);
        miaLista.add(25);

        System.out.println("Somma: " + miaLista.sum());      // Atteso: 60
        System.out.println("Media: " + miaLista.average());  // Atteso: 15
        System.out.println("Max: " + miaLista.max());        // Atteso: 25
        System.out.println("Min: " + miaLista.min());        // Atteso: 5
    }
}