package orr.service.impl;

import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialectCategory;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class PostgreSQLContainerAbstract {

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

    Configuration providedConfiguration() {

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
}
