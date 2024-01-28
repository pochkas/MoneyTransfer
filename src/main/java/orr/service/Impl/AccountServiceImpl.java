package orr.service.Impl;

import com.google.inject.Inject;
import orr.dao.impl.AccountDaoImpl;
import orr.dto.AccountDto;
import orr.exception.AccountNotFoundException;
import orr.models.Account;
import orr.service.AccountService;

import java.util.Collection;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountDaoImpl accountDao;

    @Inject
    public AccountServiceImpl(AccountDaoImpl accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Collection<Account> getAll() {
        return accountDao.getAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public Account getById(Long id) {
        return accountDao.getById(id);
    }

    @Override
    public Account update(Long id, AccountDto accountDto) {
        return accountDao.update(id, accountDto);
    }

    @Override
    public void delete(Long id) {
        try {
            accountDao.delete(id);
        } catch (Exception exception) {
            throw new AccountNotFoundException(id);
        }
    }

    @Override
    public Account add(Long userId, Account account) {
        return accountDao.add(userId, account);
    }

    @Override
    public Optional<Account> findByAccountNumber(Long accountNumberId) {
        return accountDao.findByAccountNumber(accountNumberId);
    }

    @Override
    public Account getByAccountNumber(Long accountNumberId) {
        return accountDao.getByAccountNumber(accountNumberId);
    }
}
