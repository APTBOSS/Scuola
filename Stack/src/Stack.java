public class Stack<T> {
    private Node<T> top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }

    public void push(T info) {
        Node<T> nuovo = new Node<>(info);
        nuovo.next = top;
        top = nuovo;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack vuoto");
        }

        T valore = top.dato;
        top = top.next;
        size--;
        return valore;
    }

    public T peek() {
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
        Node<T> current = top;
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
