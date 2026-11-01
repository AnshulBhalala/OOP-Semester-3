package app;

import model.Account;
import repository.Repository;
import service.*;
import exception.BankException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class MiniBank {
    public static void main(String[] args) {
        Repository<Account> repository = new Repository<>();
        BankService bank = new BankService(repository);
        AccountFileStore fileStore = new AccountFileStore(Path.of("data", "accounts.txt"));
        ReportService reports = new ReportService();

        try {
            fileStore.loadInto(bank);
        } catch (Exception e) {
            System.out.println("Could not load saved accounts: " + e.getMessage());
        }

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n===== MiniBank =====");
                System.out.println("1. Open account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. Statement");
                System.out.println("6. List accounts");
                System.out.println("7. Run report");
                System.out.println("8. Process sample batch");
                System.out.println("0. Save and exit");
                System.out.print("Choice: ");
                String choice = sc.nextLine().trim();
                try {
                    switch (choice) {
                        case "1" -> {
                            System.out.print("Owner name: ");
                            String owner = sc.nextLine();
                            System.out.print("Opening balance: ");
                            long amount = Long.parseLong(sc.nextLine());
                            System.out.println("Created: " + bank.openAccount(owner, amount));
                        }
                        case "2" -> {
                            System.out.print("Account number: "); String id = sc.nextLine();
                            System.out.print("Amount: "); long amount = Long.parseLong(sc.nextLine());
                            bank.deposit(id, amount); System.out.println("Deposit successful.");
                        }
                        case "3" -> {
                            System.out.print("Account number: "); String id = sc.nextLine();
                            System.out.print("Amount: "); long amount = Long.parseLong(sc.nextLine());
                            bank.withdraw(id, amount); System.out.println("Withdrawal successful.");
                        }
                        case "4" -> {
                            System.out.print("From account: "); String from = sc.nextLine();
                            System.out.print("To account: "); String to = sc.nextLine();
                            System.out.print("Amount: "); long amount = Long.parseLong(sc.nextLine());
                            bank.transfer(from, to, amount); System.out.println("Transfer successful.");
                        }
                        case "5" -> {
                            System.out.print("Account number: ");
                            System.out.println(bank.statement(sc.nextLine()));
                        }
                        case "6" -> bank.listAccounts().forEach(System.out::println);
                        case "7" -> System.out.println(reports.buildReport(bank.listAccounts()));
                        case "8" -> {
                            if (bank.listAccounts().isEmpty()) {
                                System.out.println("Open an account first.");
                                break;
                            }
                            Account first = bank.listAccounts().get(0);
                            List<Runnable> jobs = new ArrayList<>();
                            for (int i = 0; i < 5; i++) jobs.add(() -> first.deposit(100));
                            try (TransactionProcessor processor = new TransactionProcessor()) {
                                processor.processBatch(jobs);
                            }
                            System.out.println("Batch complete. " + first);
                        }
                        case "0" -> {
                            fileStore.save(bank.listAccounts());
                            System.out.println("Data saved. Goodbye.");
                            return;
                        }
                        default -> System.out.println("Invalid menu option.");
                    }
                } catch (BankException | IllegalArgumentException e) {
                    System.out.println("Operation failed: " + e.getMessage());
                } catch (InterruptedException | ExecutionException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Batch processing failed: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Application error: " + e.getMessage());
        }
    }
}
