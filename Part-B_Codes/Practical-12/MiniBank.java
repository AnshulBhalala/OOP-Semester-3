import java.util.concurrent.*;

class Account {
    private long balance;

    synchronized void deposit(long a) {
        if (a > 0)
            balance += a;
    }

    synchronized boolean withdraw(long a) {
        if (a <= 0 || a > balance)
            return false;
        balance -= a;
        return true;
    }

    synchronized long getBalance() {
        return balance;
    }
}

class ConcurrentAccountStore {
    private final ConcurrentHashMap<String, Account> map = new ConcurrentHashMap<>();

    void put(String id, Account a) {
        map.put(id, a);
    }

    Account get(String id) {
        return map.get(id);
    }
}

public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentAccountStore store = new ConcurrentAccountStore();
        Account a = new Account();
        store.put("AC0001", a);
        Thread[] ts = new Thread[10];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++)
                    store.get("AC0001").deposit(1);
            });
            ts[i].start();
        }
        for (Thread t : ts)
            t.join();
        System.out.println("Thread-safe final balance: " + store.get("AC0001").getBalance());
    }
}
