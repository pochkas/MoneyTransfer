package orr.service;

import orr.models.MoneyTransfer;

import java.util.Collection;
import java.util.Optional;

public interface MoneyTransferService {

    Collection<MoneyTransfer> getAll();
    Optional<MoneyTransfer> findById(Long id);
    MoneyTransfer getById(Long id);
    MoneyTransfer add(MoneyTransfer moneyTransfer);
    MoneyTransfer performTransaction(Long fromAccountNumber, Long toAccountNumber, double amount);
}
