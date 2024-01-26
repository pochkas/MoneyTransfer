/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.Account;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountRecord extends UpdatableRecordImpl<AccountRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.account.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.account.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.account.accountNumber</code>.
     */
    public void setAccountnumber(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.account.accountNumber</code>.
     */
    public Long getAccountnumber() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.account.balance</code>.
     */
    public void setBalance(Double value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.account.balance</code>.
     */
    public Double getBalance() {
        return (Double) get(2);
    }

    /**
     * Setter for <code>public.account.userId</code>.
     */
    public void setUserid(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.account.userId</code>.
     */
    public Long getUserid() {
        return (Long) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AccountRecord
     */
    public AccountRecord() {
        super(Account.ACCOUNT);
    }

    /**
     * Create a detached, initialised AccountRecord
     */
    public AccountRecord(Long id, Long accountnumber, Double balance, Long userid) {
        super(Account.ACCOUNT);

        setId(id);
        setAccountnumber(accountnumber);
        setBalance(balance);
        setUserid(userid);
        resetChangedOnNotNull();
    }
}
