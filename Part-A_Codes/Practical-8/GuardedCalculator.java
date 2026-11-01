import java.util.*;

class DivideByZeroException extends Exception {
    DivideByZeroException(String m) {
        super(m);
    }
}

public class GuardedCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("First number (q to quit): ");
                String x = sc.next();
                if (x.equalsIgnoreCase("q"))
                    break;
                double a = Double.parseDouble(x);
                System.out.print("Operator and second number: ");
                String op = sc.next();
                double b = Double.parseDouble(sc.next());
                double result;
                switch (op) {
                    case "+" -> result = a + b;
                    case "-" -> result = a - b;
                    case "*" -> result = a * b;
                    case "/" -> {
                        if (b == 0)
                            throw new DivideByZeroException("Cannot divide by zero");
                        result = a / b;
                    }
                    default -> throw new IllegalArgumentException("Unknown operator");
                }
                System.out.println("Result: " + result);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number");
            } catch (DivideByZeroException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Attempt logged");
            }
        }
    }
}
