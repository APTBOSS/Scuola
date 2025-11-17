public class Stack<G> {
    private Node<G> top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }

    public void push(G info) {
        Node<G> nuovo = new Node<>(info);
        nuovo.next = top;
        top = nuovo;
        size++;
    }

    public G pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack vuoto");
        }

        G valore = top.dato;
        top = top.next;
        size--;
        return valore;
    }

    public G peek() {
        if (isEmpty())
            throw new RuntimeException("Stack vuoto");
        return top.dato;
    }

    public int size() {
        return size;
    }

    public void print() {
        if (isEmpty())
            throw new RuntimeException("Stack vuoto");
        Node<G> current = top;
        System.out.println("Cima --> ");
        while (current != null) {
            System.out.println(current.dato + " ");
            current = current.next;
        }
        System.out.println();
    }

    public boolean isEmpty() {
        return top == null;
    }
}
