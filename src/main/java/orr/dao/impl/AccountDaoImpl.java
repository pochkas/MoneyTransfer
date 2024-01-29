package orr.dao.impl;

import com.google.inject.Inject;
import org.jooq.DSLContext;
import orr.dao.AccountDao;
import orr.dto.AccountDto;
import orr.exception.AccountNotFoundException;
import orr.models.Account;
import orr.utils.UUIDSupplier;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static generated.tables.Account.ACCOUNT;

public class AccountDaoImpl implements AccountDao {

    private final DSLContext context;
    private final UUIDSupplier uuidSupplier;

    @Inject
    public AccountDaoImpl(DSLContext context, UUIDSupplier uuidSupplier) {
        this.context = context;
        this.uuidSupplier=uuidSupplier;
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
        return findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    public Optional<Account> findByAccountNumber(Long accountNumber) {
        Account account = context.select().from(ACCOUNT).where(ACCOUNT.ACCOUNTNUMBER.eq(accountNumber)).fetchOneInto(Account.class);
        return Optional.ofNullable(account);
    }

    @Override
    public Account getByAccountNumber(Long accountNumber) {
        return findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
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
    public Long add(Long userId, AccountDto accountDto) {
        long uniqueLong = uuidSupplier.create().getMostSignificantBits() & Long.MAX_VALUE;
        Account account = context.insertInto(ACCOUNT, ACCOUNT.ACCOUNTNUMBER, ACCOUNT.BALANCE, ACCOUNT.USERID)
                .values(uniqueLong, accountDto.getBalance(), userId).returning(ACCOUNT.ID).fetchOneInto(Account.class);
        return account.getId();
    }

    @Override
    public void delete(Long id) {
        context.delete(ACCOUNT).where(ACCOUNT.ID.eq(id)).execute();
    }
}
