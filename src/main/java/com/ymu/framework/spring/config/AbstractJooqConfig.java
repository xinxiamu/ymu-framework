package com.ymu.framework.spring.config;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

public abstract class AbstractJooqConfig {

    public abstract DataSource dataSource();

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource()));
    }

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration config = new DefaultConfiguration();
        config.setSQLDialect(SQLDialect.MYSQL);
        config.setConnectionProvider(connectionProvider());
//        config.setTransactionProvider(ymuFileDataSource);//事务

        DefaultExecuteListenerProvider defaultExecuteListenerProvider = new DefaultExecuteListenerProvider(new ExceptionTranslator());
        config.setExecuteListenerProvider(defaultExecuteListenerProvider);

        return config;
    }

    @Bean
    public DefaultDSLContext jooqDsl() {
        DefaultDSLContext dsl = new DefaultDSLContext(configuration());
        return dsl;
    }
}
