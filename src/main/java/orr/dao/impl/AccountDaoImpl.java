package orr.dao.impl;

import com.google.inject.Inject;
import org.jooq.DSLContext;
import orr.dao.AccountDao;
import orr.dto.AccountDto;
import orr.exception.AccountNotFoundException;
import orr.models.Account;

import java.util.Collection;
import java.util.Optional;

import static generated.tables.Account.ACCOUNT;

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
                .set(ACCOUNT.ACCOUNTHOLDERNAME, accountDto.getAccountHolderName())
                .set(ACCOUNT.BALANCE, accountDto.getBalance())
                .where(ACCOUNT.ID.eq(id))
                .execute();

        return getById(id);
    }

    @Override
    public void delete(Long id) {
        context.delete(ACCOUNT).where(ACCOUNT.ID.eq(id)).execute();
    }
}
