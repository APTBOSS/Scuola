import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class StackManager<G> {

    private final List<Stack<G>> stacks = new ArrayList<>();
    private final Class<G> type;

    public StackManager(Class<G> type, int numberOfStacks) {
        this.type = type;

        // Creazione dinamica delle pile
        for (int i = 0; i < numberOfStacks; i++) {
            stacks.add(new Stack<>());
        }
    }

    // Mostra "Stack<Tipo>"
    private String getStackType() {
        return "Stack<" + type.getSimpleName() + ">";
    }

    // Conversione da input ⇾ T
    @SuppressWarnings("unchecked")
    private G convert(String s) {
        if (type == Integer.class) return (G) Integer.valueOf(s);
        if (type == Double.class) return (G) Double.valueOf(s);
        if (type == Boolean.class) return (G) Boolean.valueOf(s);
        if (type == String.class) return (G) s;
        if (type == LocalDate.class) return (G) LocalDate.parse(s);

        throw new IllegalArgumentException("Tipo non supportato: " + type.getSimpleName());
    }

    public void start() {
        Scanner input = new Scanner(System.in);

        System.out.println("Hai creato " + stacks.size() + " pile di tipo " + getStackType());

        // Riempie ciascuna pila
        for (int i = 0; i < stacks.size(); i++) {
            System.out.println("\n--- Caricamento Pila " + (i + 1) + " ---");

            System.out.print("Quanti valori vuoi inserire nella pila " + (i + 1) + "? ");
            int quanti = Integer.parseInt(input.nextLine());

            for (int j = 0; j < quanti; j++) {
                System.out.print("Inserisci valore per " + getStackType() + ": ");
                G val = convert(input.nextLine());
                stacks.get(i).push(val);
            }
        }

        // Mostra i top
        System.out.println("\n=== TOP DI OGNI PILA ===");
        for (int i = 0; i < stacks.size(); i++) {
            System.out.println("Pila " + (i + 1) + " → TOP: " + stacks.get(i).peek());
        }
    }
}
