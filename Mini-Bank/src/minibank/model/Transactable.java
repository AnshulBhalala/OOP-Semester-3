package minibank.model;

import java.math.BigDecimal;
import minibank.exception.BankException;

public interface Transactable {
    void deposit(BigDecimal amount) throws BankException;
    void withdraw(BigDecimal amount) throws BankException;
}
