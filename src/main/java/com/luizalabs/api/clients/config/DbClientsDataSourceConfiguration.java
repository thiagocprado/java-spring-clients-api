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
            @Value("${jdbc.dbClients.url}") String url,
            @Value("${jdbc.dbClients.username}") String username,
            @Value("${jdbc.dbClients.password}") String password) {
        super(url, username, password);
    }

    @Bean("dataSourceClients")
    @Override
    public DataSource dataSource() {
        return buildDatasource();
    }

    @Bean("entityManagerFactoryDbClients")
    @Override
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
    public JpaTransactionManager transactionManager(
            @Qualifier("entityManagerFactoryDbClients") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, String> jpaProperties() {
        return buildProperties("org.hibernate.dialect.MySQLDialect");
    }
}
