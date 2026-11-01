import java.util.*;

class OutOfStockException extends Exception {
    final int shortfall;

    OutOfStockException(int s) {
        super("Out of stock by " + s);
        shortfall = s;
    }
}

class InvalidQuantityException extends Exception {
    InvalidQuantityException() {
        super("Quantity must be positive");
    }
}

public class Warehouse {
    private final Map<String, Integer> stock = new HashMap<>(Map.of("pen", 5, "book", 2));

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0)
            throw new InvalidQuantityException();
        int available = stock.getOrDefault(item, 0);
        if (qty > available)
            throw new OutOfStockException(qty - available);
        stock.put(item, available - qty);
        System.out.println("Issued " + qty + " " + item);
    }

    public static void main(String[] args) {
        Warehouse w = new Warehouse();
        Object[][] req = { { "pen", 2 }, { "book", 5 }, { "pen", 0 } };
        for (Object[] r : req)
            try {
                w.issue((String) r[0], (int) r[1]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
}
