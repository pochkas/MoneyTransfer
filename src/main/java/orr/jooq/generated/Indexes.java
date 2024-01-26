/*
 * This file is generated by jOOQ.
 */
package generated;


import generated.tables.Account;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index FKI_USERID_FKEY = Internal.createIndex(DSL.name("fki_userId_fkey"), Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.USERID }, false);
}
