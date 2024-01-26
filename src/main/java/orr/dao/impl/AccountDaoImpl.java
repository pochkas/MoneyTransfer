package orr.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import org.jooq.DSLContext;
import orr.adapter.LocalDateTypeAdapter;
import orr.dao.AccountDao;
import orr.dto.AccountDto;
import orr.exception.AccountNotFoundException;
import orr.models.Account;
import orr.models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static generated.tables.Account.ACCOUNT;
import static generated.tables.User.USER;

public class AccountDaoImpl implements AccountDao {

    private final DSLContext context;

    @Inject
    public AccountDaoImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public Collection<Account> getAll() {
        return context.select().from(ACCOUNT).fetchInto(Account.class);
    }

    @Override
    public Optional<Account> findById(Long id) {
        Account account = context.select().from(ACCOUNT).where(ACCOUNT.ID.eq(id)).fetchOneInto(Account.class);
        return Optional.ofNullable(account);
    }

    @Override
    public Account getById(Long id) {
        return findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public Account update(Long id, AccountDto accountDto) {

        context.update(ACCOUNT)
                .set(ACCOUNT.BALANCE, accountDto.getBalance())
                .where(ACCOUNT.ID.eq(id))
                .execute();

        return getById(id);
    }
    @Override
    public Account add(Long userId, Account account) {
        long uniqueLong = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        context.insertInto(ACCOUNT, ACCOUNT.ACCOUNTNUMBER, ACCOUNT.BALANCE, ACCOUNT.USERID)
                .values(uniqueLong, account.getBalance(), userId).execute();
        return account;
    }

    @Override
    public User getByUserId(Long userId) {
        return context.select().from(USER).where(ACCOUNT.USERID.eq(userId)).fetchOneInto(User.class);
    }

    @Override
    public void delete(Long id) {
        context.delete(ACCOUNT).where(ACCOUNT.ID.eq(id)).execute();
    }
}
