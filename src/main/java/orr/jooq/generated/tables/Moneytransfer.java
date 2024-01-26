/*
 * This file is generated by jOOQ.
 */
package generated.tables;


import generated.Keys;
import generated.Public;
import generated.tables.records.MoneytransferRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Check;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Moneytransfer extends TableImpl<MoneytransferRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.moneyTransfer</code>
     */
    public static final Moneytransfer MONEYTRANSFER = new Moneytransfer();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MoneytransferRecord> getRecordType() {
        return MoneytransferRecord.class;
    }

    /**
     * The column <code>public.moneyTransfer.id</code>.
     */
    public final TableField<MoneytransferRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.moneyTransfer.fromAccountNumber</code>.
     */
    public final TableField<MoneytransferRecord, Long> FROMACCOUNTNUMBER = createField(DSL.name("fromAccountNumber"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.moneyTransfer.toAccountNumber</code>.
     */
    public final TableField<MoneytransferRecord, Long> TOACCOUNTNUMBER = createField(DSL.name("toAccountNumber"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.moneyTransfer.amount</code>.
     */
    public final TableField<MoneytransferRecord, Double> AMOUNT = createField(DSL.name("amount"), SQLDataType.DOUBLE, this, "");

    private Moneytransfer(Name alias, Table<MoneytransferRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Moneytransfer(Name alias, Table<MoneytransferRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.moneyTransfer</code> table reference
     */
    public Moneytransfer(String alias) {
        this(DSL.name(alias), MONEYTRANSFER);
    }

    /**
     * Create an aliased <code>public.moneyTransfer</code> table reference
     */
    public Moneytransfer(Name alias) {
        this(alias, MONEYTRANSFER);
    }

    /**
     * Create a <code>public.moneyTransfer</code> table reference
     */
    public Moneytransfer() {
        this(DSL.name("moneyTransfer"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<MoneytransferRecord, Long> getIdentity() {
        return (Identity<MoneytransferRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<MoneytransferRecord> getPrimaryKey() {
        return Keys.MONEYTRANSFER_PKEY;
    }

    @Override
    public List<Check<MoneytransferRecord>> getChecks() {
        return Arrays.asList(
            Internal.createCheck(this, DSL.name("amountCheck"), "((amount >= (0)::double precision))", true)
        );
    }

    @Override
    public Moneytransfer as(String alias) {
        return new Moneytransfer(DSL.name(alias), this);
    }

    @Override
    public Moneytransfer as(Name alias) {
        return new Moneytransfer(alias, this);
    }

    @Override
    public Moneytransfer as(Table<?> alias) {
        return new Moneytransfer(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Moneytransfer rename(String name) {
        return new Moneytransfer(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Moneytransfer rename(Name name) {
        return new Moneytransfer(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Moneytransfer rename(Table<?> name) {
        return new Moneytransfer(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Moneytransfer where(Condition condition) {
        return new Moneytransfer(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Moneytransfer where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Moneytransfer where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Moneytransfer where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Moneytransfer where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Moneytransfer where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Moneytransfer where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Moneytransfer where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Moneytransfer whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Moneytransfer whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
