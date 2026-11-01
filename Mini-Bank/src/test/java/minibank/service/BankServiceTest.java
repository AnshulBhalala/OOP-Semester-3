package minibank.service;

import minibank.exception.BankException;
import minibank.model.Account;
import minibank.util.FileStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {
    @TempDir Path temporaryDirectory;

    private BankService newBank() {
        return new BankService(new FileStore(temporaryDirectory));
    }

    private static BigDecimal money(String value) { return new BigDecimal(value); }

    @Test void createsSavingsAndCurrentAccountsWithUniqueNumbers() throws Exception {
        BankService bank = newBank();
        Account savings = bank.createAccount("Riya Patel", "SAVINGS", money("1000"));
        Account current = bank.createAccount("Aarav Shah", "CURRENT", money("1000"));

        assertEquals("SAVINGS", savings.getAccountType());
        assertEquals("CURRENT", current.getAccountType());
        assertNotEquals(savings.getAccountNumber(), current.getAccountNumber());
    }

    @Test void currentAccountMayStartAtZero() throws Exception {
        BankService bank = newBank();

        Account current = bank.createAccount("Aarav Shah", "CURRENT", BigDecimal.ZERO);

        assertEquals(BigDecimal.ZERO, current.getBalance());
    }

    @Test void depositIncreasesBalance() throws Exception {
        BankService bank = newBank();
        Account account = bank.createAccount("Riya Patel", "SAVINGS", money("1000"));

        bank.deposit(account.getAccountNumber(), money("250"));

        assertEquals(money("1250"), account.getBalance());
    }

    @Test void withdrawalDecreasesBalance() throws Exception {
        BankService bank = newBank();
        Account account = bank.createAccount("Riya Patel", "SAVINGS", money("1000"));

        bank.withdraw(account.getAccountNumber(), money("200"));

        assertEquals(money("800"), account.getBalance());
    }

    @Test void withdrawalBelowSavingsMinimumIsRejected() throws Exception {
        BankService bank = newBank();
        Account account = bank.createAccount("Riya Patel", "SAVINGS", money("600"));

        assertThrows(BankException.class,
                () -> bank.withdraw(account.getAccountNumber(), money("101")));
        assertEquals(money("600"), account.getBalance());
    }

    @Test void invalidDepositAmountIsRejected() throws Exception {
        BankService bank = newBank();
        Account account = bank.createAccount("Riya Patel", "SAVINGS", money("600"));

        assertThrows(BankException.class,
                () -> bank.deposit(account.getAccountNumber(), BigDecimal.ZERO));
        assertEquals(money("600"), account.getBalance());
    }

    @Test void transferUpdatesBothAccountsAndRecordsHistory() throws Exception {
        BankService bank = newBank();
        Account from = bank.createAccount("Riya Patel", "SAVINGS", money("1000"));
        Account to = bank.createAccount("Aarav Shah", "CURRENT", money("100"));

        bank.transfer(from.getAccountNumber(), to.getAccountNumber(), money("300"));

        assertEquals(money("700"), from.getBalance());
        assertEquals(money("400"), to.getBalance());
        assertEquals("TRANSFER", bank.getTransactions().get(2).getType());
    }

    @Test void unknownAccountNumberIsReported() {
        BankService bank = newBank();

        assertThrows(BankException.class, () -> bank.getAccount("not-an-account"));
    }

    @Test void concurrentDepositsKeepTheExactBalance() throws Exception {
        BankService bank = newBank();
        Account account = bank.createAccount("Riya Patel", "SAVINGS", money("500"));
        int workers = 8;
        int depositsPerWorker = 250;
        ExecutorService pool = Executors.newFixedThreadPool(workers);
        CountDownLatch ready = new CountDownLatch(workers);
        CountDownLatch start = new CountDownLatch(1);
        for (int i = 0; i < workers; i++) {
            pool.submit(() -> {
                ready.countDown();
                try {
                    start.await();
                    for (int j = 0; j < depositsPerWorker; j++)
                        bank.deposit(account.getAccountNumber(), BigDecimal.ONE);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        assertTrue(ready.await(5, TimeUnit.SECONDS));
        start.countDown();
        pool.shutdown();
        assertTrue(pool.awaitTermination(20, TimeUnit.SECONDS));

        assertEquals(money("2500"), bank.getAccount(account.getAccountNumber()).getBalance());
    }

    @Test void dataIsReloadedAfterCreatingANewService() throws Exception {
        BankService firstRun = newBank();
        Account account = firstRun.createAccount("Riya Patel", "SAVINGS", money("800"));
        firstRun.deposit(account.getAccountNumber(), money("150"));

        BankService restarted = new BankService(new FileStore(temporaryDirectory));

        assertEquals(money("950"), restarted.getAccount(account.getAccountNumber()).getBalance());
        assertEquals(2, restarted.getTransactions().size());
    }

    @Test void invalidAccountCreationIsRejected() {
        BankService bank = newBank();

        assertThrows(BankException.class,
                () -> bank.createAccount("   ", "SAVINGS", money("500")));
        assertThrows(BankException.class,
                () -> bank.createAccount("Riya Patel", "UNKNOWN", money("500")));
        assertThrows(BankException.class,
                () -> bank.createAccount("Riya Patel", "SAVINGS", money("100")));
        assertTrue(bank.getAllAccounts().isEmpty());
    }
}
