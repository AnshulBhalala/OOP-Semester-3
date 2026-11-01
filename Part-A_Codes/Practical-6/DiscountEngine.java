import java.util.*;
import java.util.function.*;

public class DiscountEngine {
    public static void main(String[] args) {
        List<Double> prices = List.of(100.0, 250.0, 400.0);
        Map<String, UnaryOperator<Double>> rules = Map.of("TEN_PERCENT", p -> p * .9, "FLAT_50",
                p -> Math.max(0, p - 50));
        Scanner sc = new Scanner(System.in);
        System.out.print("Rule (TEN_PERCENT/FLAT_50): ");
        String key = sc.nextLine().toUpperCase();
        UnaryOperator<Double> rule = rules.getOrDefault(key, UnaryOperator.identity());
        prices.forEach(p -> System.out.println(p + " -> " + rule.apply(p)));
    }
}
