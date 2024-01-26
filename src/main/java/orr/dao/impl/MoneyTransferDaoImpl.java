package orr.dao.impl;

import com.google.inject.Inject;
import org.jooq.DSLContext;
import orr.dao.MoneyTransferDao;
import orr.models.Account;
import orr.models.MoneyTransfer;

import java.util.Collection;
import java.util.Optional;

import static generated.tables.Account.ACCOUNT;
import static generated.tables.Moneytransfer.MONEYTRANSFER;

public class MoneyTransferDaoImpl implements MoneyTransferDao {

    private final DSLContext context;

    @Inject
    public MoneyTransferDaoImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public Collection<MoneyTransfer> getAll() {
        return context.select().from(MONEYTRANSFER).fetchInto(MoneyTransfer.class);
    }

    @Override
    public Optional<MoneyTransfer> findById(Long id) {
        MoneyTransfer moneyTransfer = context.select().from(MONEYTRANSFER).fetchOneInto(MoneyTransfer.class);
        return Optional.ofNullable(moneyTransfer);
    }

    @Override
    public MoneyTransfer getById(Long id) {
        return findById(id).get();
    }

    @Override
    public MoneyTransfer add(MoneyTransfer moneyTransfer) {
        context.insertInto(MONEYTRANSFER, MONEYTRANSFER.FROMACCOUNTNUMBER, MONEYTRANSFER.TOACCOUNTNUMBER, MONEYTRANSFER.AMOUNT)
                .values(moneyTransfer.getFromAccountNumber(), moneyTransfer.getToAccountNumber(), moneyTransfer.getAmount()).execute();
        return moneyTransfer;
    }

    @Override
    public MoneyTransfer performTransaction(Long fromAccountNumber, Long toAccountNumber, double amount) {

        Account accountFrom = context.select().from(ACCOUNT).where(ACCOUNT.ACCOUNTNUMBER.eq(fromAccountNumber)).fetchOneInto(Account.class);
        Account accountTo = context.select().from(ACCOUNT).where(ACCOUNT.ACCOUNTNUMBER.eq(toAccountNumber)).fetchOneInto(Account.class);
        assert accountFrom != null;

        context.transaction(transaction -> {

            transaction.dsl()
                    .update(ACCOUNT)
                    .set(ACCOUNT.BALANCE, accountFrom.getBalance() - amount)
                    .where(ACCOUNT.ACCOUNTNUMBER.eq(fromAccountNumber))
                    .execute();

            assert accountTo != null;
            transaction.dsl()
                    .update(ACCOUNT)
                    .set(ACCOUNT.BALANCE, accountTo.getBalance() + amount)
                    .where(ACCOUNT.ACCOUNTNUMBER.eq(toAccountNumber))
                    .execute();
        });
        MoneyTransfer moneyTransfer = new MoneyTransfer(fromAccountNumber, toAccountNumber, amount);
        add(moneyTransfer);
        return moneyTransfer;
    }
}
