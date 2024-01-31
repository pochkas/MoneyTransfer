package orr.service.impl;

import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialectCategory;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runners.MethodSorters;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import orr.dao.impl.AccountDaoImpl;
import orr.dao.impl.MoneyTransferDaoImpl;
import orr.dao.impl.UserDaoImpl;
import orr.dto.AccountDto;
import orr.exception.AccountNotFoundException;
import orr.exception.InsufficientFundsException;
import orr.exception.MoneyTransferException;
import orr.exception.MoneyTransferSameAccountException;
import orr.models.Account;
import orr.models.User;
import orr.service.AccountService;
import orr.service.Impl.AccountServiceImpl;
import orr.service.Impl.MoneyTransferServiceImpl;
import orr.service.Impl.UserServiceImpl;
import orr.service.MoneyTransferService;
import orr.service.UserService;
import orr.utils.UUIDSupplier;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MoneyTransferServiceImplTest extends PostgreSQLContainerAbstract {

    private final UUID uuid = UUID.randomUUID();

    private final UUID uuid2 = UUID.randomUUID();

    private UUIDSupplier uuidSupplier = new UUIDSupplier() {
        @Override
        public UUID create() {
            return uuid;
        }
    };

    private UUIDSupplier uuidSupplier2 = new UUIDSupplier() {
        @Override
        public UUID create() {
            return uuid2;
        }
    };
    private MoneyTransferService moneyTransferService = new MoneyTransferServiceImpl(new MoneyTransferDaoImpl(dslContext(providedConfiguration())));
    private UserService userService = new UserServiceImpl(new UserDaoImpl(dslContext(providedConfiguration())));
    private AccountService accountService = new AccountServiceImpl(new AccountDaoImpl(dslContext(providedConfiguration()), uuidSupplier));
    private AccountService accountService2 = new AccountServiceImpl(new AccountDaoImpl(dslContext(providedConfiguration()), uuidSupplier2));

    public List<Account> addUserAndAccount() {
        User user = new User(1L, "test", "test", "test@gmail.com", "test", "test");
        User user2 = new User(2L, "test2", "test2", "test2@gmail.com", "test2", "test2");

        Long userId = userService.add(user);
        Long userId2 = userService.add(user2);

        AccountDto accountDto = new AccountDto("test", 2000.0);
        AccountDto accountDto2 = new AccountDto("test2", 1000.0);

        Long accountId = accountService.add(userId, accountDto);
        Long accountId2 = accountService2.add(userId2, accountDto2);

        List<Account> accounts = new ArrayList<>();
        accounts.add(accountService.getById(accountId));
        accounts.add(accountService2.getById(accountId2));
        return accounts;
    }

    @Test
    public void test1AllTest() {
        assertEquals(moneyTransferService.getAll().size(), 0);
    }

    @Test
    public void test2AddAndGetByIdAndExceptionTest() throws NoSuchFieldException, IllegalAccessException {
        List<Account> accounts = addUserAndAccount();

        Account account = accounts.get(0);
        Account account2 = accounts.get(1);

        Double balance = account.getBalance();
        Double balance2 = account2.getBalance();

        Long accountNumber = account.getAccountNumber();
        Long accountNumber2 = account2.getAccountNumber();

        Double amount = 1000.0;

        moneyTransferService.performTransaction(accountNumber, accountNumber2, amount);

        assertEquals(accountService.getById(account.getId()).getBalance(), balance - amount, 0);
        assertEquals(accountService2.getById(account2.getId()).getBalance(), balance2 + amount, 0);

        Exception exception = assertThrows(InsufficientFundsException.class, () ->
                moneyTransferService.performTransaction(accountNumber, accountNumber2, amount + balance));
        assertEquals("Insufficient Funds.", exception.getMessage());

        Exception exception2 = assertThrows(MoneyTransferSameAccountException.class, () ->
                moneyTransferService.performTransaction(accountNumber, accountNumber, amount + balance));
        assertEquals("You can not transfer funds to the same account.", exception2.getMessage());
    }
}
