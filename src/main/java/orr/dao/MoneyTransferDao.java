package orr.dao;

import orr.models.MoneyTransfer;

import java.util.Collection;
import java.util.Optional;

public interface MoneyTransferDao {
    Collection<MoneyTransfer> getAll();
    Optional<MoneyTransfer> findById(Long id);
    MoneyTransfer getById(Long id);
    Long add(MoneyTransfer moneyTransfer);
    MoneyTransfer performTransaction(Long fromAccountNumber, Long toAccountNumber, double amount);
}
