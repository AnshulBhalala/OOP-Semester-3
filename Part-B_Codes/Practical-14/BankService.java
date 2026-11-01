package service;

import model.Account;
import repository.Repository;
import exception.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BankService {
    private final Repository<Account> repository;
    private final AtomicInteger nextId = new AtomicInteger(1);

    public BankService(Repository<Account> repository) {
        this.repository = repository;
    }

    public Account openAccount(String ownerName, long openingBalance) {
        String id = "AC" + String.format("%04d", nextId.getAndIncrement());
        Account account = new Account(id, ownerName, openingBalance);
        repository.save(account);
        return account;
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = repository.findById(accountNumber);
        if (account == null)
            throw new AccountNotFoundException(accountNumber);
        return account;
    }

    public void deposit(String accountNumber, long amount) throws BankException {
        if (amount <= 0)
            throw new InvalidAmountException(amount);
        getAccount(accountNumber).deposit(amount);
    }

    public void withdraw(String accountNumber, long amount) throws BankException {
        if (amount <= 0)
            throw new InvalidAmountException(amount);
        Account account = getAccount(accountNumber);
        if (account.getBalance() < amount)
            throw new InsufficientFundsException(amount - account.getBalance());
        account.withdraw(amount);
    }

    public void transfer(String from, String to, long amount) throws BankException {
        if (amount <= 0)
            throw new InvalidAmountException(amount);
        Account source = getAccount(from);
        Account destination = getAccount(to);
        synchronized (BankService.class) {
            if (source.getBalance() < amount)
                throw new InsufficientFundsException(amount - source.getBalance());
            source.withdraw(amount);
            destination.deposit(amount);
        }
    }

    public String statement(String accountNumber) throws BankException {
        return getAccount(accountNumber).toString();
    }

    public List<Account> listAccounts() {
        return repository.findAll();
    }

    public Repository<Account> getRepository() {
        return repository;
    }
}
