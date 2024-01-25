package orr.models;

import java.time.LocalDateTime;

public class Account {

    private Long id;
    private Long accountNumber;
    private String accountHolderName;
    private double balance;
    private LocalDateTime createdDate;

    public Account(Long accountNumber, Long accountHolderId, String accountHolderName, double currentBalance, LocalDateTime createdDate) {
        this.accountNumber = accountNumber;
        this.id = accountHolderId;
        this.accountHolderName = accountHolderName;
        this.balance = currentBalance;
        this.createdDate = createdDate;
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

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
