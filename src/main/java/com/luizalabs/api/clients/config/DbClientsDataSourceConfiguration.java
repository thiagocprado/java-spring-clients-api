package com.luizalabs.api.clients.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "com.luizalabs.api.clients.repository",
        entityManagerFactoryRef = "entityManagerFactoryDbClients",
        transactionManagerRef = "transactionManagerDbClients")
public class DbClientsDataSourceConfiguration extends DataSourceConfiguration {

    private static final String[] MODEL_PACKAGES = {"com.luizalabs.api.clients.entity"};

    @Autowired
    DbClientsDataSourceConfiguration(
            @Value("${jdbc.db_clients.url}") String url,
            @Value("${jdbc.db_clients.username}") String username,
            @Value("${jdbc.db_clients.password}") String password,
            @Value("${jdbc.db_clients.showSql}") String showSql,
            @Value("${jdbc.db_clients.minimumIdle}") int minimumIdle,
            @Value("${jdbc.db_clients.maximumPoolSize}") int maximumPoolSize) {
        super(url, username, password, showSql, minimumIdle, maximumPoolSize);
    }

    @Bean("dataSourceClients")
    @Override
    @Primary
    public DataSource dataSource() {
        return buildDatasource();
    }

    @Bean("entityManagerFactoryDbClients")
    @Override
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSourceClients") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages(MODEL_PACKAGES)
                .properties(jpaProperties())
                .build();
    }

    @Bean("transactionManagerDbClients")
    @Override
    @Primary
    public JpaTransactionManager transactionManager(
            @Qualifier("entityManagerFactoryDbClients") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, String> jpaProperties() {
        return buildProperties("org.hibernate.dialect.MySQLDialect");
    }
}
