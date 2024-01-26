package orr.service;

import orr.dto.AccountDto;
import orr.models.Account;
import orr.models.User;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {

    Collection<Account> getAll();

    Optional<Account> findById(Long id);

    Account getById(Long id);

    Account update(Long id, AccountDto accountDto);

    void delete(Long id);

    Account add(Long userId, Account account);
}
