/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.User;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.user.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.user.firstName</code>.
     */
    public void setFirstname(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user.firstName</code>.
     */
    public String getFirstname() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.user.lastName</code>.
     */
    public void setLastname(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.user.lastName</code>.
     */
    public String getLastname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.user.email</code>.
     */
    public void setEmail(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.user.email</code>.
     */
    public String getEmail() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.user.username</code>.
     */
    public void setUsername(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.user.username</code>.
     */
    public String getUsername() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.user.password</code>.
     */
    public void setPassword(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.user.password</code>.
     */
    public String getPassword() {
        return (String) get(5);
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
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Long id, String firstname, String lastname, String email, String username, String password) {
        super(User.USER);

        setId(id);
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setUsername(username);
        setPassword(password);
        resetChangedOnNotNull();
    }
}