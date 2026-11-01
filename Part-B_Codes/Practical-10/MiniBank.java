import java.util.*;
import java.util.concurrent.*;

class Account {
    private long balance;

    synchronized void deposit(long a) {
        if (a > 0)
            balance += a;
    }

    synchronized boolean withdraw(long a) {
        if (a <= 0 || balance < a)
            return false;
        balance -= a;
        return true;
    }

    synchronized long getBalance() {
        return balance;
    }
}

class TransactionProcessor implements AutoCloseable {
    private final ExecutorService pool = Executors.newFixedThreadPool(3);

    void deposit(Account a, long amount) {
        pool.submit(() -> a.deposit(amount));
    }

    void withdraw(Account a, long amount) {
        pool.submit(() -> a.withdraw(amount));
    }

    public void close() {
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class MiniBank {
    public static void main(String[] args) {
        Account a = new Account();
        try (TransactionProcessor p = new TransactionProcessor()) {
            for (int i = 0; i < 100; i++)
                p.deposit(a, 100);
        }
        System.out.println("Balance after batch: " + a.getBalance());
    }
}
