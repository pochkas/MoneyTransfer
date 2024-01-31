package orr.dto;

import java.util.Objects;

public class MoneyTransferDto {

    private Long fromAccountNumber;
    private Long toAccountNumber;
    private Double amount;

    public MoneyTransferDto(Long fromAccountNumber, Long toAccountNumber, Double amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyTransferDto that = (MoneyTransferDto) o;
        return Objects.equals(fromAccountNumber, that.fromAccountNumber) && Objects.equals(toAccountNumber, that.toAccountNumber) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromAccountNumber, toAccountNumber, amount);
    }
}
