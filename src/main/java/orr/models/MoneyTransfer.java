package orr.models;

import java.util.Objects;

public class MoneyTransfer {

    private Long id;
    private Long fromAccountNumber;
    private Long toAccountNumber;
    private double amount;

    public MoneyTransfer(Long fromAccountNumber, Long toAccountNumber, double amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
    }

    public MoneyTransfer(Long id, Long fromAccountNumber, Long toAccountNumber, double amount) {
        this.id = id;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(Long fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public Long getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(Long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyTransfer that = (MoneyTransfer) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(id, that.id) && Objects.equals(fromAccountNumber, that.fromAccountNumber) && Objects.equals(toAccountNumber, that.toAccountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromAccountNumber, toAccountNumber, amount);
    }

    @Override
    public String toString() {
        return "MoneyTransfer{" +
                "id=" + id +
                ", fromAccountNumber=" + fromAccountNumber +
                ", toAccountNumber=" + toAccountNumber +
                ", amount=" + amount +
                '}';
    }
}
