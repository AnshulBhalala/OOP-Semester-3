import java.util.concurrent.*;

public class ConcurrentStore {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Long> balances = new ConcurrentHashMap<>();
        balances.put("AC0001", 0L);
        Thread[] ts = new Thread[10];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++)
                    balances.compute("AC0001", (k, v) -> v + 1);
            });
            ts[i].start();
        }
        for (Thread t : ts)
            t.join();
        System.out.println("Balance: " + balances.get("AC0001"));
    }
}
