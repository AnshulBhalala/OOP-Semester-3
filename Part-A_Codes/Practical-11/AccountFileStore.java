import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AccountFileStore {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("accounts.csv");
        List<String> rows = List.of("AC0001,Anshul,5000", "AC0002,Riya,2500");
        Files.write(p, rows);
        List<String> loaded = Files.readAllLines(p);
        System.out.println("Persistence verified: " + rows.equals(loaded));
        loaded.forEach(System.out::println);
    }
}
