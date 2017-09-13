package com.ymu.framework.sample;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "ymuDbDataSource")
    @Qualifier("ymuDbDataSource")
    @ConfigurationProperties(prefix="spring.datasource.ymuDb")
    public DataSource ymuDbDataSource() {
        return DataSourceBuilder.create().build();
    }
}
