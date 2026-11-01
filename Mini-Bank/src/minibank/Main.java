package minibank;

import minibank.exception.BankException;
import minibank.model.Account;
import minibank.model.Transaction;
import minibank.service.BankService;
import minibank.util.FileStore;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final BankService BANK = new BankService(new FileStore(Path.of("data")));

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("          WELCOME TO MINIBANK");
        System.out.println("       OOP Practical Project | 25IT019");
        System.out.println("========================================");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = prompt("Enter your choice: ");
            try {
                switch (choice) {
                    case "1" -> createAccount();
                    case "2" -> deposit();
                    case "3" -> withdraw();
                    case "4" -> transfer();
                    case "5" -> viewAccount();
                    case "6" -> listAccounts();
                    case "7" -> showTransactions();
                    case "0" -> { running = false; System.out.println("Thank you for banking with MiniBank."); }
                    default -> System.out.println("Invalid choice. Please select 0 to 7.");
                }
            } catch (BankException | IllegalArgumentException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("ERROR: Operation could not be completed: " + ex.getMessage());
            }
            System.out.println();
        }
        INPUT.close();
    }

    private static void printMenu() {
        System.out.println("\n--------------- MAIN MENU ---------------");
        System.out.println("1. Create account");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Transfer money");
        System.out.println("5. View account details");
        System.out.println("6. View all accounts");
        System.out.println("7. Transaction history");
        System.out.println("0. Exit");
        System.out.println("-----------------------------------------");
    }

    private static void createAccount() throws BankException {
        String name = prompt("Customer name: ");
        System.out.println("1. Savings account (minimum ₹500)");
        System.out.println("2. Current account (minimum ₹0; overdraft limit ₹1000)");
        String typeChoice = prompt("Account type: ");
        if (!typeChoice.equals("1") && !typeChoice.equals("2")) {
            System.out.println("Invalid account type.");
            return;
        }
        BigDecimal opening = money("Opening balance: ₹");
        Account a = BANK.createAccount(name, typeChoice.equals("1") ? "SAVINGS" : "CURRENT", opening);
        System.out.println("Account created successfully!");
        System.out.println("Your account number is: " + a.getAccountNumber());
    }

    private static void deposit() throws BankException {
        String number = prompt("Account number: ");
        BigDecimal amount = money("Amount to deposit: ₹");
        BANK.deposit(number, amount);
        System.out.println("Deposit successful.");
        System.out.println("Updated balance: ₹" + BANK.getAccount(number).getBalance());
    }

    private static void withdraw() throws BankException {
        String number = prompt("Account number: ");
        BigDecimal amount = money("Amount to withdraw: ₹");
        BANK.withdraw(number, amount);
        System.out.println("Withdrawal successful.");
        System.out.println("Updated balance: ₹" + BANK.getAccount(number).getBalance());
    }

    private static void transfer() throws BankException {
        String from = prompt("From account number: ");
        String to = prompt("To account number: ");
        BigDecimal amount = money("Transfer amount: ₹");
        BANK.transfer(from, to, amount);
        System.out.println("Transfer completed successfully.");
        System.out.println("Sender balance: ₹" + BANK.getAccount(from).getBalance());
        System.out.println("Receiver balance: ₹" + BANK.getAccount(to).getBalance());
    }

    private static void viewAccount() throws BankException {
        String number = prompt("Account number: ");
        Account a = BANK.getAccount(number);
        System.out.println("\nACCOUNT DETAILS");
        System.out.println("Account number : " + a.getAccountNumber());
        System.out.println("Customer name  : " + a.getCustomerName());
        System.out.println("Account type   : " + a.getAccountType());
        System.out.println("Balance        : ₹" + a.getBalance());
    }

    private static void listAccounts() {
        List<Account> accounts = BANK.getAllAccounts();
        if (accounts.isEmpty()) { System.out.println("No accounts found."); return; }
        System.out.println("\nACCOUNT NO.  | TYPE       | CUSTOMER               | BALANCE");
        System.out.println("---------------------------------------------------------------");
        accounts.forEach(System.out::println);
    }

    private static void showTransactions() {
        List<Transaction> transactions = BANK.getTransactions();
        if (transactions.isEmpty()) { System.out.println("No transactions found."); return; }
        System.out.println("\nTRANSACTION HISTORY");
        transactions.forEach(System.out::println);
    }

    private static String prompt(String message) {
        System.out.print(message);
        return INPUT.nextLine().trim();
    }

    private static BigDecimal money(String message) {
        try { return new BigDecimal(prompt(message)); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("Enter a valid numeric amount."); }
    }
}
