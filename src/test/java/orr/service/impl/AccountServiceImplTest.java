package orr.service.impl;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import orr.dao.impl.AccountDaoImpl;
import orr.dao.impl.UserDaoImpl;
import orr.dto.AccountDto;
import orr.exception.AccountNotFoundException;
import orr.models.Account;
import orr.models.User;
import orr.service.AccountService;
import orr.service.Impl.AccountServiceImpl;
import orr.service.Impl.UserServiceImpl;
import orr.service.UserService;
import orr.utils.UUIDSupplier;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountServiceImplTest extends PostgreSQLContainerAbstract{

    private final UUID uuid = UUID.randomUUID();

    private UUIDSupplier uuidSupplier = new UUIDSupplier() {
        @Override
        public UUID create() {
            return uuid;
        }
    };
    private AccountService service = new AccountServiceImpl(new AccountDaoImpl(dslContext(providedConfiguration()), uuidSupplier));
    private UserService userService = new UserServiceImpl(new UserDaoImpl(dslContext(providedConfiguration())));

    public void addUser() {
        User user = new User(1L, "test", "test", "test@gmail.com", "test", "test");
        Long id = userService.add(user);
        assertEquals(user, userService.getById(id));
    }

    @Test
    public void test1AllTest() {
        assertEquals(service.getAll().size(), 0);
    }

    @Test
    public void test2AddAndGetByIdAndUpdateTest() throws NoSuchFieldException, IllegalAccessException {
        addUser();
        Long userId = 1L;
        Long uniqueLong = uuid.getMostSignificantBits() & Long.MAX_VALUE;
        AccountDto accountDto = new AccountDto("a", 2000.0);
        Long id = service.add(userId, accountDto);
        Account account = service.getById(id);
        assertEquals(account.getAccountNumber(), uniqueLong);
        Account updatedAccount = service.update(id, new AccountDto("newTest", 5555.5));
        assertEquals(service.getById(id), updatedAccount);
    }

    @Test
    public void test4delete() {
        Long userId = 1L;
        Long id = 2L;

        Exception exception = assertThrows(AccountNotFoundException.class, () ->
                service.getById(id));
        assertEquals("No account with id " + id + " found.", exception.getMessage());

        AccountDto accountDto = new AccountDto("a", 2000.0);
        Long newAccountId = service.add(userId, accountDto);
        assertEquals(service.getById(id), service.getById(newAccountId));
        service.delete(id);

        Exception exception2 = assertThrows(AccountNotFoundException.class, () ->
                service.getById(id));
        assertEquals("No account with id " + id + " found.", exception2.getMessage());
    }
}
