package com.ymu.framework.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryYmuDb",
        transactionManagerRef = "transactionManagerYmuDb",
        basePackages = {"com.ymu.framework.sample.dao"}) //设置Repository所在位置
public class YmuDbConfig {

    @Autowired
    @Qualifier("ymuDbDataSource")
    private DataSource ymuDbDataSource;

    @Primary
    @Bean(name = "entityManagerYmuDb")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryYmuDb(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryYmuDb")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryYmuDb(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(ymuDbDataSource)
                .properties(getVendorProperties(ymuDbDataSource))
                .packages("com.ymu.framework.sample.domain") //设置实体类所在位置
                .persistenceUnit("ymuDb") //如果是多个数据库，这里要区分，只有一个数据库可以省略该配置
                .build();
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        JpaProperties jpaProperties = new JpaProperties();
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryYmuDb(builder).getObject());
    }

}