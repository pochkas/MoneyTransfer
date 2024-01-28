package orr.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialectCategory;
import org.jooq.impl.*;
import org.postgresql.ds.PGSimpleDataSource;
import orr.MoneyTransferApplication;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MoneyTransferApplication.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    private ObjectMapper providedObjectMapper() {
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    public DSLContext dslContext(Configuration configuration) {
        return DSL.using(configuration);
    }

    @Provides
    @Singleton
    private Configuration providedConfiguration() throws IOException {

        Properties properties = getProperties();

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql:MoneyTransfer");
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));

        ConnectionProvider cp = new DataSourceConnectionProvider(dataSource);
        Configuration configuration = new DefaultConfiguration()
                .set(SQLDialectCategory.POSTGRES.dialects().stream().findFirst().get())
                .set(cp)
                .set(new ThreadLocalTransactionProvider(cp, true));
       SQLDialectCategory.POSTGRES.dialects().stream().findFirst().get();
        return configuration;
    }

    public Properties getProperties() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("secret.properties").getPath();
        Properties properties = new Properties();
        properties.load(new FileInputStream(rootPath));
        return properties;
    }
}
