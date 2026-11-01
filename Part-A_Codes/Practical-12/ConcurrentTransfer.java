import java.util.concurrent.*;

public class ConcurrentTransfer {
    static class Bank {
        final ConcurrentHashMap<String, Long> b = new ConcurrentHashMap<>();

        synchronized boolean transfer(String from, String to, long amount) {
            if (amount <= 0 || b.get(from) < amount)
                return false;
            b.put(from, b.get(from) - amount);
            b.put(to, b.get(to) + amount);
            return true;
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.b.put("A", 1000L);
        bank.b.put("B", 1000L);
        System.out.println(bank.transfer("A", "B", 250));
        System.out.println(bank.b);
    }
}
