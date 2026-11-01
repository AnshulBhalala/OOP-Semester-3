class Account {
    private long balance;

    synchronized void deposit(long amount) {
        balance += amount;
    }

    synchronized boolean withdraw(long amount) {
        if (amount <= 0 || amount > balance)
            return false;
        balance -= amount;
        return true;
    }

    synchronized long getBalance() {
        return balance;
    }
}

class AccountWorker implements Runnable {
    private final Account account;
    private final int times;
    private final long amount;

    AccountWorker(Account a, int t, long m) {
        account = a;
        times = t;
        amount = m;
    }

    public void run() {
        for (int i = 0; i < times; i++)
            account.deposit(amount);
        System.out.println(Thread.currentThread().getName() + " completed");
    }
}

public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        Account a = new Account();
        Thread[] workers = new Thread[10];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread(new AccountWorker(a, 100, 10), "worker-" + i);
            workers[i].start();
        }
        for (Thread t : workers)
            t.join();
        System.out.println("Final balance: " + a.getBalance() + " (expected 10000)");
    }
}
