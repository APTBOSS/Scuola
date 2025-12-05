import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Inserisci un'espressione algebrica");
        String espr = sc.nextLine();

        if(Bracket_Validator.balance_brackets(espr)) {
            System.out.println("✔ Le parentesi sono bilanciate.");
        } else {
            System.out.println("❌ Le parentesi NON sono bilanciate.");
        }

    }
}