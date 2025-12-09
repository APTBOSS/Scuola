public class Main {
    public static void main(String[] args) {

        // Coda di Integer
        Queue<Integer> q1 = new Queue<>();
        q1.enqueue(10);
        q1.enqueue(20);
        q1.enqueue(30);

        System.out.println("Coda di interi: " + q1);
        System.out.println("Primo elemento: " + q1.peek());
        System.out.println("Rimuovo: " + q1.dequeue());
        System.out.println("Dopo il dequeue: " + q1);

        // Coda di String
        Queue<String> q2 = new Queue<>();
        q2.enqueue("ciao");
        q2.enqueue("come");
        q2.enqueue("stai");

        System.out.println("\nCoda di stringhe: " + q2);

        // Coda di qualsiasi oggetto
        Queue<Double> q3 = new Queue<>();
        q3.enqueue(3.14);
        q3.enqueue(2.71);

        System.out.println("\nCoda di double: " + q3);
    }
}
