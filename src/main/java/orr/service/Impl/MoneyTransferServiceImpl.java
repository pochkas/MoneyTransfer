package orr.service.Impl;

import com.google.inject.Inject;
import orr.dao.impl.MoneyTransferDaoImpl;
import orr.models.MoneyTransfer;
import orr.service.MoneyTransferService;

import java.util.Collection;
import java.util.Optional;

public class MoneyTransferServiceImpl implements MoneyTransferService {

    private final MoneyTransferDaoImpl moneyTransferDao;

    @Inject
    public MoneyTransferServiceImpl(MoneyTransferDaoImpl moneyTransferDao) {
        this.moneyTransferDao = moneyTransferDao;
    }

    @Override
    public Collection<MoneyTransfer> getAll() {
        return null;
    }

    @Override
    public Optional<MoneyTransfer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public MoneyTransfer getById(Long id) {
        return null;
    }

    @Override
    public MoneyTransfer add(MoneyTransfer moneyTransfer) {
        return null;
    }

    @Override
    public void createTransaction(Long fromAccountNumber, Long toAccountNumber, double amount) {
        moneyTransferDao.performTransaction(fromAccountNumber, toAccountNumber, amount);
    }
}
