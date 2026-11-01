abstract class Account {
    protected String accountNumber, ownerName;
    protected long balance;

    Account(String n, String o, long b) {
        accountNumber = n;
        ownerName = o;
        balance = b;
    }

    abstract double interestRate();

    abstract boolean canWithdraw(long amount);

    public long getBalance() {
        return balance;
    }
}

class SavingsAccount extends Account {
    long minBalance;

    SavingsAccount(String n, String o, long b, long m) {
        super(n, o, b);
        minBalance = m;
    }

    double interestRate() {
        return 4.0;
    }

    boolean canWithdraw(long a) {
        return a > 0 && balance - a >= minBalance;
    }
}

class CurrentAccount extends Account {
    long overdraftLimit;

    CurrentAccount(String n, String o, long b, long l) {
        super(n, o, b);
        overdraftLimit = l;
    }

    double interestRate() {
        return 0.0;
    }

    boolean canWithdraw(long a) {
        return a > 0 && balance - a >= -overdraftLimit;
    }
}

class FixedDepositAccount extends Account {
    FixedDepositAccount(String n, String o, long b) {
        super(n, o, b);
    }

    double interestRate() {
        return 7.0;
    }

    boolean canWithdraw(long a) {
        return false;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        Account[] a = { new SavingsAccount("AC1", "Riya", 5000, 1000), new CurrentAccount("AC2", "Jay", 1000, 2000),
                new FixedDepositAccount("AC3", "Anshul", 10000) };
        for (Account x : a)
            System.out.println(
                    x.accountNumber + " interest=" + x.interestRate() + "% canWithdraw(500)=" + x.canWithdraw(500));
    }
}
