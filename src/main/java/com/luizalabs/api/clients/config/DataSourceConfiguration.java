package com.luizalabs.api.clients.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class DataSourceConfiguration {

  private final String url;
  private final String username;
  private final String password;
  private final String showSql;
  private final int minimumIdle;
  private final int maximumPoolSize;

  public abstract DataSource dataSource() throws SQLException;

  public abstract LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder,
      DataSource dataSource);

  public abstract JpaTransactionManager transactionManager(
      EntityManagerFactory entityManagerFactory);

  protected DataSource buildDatasource() {
    var hikariDataSource = DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .url(url)
        .username(username)
        .password(password)
        .build();

    hikariDataSource.setMinimumIdle(minimumIdle);
    hikariDataSource.setMaximumPoolSize(maximumPoolSize);

    return hikariDataSource;
  }

  protected Map<String, String> buildProperties(String dialect) {

    var properties = new HashMap<String, String>();

    if (!showSql.isEmpty()) {
      properties.put("hibernate.show_sql", showSql);
    }

    properties.put("hibernate.hbm2ddl.auto", "none");
    properties.put("hibernate.dialect", dialect);

    return properties;

  }

}
