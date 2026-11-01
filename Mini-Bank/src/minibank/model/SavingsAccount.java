package minibank.model;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    public static final BigDecimal MINIMUM_BALANCE = new BigDecimal("500.00");

    public SavingsAccount(String number, String name, BigDecimal openingBalance) {
        super(number, name, openingBalance);
        if (openingBalance.compareTo(MINIMUM_BALANCE) < 0)
            throw new IllegalArgumentException("Savings account requires at least ₹500 opening balance.");
    }

    @Override protected BigDecimal minimumAllowedBalance() { return MINIMUM_BALANCE; }
    @Override public String getAccountType() { return "SAVINGS"; }
}
