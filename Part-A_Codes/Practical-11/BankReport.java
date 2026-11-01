import java.util.*;

public class BankReport {
    record Tx(String type, long amount) {
    }

    public static void main(String[] args) {
        List<Tx> tx = List.of(new Tx("DEPOSIT", 5000), new Tx("WITHDRAW", 1200), new Tx("DEPOSIT", 300));
        long deposits = tx.stream().filter(t -> t.type.equals("DEPOSIT")).mapToLong(Tx::amount).sum();
        long withdrawals = tx.stream().filter(t -> t.type.equals("WITHDRAW")).mapToLong(Tx::amount).sum();
        System.out.println("Transactions: " + tx.size());
        System.out.println("Deposits: " + deposits);
        System.out.println("Withdrawals: " + withdrawals);
        System.out.println("Net: " + (deposits - withdrawals));
    }
}
