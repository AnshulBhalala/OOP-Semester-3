import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PersistenceDemo {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("accounts.txt");
        List<String> accounts = Files.exists(path) ? Files.readAllLines(path) : new ArrayList<>();
        if (accounts.isEmpty())
            accounts.add("AC0001,Anshul,5000");
        Files.write(path, accounts);
        System.out.println("Loaded accounts:");
        accounts.forEach(System.out::println);
        System.out.println("Saved to " + path.toAbsolutePath());
    }
}
