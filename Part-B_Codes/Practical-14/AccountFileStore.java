package service;

import model.Account;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class AccountFileStore {
    private final Path file;

    public AccountFileStore(Path file) {
        this.file = file;
    }

    public void save(List<Account> accounts) throws IOException {
        Files.createDirectories(file.toAbsolutePath().getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Account a : accounts) {
                writer.write(a.getAccountNumber() + "|" + a.getOwnerName() + "|" + a.getBalance());
                writer.newLine();
            }
        }
    }

    public void loadInto(BankService service) throws IOException {
        if (!Files.exists(file))
            return;
        for (String line : Files.readAllLines(file)) {
            String[] p = line.split("\\|", 3);
            if (p.length == 3 && service.getRepository().findById(p[0]) == null) {
                service.getRepository().save(new Account(p[0], p[1], Long.parseLong(p[2])));
            }
        }
    }
}
