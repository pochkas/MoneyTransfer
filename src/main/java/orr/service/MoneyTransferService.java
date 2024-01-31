package orr.service;

import com.google.inject.ImplementedBy;
import orr.dto.MoneyTransferDto;
import orr.models.MoneyTransfer;
import orr.service.Impl.AccountServiceImpl;
import orr.service.Impl.MoneyTransferServiceImpl;

import java.util.Collection;
import java.util.Optional;
@ImplementedBy(MoneyTransferServiceImpl.class)
public interface MoneyTransferService {

    Collection<MoneyTransfer> getAll();
    Optional<MoneyTransfer> findById(Long id);
    MoneyTransfer getById(Long id);
    Long add(MoneyTransfer moneyTransfer);
    void performTransaction(MoneyTransferDto moneyTransferDto);
}
