package minibank.model;

import java.math.BigDecimal;
import minibank.exception.InsufficientFundsException;
import minibank.exception.InvalidAmountException;
import minibank.exception.BankException;

public abstract class Account implements Transactable {
    private final String accountNumber;
    private final String customerName;
    private BigDecimal balance;

    protected Account(String accountNumber, String customerName, BigDecimal openingBalance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.balance = openingBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getCustomerName() { return customerName; }
    public synchronized BigDecimal getBalance() { return balance; }

    @Override
    public synchronized void deposit(BigDecimal amount) throws BankException {
        validateAmount(amount);
        balance = balance.add(amount);
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) throws BankException {
        validateAmount(amount);
        BigDecimal minimum = minimumAllowedBalance();
        if (balance.subtract(amount).compareTo(minimum) < 0) {
            throw new InsufficientFundsException("Insufficient funds. Available balance: ₹" + balance
                    + "; minimum permitted balance: ₹" + minimum + ".");
        }
        balance = balance.subtract(amount);
    }

    protected abstract BigDecimal minimumAllowedBalance();
    public abstract String getAccountType();

    public static void validateAmount(BigDecimal amount) throws InvalidAmountException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }
    }

    protected void setBalance(BigDecimal balance) { this.balance = balance; }

    public String toCsv() {
        return String.join("|", getAccountType(), accountNumber, customerName.replace("|", "/"),
                balance.toPlainString());
    }

    @Override
    public String toString() {
        return String.format("%-12s | %-10s | %-22s | ₹%s",
                accountNumber, getAccountType(), customerName, balance.toPlainString());
    }
}
