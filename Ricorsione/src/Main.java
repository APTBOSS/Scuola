import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        // 1. Potenza
        System.out.println("1. Potenza: 2^5 = " + pow(2, 5));

        // 2. Fattoriale
        System.out.println("2. Fattoriale: 5! = " + factorial(5));

        // 3. Somma da 0 a n-1
        System.out.println("3a. Somma da 0 a 4 = " + sumFromZero(5));

        // 3b. Somma da 1 a n
        System.out.println("3b. Somma da 1 a 5 = " + sumFromOne(5));

        // 4. Numero di cifre
        System.out.println("4. Cifre in 438 = " + digits(438));

        // 5. Presenza in array non ordinato
        int[] arr = {3, 7, 2, 9, 5};
        System.out.println("5. Contiene 9? " + contains(arr, 9));
        System.out.println("5. Contiene 4? " + contains(arr, 4));

        // 6. Ricerca dicotomica
        int[] ordinato = {1, 3, 5, 7, 9};
        System.out.println("6. Ricerca 5? " + binarySearch(ordinato, 5));
        System.out.println("6. Ricerca 6? " + binarySearch(ordinato, 6));

        // 7. Palindromo
        System.out.println("7. 'anna' è palindroma? " + isPalindrome("anna"));
        System.out.println("7. 'ciao' è palindroma? " + isPalindrome("ciao"));

        // 8. Somma array
        System.out.println("8. Somma array = " + sumArray(arr));

        // 9. Prodotto array
        System.out.println("9. Prodotto array = " + productArray(arr));

        // 10. MCD tra due numeri
        System.out.println("10. MCD tra 48 e 18 = " + mcd(48, 18));
    }
    //1. Calcolare la potenza di due valori interi positivi.
    public static long pow(int a, int b) {
        if (b < 0) throw new IllegalArgumentException("Esponente negativo non permesso");
        if (b == 0) return 1;
        return a * pow(a, b - 1);
    }
    //2. Calcolare il fattoriale di un intero positivo.
    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n deve essere non negativo");
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }
    /*
    3. Calcolare la somma dei primi n valori interi positivi.
    [ se n=5, ottenere 10 se conteggiamo anche lo zero cioè 0+1+2+3+4,
    altrimenti 15 se si parte il conteggio dall’uno cioè 1+2+3+4+5 ]
     */
    public static int sumFromZero(int n) {
        if (n < 0) throw new IllegalArgumentException("n deve essere non negativo");
        if (n == 0) return 0;
        return (n - 1) + sumFromZero(n - 1);
    }

    public static int sumFromOne(int n) {
        if (n <= 0) throw new IllegalArgumentException("n deve essere >= 1");
        if (n == 1) return 1;
        return n + sumFromOne(n - 1);
    }
    //4. Calcolare il numero di cifre di un valore intero. [3 se il valore analizzato è il 438]
    public static int digits(int x) {
        x = Math.abs(x);
        if (x < 10) return 1;
        return 1 + digits(x / 10);
    }
    //5. Controllare la presenza o meno di un valore in un array unidimensionale.
    public static boolean contains(int[] arr, int target) {
        return containsRec(arr, target, 0);
    }

    private static boolean containsRec(int[] arr, int target, int i) {
        if (i >= arr.length) return false;
        if (arr[i] == target) return true;
        return containsRec(arr, target, i + 1);
    }
    //6. Controllare la presenza o meno di un valore in un array unidimensionale ordinato, utilizzando la ricerca dicotomica.
    public static boolean binarySearch(int[] arr, int target) {
        return binSearchRec(arr, target, 0, arr.length - 1);
    }

    private static boolean binSearchRec(int[] arr, int target, int low, int high) {
        if (low > high) return false;
        int mid = low + (high - low) / 2;
        if (arr[mid] == target) return true;
        if (target < arr[mid]) return binSearchRec(arr, target, low, mid - 1);
        return binSearchRec(arr, target, mid + 1, high);
    }
    //7. Valutare se una stringa data è palindroma o meno.[ " anna" è palindroma  ]
    public static boolean isPalindrome(String s) {
        if (s == null) return false;
        String t = s.replaceAll("\\s+", "").toLowerCase();
        return isPalRec(t, 0, t.length() - 1);
    }

    private static boolean isPalRec(String s, int left, int right) {
        if (left >= right) return true;
        if (s.charAt(left) != s.charAt(right)) return false;
        return isPalRec(s, left + 1, right - 1);
    }
    //8. dato un array unidimensionale lungo 5, di interi, calcolare la somma dei valori caricati nel vettore.
    public static int sumArray(int[] arr) {
        return sumArrayRec(arr, 0);
    }

    private static int sumArrayRec(int[] arr, int i) {
        if (i >= arr.length) return 0;
        return arr[i] + sumArrayRec(arr, i + 1);
    }
    //9. Calcolare il prodotto dei valori contenuti in un array unidimensionale.
    public static long productArray(int[] arr) {
        return productArrayRec(arr, 0);
    }

    private static long productArrayRec(int[] arr, int i) {
        if (i >= arr.length) return 1;
        return arr[i] * productArrayRec(arr, i + 1);
    }
    
    //10. Calcolare il massimo comune divisore tra due numeri
    public static int mcd(int a, int b) {
        if(b == 0)
            return a;
        return mcd(b, a % b);
    }
}