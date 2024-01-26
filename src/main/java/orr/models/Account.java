package orr.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Account {

    private Long id;
    private Long accountNumber;
    private double balance;
    private Long userId;

    public Account(Long accountNumber, Long accountHolderId, double currentBalance, Long userId) {
        this.accountNumber = accountNumber;
        this.id = accountHolderId;
        this.balance = currentBalance;
        this.userId = userId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }
}
