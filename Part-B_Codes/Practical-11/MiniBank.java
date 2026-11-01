import java.io.*;
import java.nio.file.*;
import java.util.*;

class Account {
    final String id, owner;
    long balance;

    Account(String i, String o, long b) {
        id = i;
        owner = o;
        balance = b;
    }

    String row() {
        return id + "," + owner + "," + balance;
    }

    static Account parse(String s) {
        String[] p = s.split(",");
        return new Account(p[0], p[1], Long.parseLong(p[2]));
    }

    public String toString() {
        return row();
    }
}

class AccountStore {
    private final Path path = Path.of("minibank-accounts.csv");

    void save(Collection<Account> accounts) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Account a : accounts)
            lines.add(a.row());
        Files.write(path, lines);
    }

    List<Account> load() throws IOException {
        List<Account> out = new ArrayList<>();
        if (Files.exists(path))
            for (String s : Files.readAllLines(path))
                if (!s.isBlank())
                    out.add(Account.parse(s));
        return out;
    }
}

public class MiniBank {
    public static void main(String[] args) throws Exception {
        AccountStore store = new AccountStore();
        List<Account> accounts = store.load();
        if (accounts.isEmpty())
            accounts.add(new Account("AC0001", "Anshul", 5000));
        store.save(accounts);
        System.out.println("Accounts loaded:");
        accounts.forEach(System.out::println);
        System.out.println("Report: count=" + accounts.size() + ", total balance="
                + accounts.stream().mapToLong(a -> a.balance).sum());
    }
}
