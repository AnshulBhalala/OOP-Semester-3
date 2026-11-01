package minibank.util;

import minibank.model.Account;
import minibank.model.Transaction;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileStore {
    private final Path directory;
    public FileStore(Path directory) {
        this.directory = directory;
        try { Files.createDirectories(directory); }
        catch (IOException ex) { throw new IllegalStateException("Cannot create data directory: " + ex.getMessage()); }
    }

    public List<String> readAccounts() { return read(directory.resolve("accounts.txt")); }
    public List<String> readTransactions() { return read(directory.resolve("transactions.txt")); }

    public void saveAccounts(Collection<Account> accounts) {
        List<String> lines = accounts.stream().map(Account::toCsv).toList();
        write(directory.resolve("accounts.txt"), lines);
    }

    public void saveTransactions(List<Transaction> transactions) {
        List<String> lines = transactions.stream().map(Transaction::toCsv).toList();
        write(directory.resolve("transactions.txt"), lines);
    }

    private List<String> read(Path file) {
        try { return Files.exists(file) ? Files.readAllLines(file) : new ArrayList<>(); }
        catch (IOException ex) { throw new IllegalStateException("Cannot read " + file + ": " + ex.getMessage()); }
    }

    private void write(Path file, List<String> lines) {
        try { Files.write(file, lines); }
        catch (IOException ex) { throw new IllegalStateException("Cannot save " + file + ": " + ex.getMessage()); }
    }
}
