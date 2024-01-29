package orr.service.impl;

import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialectCategory;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runners.MethodSorters;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
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

import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountServiceImplTest {

    @ClassRule
    public static PostgreSQLContainer postgresContainer = (PostgreSQLContainer) new PostgreSQLContainer().withInitScript("init_script.sql");

    public DSLContext dslContext(Configuration configuration) {
        return DSL.using(configuration);
    }

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }


    private Configuration providedConfiguration() {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(postgresContainer.getJdbcUrl());
        dataSource.setUser(postgresContainer.getUsername());
        dataSource.setPassword(postgresContainer.getPassword());

        ConnectionProvider cp = new DataSourceConnectionProvider(dataSource);
        Configuration configuration = new DefaultConfiguration()
                .set(SQLDialectCategory.POSTGRES.dialects().stream().findFirst().get())
                .set(cp)
                .set(new ThreadLocalTransactionProvider(cp, true));
        SQLDialectCategory.POSTGRES.dialects().stream().findFirst().get();
        return configuration;
    }

    private final UUID uuid = UUID.randomUUID();

    private UUIDSupplier uuidSupplier = new UUIDSupplier() {
        @Override
        public UUID create() {
            return uuid;
        }
    };
    private AccountService service = new AccountServiceImpl(new AccountDaoImpl(dslContext(providedConfiguration()), uuidSupplier));
    private UserService userService = new UserServiceImpl(new UserDaoImpl(dslContext(providedConfiguration())));

    @Test
    @BeforeAll
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
    public void test2AddAndGetByIdTest() throws NoSuchFieldException, IllegalAccessException {
        Long userId = 1L;
        Long uniqueLong = uuid.getMostSignificantBits() & Long.MAX_VALUE;
        AccountDto accountDto = new AccountDto("a", 2000.0);
        Long id = service.add(userId, accountDto);
        Account account = service.getById(id);
        assertEquals(account.getAccountNumber(), uniqueLong);
    }

    @Test
    public void test4delete() {
        Long userId = 1L;
        Long id = 2L;
        Long uniqueLong = uuid.getMostSignificantBits() & Long.MAX_VALUE;

        Exception exception = assertThrows(AccountNotFoundException.class, () ->
                service.getById(id));
        assertEquals("No account with id " + id + " found.", exception.getMessage());

        AccountDto accountDto = new AccountDto("a", 2000.0);
        Long newAccountId = service.add(userId, accountDto);
        assertEquals(service.getById(id), service.getById(newAccountId));
        service.delete(id);

        Exception exception2 = assertThrows(AccountNotFoundException.class, () ->
                service.getById(id));
        assertEquals("No account with id " + id + " found.", exception.getMessage());
    }
}
