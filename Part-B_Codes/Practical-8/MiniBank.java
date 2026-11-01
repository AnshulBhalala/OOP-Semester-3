class BankException extends Exception {
    BankException(String m) {
        super(m);
    }
}

class InsufficientFundsException extends BankException {
    final long shortfall;

    InsufficientFundsException(long s) {
        super("Insufficient funds; short by " + s);
        shortfall = s;
    }
}

class InvalidAmountException extends BankException {
    InvalidAmountException() {
        super("Amount must be positive");
    }
}

class AccountNotFoundException extends BankException {
    AccountNotFoundException() {
        super("Account not found");
    }
}

class Account {
    private long balance;

    Account(long b) {
        balance = b;
    }

    void deposit(long a) throws InvalidAmountException {
        if (a <= 0)
            throw new InvalidAmountException();
        balance += a;
    }

    void withdraw(long a) throws InsufficientFundsException, InvalidAmountException {
        if (a <= 0)
            throw new InvalidAmountException();
        if (a > balance)
            throw new InsufficientFundsException(a - balance);
        balance -= a;
    }

    void transfer(Account to, long a) throws BankException {
        try {
            withdraw(a);
            to.deposit(a);
        } catch (BankException e) {
            throw e;
        } finally {
            System.out.println("Transfer attempt finished");
        }
    }

    long balance() {
        return balance;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        Account a = new Account(1000), b = new Account(0);
        try {
            a.transfer(b, 5000);
        } catch (BankException e) {
            System.out.println("Operation failed: " + e.getMessage());
        } finally {
            System.out.println("Current balance: " + a.balance());
        }
        System.out.println("Balances: " + a.balance() + ", " + b.balance());
    }
}
