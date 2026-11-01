import java.util.*;

class Customer {
    private final String customerId;
    private String name, email, mobile;
    private static long counter = 100;

    Customer(String n, String e, String m) {
        customerId = "CUST" + (++counter);
        name = n;
        email = e;
        mobile = m;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}

class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active = true;
    private static int counter = 0;

    Account(String owner, long opening) {
        accountNumber = String.format("AC%04d", ++counter);
        ownerName = owner;
        balance = opening;
    }

    Account(String owner) {
        this(owner, 0);
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

    public long getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        Account[] a = { new Account("Riya", 5000), new Account("Anshul"), new Account("Jay", 2500) };
        a[0].deposit(2000);
        a[0].withdraw(3000);
        a[0].withdraw(10000);
        for (Account x : a)
            System.out.println(x.getAccountNumber() + " " + x.getOwnerName() + " balance=" + x.getBalance());
    }
}
