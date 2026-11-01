package minibank.service;

import minibank.exception.BankException;
import minibank.exception.AccountNotFoundException;
import minibank.model.*;
import minibank.util.FileStore;

import java.math.BigDecimal;
import java.util.*;

public class BankService {
    private final Map<String, Account> accounts = new LinkedHashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final FileStore store;

    public BankService(FileStore store) {
        this.store = store;
        load();
    }

    public synchronized Account createAccount(String name, String type, BigDecimal opening) throws BankException {
        if (name == null || name.isBlank()) throw new BankException("Customer name cannot be empty.");
        if (type == null || (!type.equalsIgnoreCase("SAVINGS") && !type.equalsIgnoreCase("CURRENT")))
            throw new BankException("Account type must be SAVINGS or CURRENT.");
        if (opening == null || opening.signum() < 0)
            throw new BankException("Opening balance cannot be negative.");
        String number = nextAccountNumber();
        try {
            Account account = type.equalsIgnoreCase("SAVINGS")
                    ? new SavingsAccount(number, name.trim(), opening)
                    : new CurrentAccount(number, name.trim(), opening);
            accounts.put(number, account);
            transactions.add(new Transaction("OPEN", "", number, opening, "Account created"));
            save();
            return account;
        } catch (IllegalArgumentException ex) {
            throw new BankException(ex.getMessage());
        }
    }

    public synchronized void deposit(String number, BigDecimal amount) throws BankException {
        Account a = requireAccount(number);
        a.deposit(amount);
        transactions.add(new Transaction("DEPOSIT", "", number, amount, "Cash deposit"));
        save();
    }

    public synchronized void withdraw(String number, BigDecimal amount) throws BankException {
        Account a = requireAccount(number);
        a.withdraw(amount);
        transactions.add(new Transaction("WITHDRAW", number, "", amount, "Cash withdrawal"));
        save();
    }

    public synchronized void transfer(String from, String to, BigDecimal amount) throws BankException {
        if (from.equals(to)) throw new BankException("Source and destination accounts must be different.");
        Account source = requireAccount(from);
        Account destination = requireAccount(to);
        Account.validateAmount(amount);
        synchronized (firstByNumber(source, destination)) {
            synchronized (secondByNumber(source, destination)) {
                source.withdraw(amount);
                destination.deposit(amount);
            }
        }
        transactions.add(new Transaction("TRANSFER", from, to, amount, "Account transfer"));
        save();
    }

    public synchronized Account getAccount(String number) throws BankException { return requireAccount(number); }
    public synchronized List<Account> getAllAccounts() { return new ArrayList<>(accounts.values()); }
    public synchronized List<Transaction> getTransactions() { return new ArrayList<>(transactions); }

    private Account requireAccount(String number) throws BankException {
        Account a = accounts.get(number);
        if (a == null) throw new AccountNotFoundException("Account " + number + " was not found.");
        return a;
    }

    private static Account firstByNumber(Account a, Account b) {
        return a.getAccountNumber().compareTo(b.getAccountNumber()) < 0 ? a : b;
    }

    private static Account secondByNumber(Account a, Account b) {
        return a.getAccountNumber().compareTo(b.getAccountNumber()) < 0 ? b : a;
    }

    private String nextAccountNumber() {
        int max = 1000;
        for (String n : accounts.keySet()) {
            try { max = Math.max(max, Integer.parseInt(n)); } catch (NumberFormatException ignored) {}
        }
        return String.valueOf(max + 1);
    }

    private void load() {
        for (String line : store.readAccounts()) {
            try {
                String[] p = line.split("\\|", 4);
                Account a = p[0].equals("SAVINGS")
                        ? new SavingsAccount(p[1], p[2], new BigDecimal(p[3]))
                        : new CurrentAccount(p[1], p[2], new BigDecimal(p[3]));
                accounts.put(a.getAccountNumber(), a);
            } catch (RuntimeException ignored) {}
        }
        for (String line : store.readTransactions()) {
            try { transactions.add(Transaction.fromCsv(line)); } catch (RuntimeException ignored) {}
        }
    }

    private void save() {
        store.saveAccounts(accounts.values());
        store.saveTransactions(transactions);
    }
}
