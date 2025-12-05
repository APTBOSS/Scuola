public class Bracket_Validator {
    // Converte caratteri Unicode simili in quelli ASCII standard
    private static char normalizza(char c) {
        return switch (c) {
            case '（' -> '(';
            case '）' -> ')';
            case '［' -> '[';
            case '］' -> ']';
            case '｛' -> '{';
            case '｝' -> '}';
            default -> c;
        };
    }

    public static boolean balance_brackets(String espr) {
        Stack<Character> stack = new Stack<>();

        for (char raw : espr.toCharArray()) {

            char c = normalizza(raw); // ← normalizzazione

            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            else if (c == ')' || c == ']' || c == '}') {

                if (stack.isEmpty()) return false;

                char top = stack.pop();

                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
        }

        return stack.isEmpty();
    }
}
