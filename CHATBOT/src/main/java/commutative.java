import java.util.Scanner;

public class commutative {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Boolean Expression Simplifier");
        System.out.println("Enter a Boolean expression or type 'exit' to quit:");

        while (true) {
            String expression = scanner.nextLine().trim();

            if (expression.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            // Make the expression case-insensitive and remove spaces
            expression = expression.toLowerCase().replaceAll("\\s+", "");

            // Simplify the expression using various Boolean laws
            String simplifiedExpression = simplifyBooleanExpression(expression);

            // Convert the simplified expression to uppercase
            simplifiedExpression = simplifiedExpression.toUpperCase();

            System.out.println("Simplified expression: " + simplifiedExpression);
        }

        scanner.close();
    }

    // Simplify the expression using various Boolean laws with a switch-case statement
    private static String simplifyBooleanExpression(String expression) {
        char operator = expression.charAt(1);
        char constant = expression.charAt(2);

        switch (operator) {
            case '+':
                if (constant == '0') {
                    // A + 0 simplifies to A (Identity Law)
                    return String.valueOf(expression.charAt(0));
                    
                    
                } else if (constant == expression.charAt(0)) {
                    // A + A simplifies to A (Null Law)
                    return String.valueOf(expression.charAt(0));
                } else if (constant == '1') {
                    // A + 1 simplifies to 1 (Domination Law)
                    return "1";
                }
                break;

            case '*':
                if (constant == '1') {
                    // A * 1 simplifies to A (Identity Law)
                    return String.valueOf(expression.charAt(0));
                } else if (constant != expression.charAt(0)) {
                    // A * A' simplifies to 0 (Null Law)
                    return "0";
                } else if (constant == '0') {
                    // A * 0 simplifies to 0 (Domination Law)
                    return "0";
                }
                break;
        }

        if (expression.charAt(0) == expression.charAt(2)) {
            // A + A or A * A simplifies to A (Idempotent Law)
            return String.valueOf(expression.charAt(0));
        }

        if (expression.charAt(2) == '\'') {
            // A' simplifies to A (Complement Law)
            return String.valueOf(expression.charAt(0));
        }

        if (expression.charAt(0) != expression.charAt(2)) {
            // A + B simplifies to B + A (Commutative Law for OR)
            return String.valueOf(expression.charAt(2)) + " + " + String.valueOf(expression.charAt(0));
        }

        return "NO SIMPLIFICATION POSSIBLE. INPUT DOES NOT MATCH ANY APPLICABLE LAW.";
    }
}
