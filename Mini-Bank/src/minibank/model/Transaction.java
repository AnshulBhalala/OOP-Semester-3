package minibank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String type;
    private final String fromAccount;
    private final String toAccount;
    private final BigDecimal amount;
    private final LocalDateTime time;
    private final String note;

    public Transaction(String type, String fromAccount, String toAccount, BigDecimal amount, String note) {
        this(type, fromAccount, toAccount, amount, note, LocalDateTime.now());
    }

    private Transaction(String type, String fromAccount, String toAccount, BigDecimal amount,
                        String note, LocalDateTime time) {
        this.type = type;
        this.fromAccount = fromAccount == null ? "" : fromAccount;
        this.toAccount = toAccount == null ? "" : toAccount;
        this.amount = amount;
        this.note = note == null ? "" : note;
        this.time = time;
    }

    public String getType() { return type; }
    public String getFromAccount() { return fromAccount; }
    public String getToAccount() { return toAccount; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTime() { return time; }
    public String getNote() { return note; }

    public String toCsv() {
        return String.join("|", type, fromAccount, toAccount, amount.toPlainString(),
                time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), note.replace("|", "/"));
    }

    public static Transaction fromCsv(String line) {
        String[] p = line.split("\\|", 6);
        return new Transaction(p[0], p[1], p[2], new BigDecimal(p[3]),
                p.length > 5 ? p[5] : "",
                LocalDateTime.parse(p[4], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Override
    public String toString() {
        return time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) +
                " | " + type + " | From: " + (fromAccount.isBlank() ? "-" : fromAccount) +
                " | To: " + (toAccount.isBlank() ? "-" : toAccount) +
                " | Amount: ₹" + amount.toPlainString() +
                (note.isBlank() ? "" : " | " + note);
    }
}
