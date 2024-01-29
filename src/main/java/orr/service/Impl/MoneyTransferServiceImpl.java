package orr.service.Impl;

import com.google.inject.Inject;
import orr.dao.impl.MoneyTransferDaoImpl;
import orr.exception.MoneyTransferException;
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
        return moneyTransferDao.getAll();
    }

    @Override
    public Optional<MoneyTransfer> findById(Long id) {
        return Optional.ofNullable(moneyTransferDao.findById(id).orElseThrow(() -> new MoneyTransferException(id)));
    }

    @Override
    public MoneyTransfer getById(Long id) {
            return moneyTransferDao.getById(id);
    }

    @Override
    public Long add(MoneyTransfer moneyTransfer) {
        return moneyTransferDao.add(moneyTransfer);
    }

    @Override
    public MoneyTransfer performTransaction(Long fromAccountNumber, Long toAccountNumber, double amount) {
       return moneyTransferDao.performTransaction(fromAccountNumber, toAccountNumber, amount);
    }
}
