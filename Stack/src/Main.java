public class Main {
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();

        s.push(10);
        s.push(20);
        s.push(30);

        s.print();

        System.out.println("Peek: " + s.peek());
        System.out.println("Pop: " + s.pop());

        s.print();

        System.out.println("Size: " + s.size());
    }
}