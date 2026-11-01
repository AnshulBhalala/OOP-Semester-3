package minibank.model;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    public static final BigDecimal OVERDRAFT_LIMIT = new BigDecimal("-1000.00");

    public CurrentAccount(String number, String name, BigDecimal openingBalance) {
        super(number, name, openingBalance);
        if (openingBalance.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Opening balance cannot be negative.");
    }

    @Override protected BigDecimal minimumAllowedBalance() { return OVERDRAFT_LIMIT; }
    @Override public String getAccountType() { return "CURRENT"; }
}
