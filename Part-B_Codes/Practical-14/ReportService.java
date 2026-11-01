package service;

import model.Account;
import java.util.Comparator;
import java.util.List;

public class ReportService {
    public String buildReport(List<Account> accounts) {
        long total = accounts.stream().mapToLong(Account::getBalance).sum();
        StringBuilder report = new StringBuilder("=== MiniBank Report ===\n");
        accounts.stream().sorted(Comparator.comparingLong(Account::getBalance).reversed())
                .forEach(a -> report.append(a).append('\n'));
        report.append("Total accounts: ").append(accounts.size()).append('\n');
        report.append("Total balance: ").append(total);
        return report.toString();
    }
}
