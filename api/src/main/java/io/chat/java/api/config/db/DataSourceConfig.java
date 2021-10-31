package io.chat.java.api.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.chat.java.api.config.security.PropertiesDbLoader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"io.chat.java.api.entity"},
    entityManagerFactoryRef = DbConstants.CHAT_ENTITY_MANAGER_FACTORY,
    transactionManagerRef = DbConstants.CHAT_TRANSACTION_MANAGER
)
@EnableTransactionManagement
public class DataSourceConfig {

    final PropertiesDbLoader propertiesLoader;

    public DataSourceConfig(PropertiesDbLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    @Bean(name = "mysqlDataSource")
    public DataSource dataSource() throws Exception {
        HikariConfig hikariConfig = new HikariConfig(propertiesLoader.getHikari());
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        dataSource.setLeakDetectionThreshold(Long.parseLong(propertiesLoader.getTransction().getProperty("timeout")));
        return new ClosableLazyConnectionDataSourceProxy(dataSource);
    }

    @Primary
    @Bean(name = DbConstants.CHAT_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("mysqlDataSource") DataSource dataSource) {
        EnableJpaRepositories enableJpaRepositories = this.getClass().getAnnotation(EnableJpaRepositories.class);
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        entityManagerFactoryBean.setJpaProperties(propertiesLoader.getJpa());
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceUnitName(DbConstants.CHAT_PERSISTENCE_UNIT_NAME);
        entityManagerFactoryBean.setPackagesToScan(enableJpaRepositories.basePackages());
        return entityManagerFactoryBean;
    }

    @Primary
    @Bean(name = DbConstants.CHAT_TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager(@Qualifier(DbConstants.CHAT_ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory.getObject());
        txManager.setDefaultTimeout(Integer.parseInt(propertiesLoader.getTransction().getProperty("timeout")));
        txManager.afterPropertiesSet();
        return txManager;
    }

}
