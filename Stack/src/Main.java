import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StackManager<Integer> integer = new StackManager<>(Integer.class, 3);
        integer.start();
        StackManager<String> string = new StackManager<>(String.class, 10);
        string.start();
    }
}