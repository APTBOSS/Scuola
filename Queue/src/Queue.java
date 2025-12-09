public class Queue<T> {

    private Node<T> head;
    private Node<T> tail;

    public Queue() {
        head = null;
        tail = null;
    }

    // Inserimento in coda
    public void enqueue(T value) {
        Node<T> nuovo = new Node<>(value);

        if (tail == null) {   // Coda vuota
            head = nuovo;
            tail = nuovo;
        } else {
            tail.next = nuovo;
            tail = nuovo;
        }
    }

    // Rimozione dalla coda
    public T dequeue() {
        if (head == null) {
            System.out.println("Coda vuota!");
            return null;
        }

        T val = head.value;
        head = head.next;

        if (head == null)    // Se la coda è diventata vuota
            tail = null;

        return val;
    }

    // Guarda il primo elemento senza rimuoverlo
    public T peek() {
        if (head == null) {
            System.out.println("Coda vuota!");
            return null;
        }
        return head.value;
    }

    // Controlla se la coda è vuota
    public boolean isEmpty() {
        return head == null;
    }

    // Stampa la coda
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;

        sb.append("[");
        while (current != null) {
            sb.append(current.value);
            if (current.next != null)
                sb.append(", ");
            current = current.next;
        }
        sb.append("]");

        return sb.toString();
    }
}
