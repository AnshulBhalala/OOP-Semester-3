package model;

import model.annotation.Id;

public class Account {
    @Id
    private final String accountNumber;
    private final String ownerName;
    private long balance;

    public Account(String accountNumber, String ownerName, long openingBalance) {
        if (accountNumber == null || accountNumber.isBlank())
            throw new IllegalArgumentException("Account number required");
        if (ownerName == null || ownerName.isBlank())
            throw new IllegalArgumentException("Owner name required");
        if (openingBalance < 0)
            throw new IllegalArgumentException("Opening balance cannot be negative");
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = openingBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public synchronized long getBalance() {
        return balance;
    }

    public synchronized void deposit(long amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        balance += amount;
    }

    public synchronized void withdraw(long amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        if (balance < amount)
            throw new IllegalStateException("Insufficient funds. Shortfall: " + (amount - balance));
        balance -= amount;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Balance: " + getBalance();
    }
}
