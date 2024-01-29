package orr.dao;

import orr.dto.AccountDto;
import orr.models.Account;
import orr.models.User;

import java.util.Collection;
import java.util.Optional;

public interface AccountDao {
    Collection<Account> getAll();
    Optional<Account> findById(Long id);
    Account getById(Long id);
    Optional<Account> findByAccountNumber(Long accountNumber);
    Account getByAccountNumber(Long accountNumber);
    Account update(Long id, AccountDto accountDto);
    void delete(Long id);
    Long add(Long userId, AccountDto accountDto);
}
