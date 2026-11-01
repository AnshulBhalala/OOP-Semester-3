import java.util.*;

public class MiniBank {
    record BankInfo(String name, String branch) {
    }

    enum MenuOption {
        OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(new BankInfo("MiniBank", "Anand Branch"));
        boolean running = true;
        while (running) {
            System.out.println("1 Open Account  2 Deposit  3 Withdraw  4 Transfer  5 Exit");
            int n = sc.nextInt();
            MenuOption option = switch (n) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };
            if (option == null) {
                System.out.println("Invalid option");
                continue;
            }
            switch (option) {
                case OPEN_ACCOUNT -> System.out.println("Open account — to be implemented in a later lab");
                case DEPOSIT -> System.out.println("Deposit — to be implemented in a later lab");
                case WITHDRAW -> System.out.println("Withdraw — to be implemented in a later lab");
                case TRANSFER -> System.out.println("Transfer — to be implemented in a later lab");
                case EXIT -> {
                    System.out.println("Goodbye");
                    running = false;
                }
            }
        }
    }
}
