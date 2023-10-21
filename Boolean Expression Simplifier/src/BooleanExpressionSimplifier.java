import java.util.Stack;

public class BooleanExpressionSimplifier {
  
  public static String simplifyExpression(String expression) {
    // Step 1: Remove unnecessary spaces
    expression = expression.replaceAll("\\s+", "");

    // Step 2: Convert the expression to postfix notation
    String postfixExpression = toPostfix(expression);

    // Step 3: Simplify the expression using a stack
    Stack<String> stack = new Stack<>();
    for (char c : postfixExpression.toCharArray()) {
      if (Character.isLetterOrDigit(c)) {
        stack.push(Character.toString(c));
      } else if (c == '\'') {
        String operand = stack.pop();
        stack.push(applyNegation(operand));
      } else {
        String operand2 = stack.pop();
        String operand1 = stack.pop();
        stack.push(applyOperator(operand1, operand2, c));
      }
    }

    // Step 4: Return the simplified expression
    return stack.pop();
  }

  private static String toPostfix(String expression) {
    StringBuilder postfix = new StringBuilder();
    Stack<Character> stack = new Stack<>();

    for (char c : expression.toCharArray()) {
      if (Character.isLetterOrDigit(c)) {
        postfix.append(c);
      } else if (c == '(') {
        stack.push(c);
      } else if (c == ')') {
        while (!stack.isEmpty() && stack.peek() != '(') {
          postfix.append(stack.pop());
        }
        if (!stack.isEmpty() && stack.peek() == '(') {
          stack.pop();
        }
      } else {
        while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
          postfix.append(stack.pop());
        }
        stack.push(c);
      }
    }

    while (!stack.isEmpty()) {
      postfix.append(stack.pop());
    }

    return postfix.toString();
  }

  private static int getPrecedence(char operator) {
    switch (operator) {
      case '\'':
        return 3;
      case '*':
        return 2;
      case '+':
        return 1;
      default:
        return 0;
    }
  }

  private static String applyOperator(String operand1, String operand2, char operator) {
    // Apply Identity Law
    if (operand1.equals("1") && operator == '+') {
      return "1";
    }
    if (operand1.equals("0") && operator == '*') {
      return "0";
    }

    // Apply Idempotent Law
    if (operand1.equals(operand2) && operator == '+') {
      return operand1;
    }
    if (operand1.equals(operand2) && operator == '*') {
      return operand1;
    }
    
    // Apply Commutative Law
    if (operator == '+' || operator == '*') {
      if (operand1.compareTo(operand2) > 0) {
        String temp = operand1;
        operand1 = operand2;
        operand2 = temp;
      }
    }
    
    // Apply Associative Law
    if (operator == '+') {
      if (operand1.charAt(0) == '(' && operand1.charAt(operand1.length() - 1) == ')') {
        operand1 = operand1.substring(1, operand1.length() - 1);
      }
      if (operand2.charAt(0) == '(' && operand2.charAt(operand2.length() - 1) == ')') {
        operand2 = operand2.substring(1, operand2.length() - 1);
      }
    }
    
    // Apply Distributive Law
    if (operator == '*' && operand1.charAt(0) == '(' && operand2.charAt(0) == '(') {
      operand1 = operand1.substring(1, operand1.length() - 1);
      operand2 = operand2.substring(1, operand2.length() - 1);
      return "(" + operand1 + " + " + operand2 + ")";
    }
    
    // Apply Inversion Law
    if (operand1.charAt(0) == '\'') {
      operand1 = operand1.substring(1);
      if (operator == '+') {
        return "(" + operand1 + " * " + operand2 + ")";
      } else if (operator == '*') {
        return "(" + operand1 + " + " + operand2 + ")";
      }
    }
    
    // Apply De Morgan's Law
    if (operand1.charAt(0) == '(' && operand2.charAt(0) == '(' && operator == '+') {
      operand1 = operand1.substring(1, operand1.length() - 1);
      operand2 = operand2.substring(1, operand2.length() - 1);
      return "\'(" + operand1 + " * " + operand2 + ")";
    }
    if (operand1.charAt(0) == '(' && operand2.charAt(0) == '(' && operator == '*') {
      operand1 = operand1.substring(1, operand1.length() - 1);
      operand2 = operand2.substring(1, operand2.length() - 1);
      return "\'(" + operand1 + " + " + operand2 + ")";
    }

    return "(" + operand1 + " " + operator + " " + operand2 + ")";
  }

  private static String applyNegation(String operand) {
    if (operand.equals("1")) {
      return "0";
    }
    if (operand.equals("0")) {
      return "1";
    }
    if (operand.charAt(0) == '!') {
      return operand.substring(1);
    }
    return "\'" + operand;
  }

  public static void main(String[] args) {
    String expression = "(A'+B')*(B*B)";
    String simplified = simplifyExpression(expression);
    System.out.println("Simplified expression: " + simplified);
  }
}