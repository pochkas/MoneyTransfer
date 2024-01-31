package orr.dao.impl;

import com.google.inject.Inject;
import org.jooq.DSLContext;
import orr.dao.MoneyTransferDao;
import orr.dto.MoneyTransferDto;
import orr.exception.InsufficientFundsException;
import orr.exception.MoneyTransferException;
import orr.exception.MoneyTransferSameAccountException;
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
        MoneyTransfer moneyTransfer = context.select().from(MONEYTRANSFER).where(MONEYTRANSFER.ID.eq(id)).fetchOneInto(MoneyTransfer.class);
        return Optional.ofNullable(moneyTransfer);
    }

    @Override
    public MoneyTransfer getById(Long id) {
        return findById(id).orElseThrow(()-> new MoneyTransferException(id));
    }

    @Override
    public Long add(MoneyTransfer moneyTransfer) {
        MoneyTransfer newMoneyTransfer = context.insertInto(MONEYTRANSFER, MONEYTRANSFER.FROMACCOUNTNUMBER, MONEYTRANSFER.TOACCOUNTNUMBER, MONEYTRANSFER.AMOUNT)
                .values(moneyTransfer.getFromAccountNumber(), moneyTransfer.getToAccountNumber(), moneyTransfer.getAmount()).returning(MONEYTRANSFER.ID).fetchOneInto(MoneyTransfer.class);
        return newMoneyTransfer.getId();
    }

    @Override
    public void performTransaction(MoneyTransferDto moneyTransferDto) {

        Account accountFrom = context.select().from(ACCOUNT).where(ACCOUNT.ACCOUNTNUMBER.eq(moneyTransferDto.getFromAccountNumber())).fetchOneInto(Account.class);
        Account accountTo = context.select().from(ACCOUNT).where(ACCOUNT.ACCOUNTNUMBER.eq(moneyTransferDto.getToAccountNumber())).fetchOneInto(Account.class);
        if(moneyTransferDto.getFromAccountNumber().equals(moneyTransferDto.getToAccountNumber())){
            throw new MoneyTransferSameAccountException();
        }

        try {
            context.transaction(transaction -> {

                transaction.dsl()
                        .update(ACCOUNT)
                        .set(ACCOUNT.BALANCE, ACCOUNT.BALANCE.minus(moneyTransferDto.getAmount()))
                        .where(ACCOUNT.ACCOUNTNUMBER.eq(moneyTransferDto.getFromAccountNumber()))
                        .execute();

                transaction.dsl()
                        .update(ACCOUNT)
                        .set(ACCOUNT.BALANCE, ACCOUNT.BALANCE.plus(moneyTransferDto.getAmount()))
                        .where(ACCOUNT.ACCOUNTNUMBER.eq(moneyTransferDto.getToAccountNumber()))
                        .execute();

                transaction.dsl()
                        .insertInto(MONEYTRANSFER, MONEYTRANSFER.FROMACCOUNTNUMBER, MONEYTRANSFER.TOACCOUNTNUMBER, MONEYTRANSFER.AMOUNT)
                        .values(moneyTransferDto.getFromAccountNumber(), moneyTransferDto.getToAccountNumber(), moneyTransferDto.getAmount()).returning(MONEYTRANSFER.ID).fetchOneInto(MoneyTransfer.class);
            });
        } catch (Exception exception) {
            throw new InsufficientFundsException();
        }
    }
}
