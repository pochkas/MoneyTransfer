package orr.service;

import orr.dto.AccountDto;
import orr.models.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {

    Collection<Account> getAll();
    Optional<Account> findById(Long id);
    Account getById(Long id);
    Account update(Long id, AccountDto accountDto);
    void delete(Long id);
    Long add(Long userId, AccountDto accountDto);
    Optional<Account> findByAccountNumber(Long accountNumberId);
    Account getByAccountNumber(Long accountNumberId);
}
