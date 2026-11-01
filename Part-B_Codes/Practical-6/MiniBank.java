interface Transactable {
    void deposit(long amount);

    boolean withdraw(long amount);
}

interface InterestBearing {
    double interestRate();

    long balance();

    default double yearlyInterest() {
        return balance() * interestRate() / 100.0;
    }
}

@FunctionalInterface
interface WithdrawRule {
    boolean allow(Account account, long amount);
}

interface Premium {
}

class Account implements Transactable, InterestBearing {
    private long balance;
    private final String number;

    Account(String n, long b) {
        number = n;
        balance = b;
    }

    public void deposit(long a) {
        if (a > 0)
            balance += a;
    }

    public boolean withdraw(long a) {
        if (a > 0 && balance >= a) {
            balance -= a;
            return true;
        }
        return false;
    }

    public double interestRate() {
        return 4.0;
    }

    public long balance() {
        return balance;
    }

    public String toString() {
        return number + " balance=" + balance;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        Account a = new Account("AC0001", 5000);
        WithdrawRule anonymous = new WithdrawRule() {
            public boolean allow(Account x, long amount) {
                return amount > 0 && amount <= x.balance();
            }
        };
        WithdrawRule lambda = (x, amount) -> amount > 0 && amount <= x.balance();
        System.out.println("Anonymous: " + anonymous.allow(a, 1000));
        System.out.println("Lambda: " + lambda.allow(a, 1000));
        System.out.println("Yearly interest: " + a.yearlyInterest());
    }
}
