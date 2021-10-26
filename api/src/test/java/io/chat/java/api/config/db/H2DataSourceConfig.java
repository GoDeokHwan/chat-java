package io.chat.java.api.config.db;

import io.chat.java.api.config.TestProfileCondition;
import io.chat.java.api.config.db.DbConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Optional;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Conditional(TestProfileCondition.class)
@TestConfiguration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"io.chat.java.api.entity"},
        entityManagerFactoryRef = "chatEntityManagerFactoryTest",
        transactionManagerRef = "chatTransactionManagerTest"
)
@EnableTransactionManagement
public class H2DataSourceConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("getCurrentAuditor");
    }

    @Bean(name = "h2DataSource")
    public DataSource dataSource() throws Exception {
        return getEmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("schema.sql")
                .addScript("init-data.sql")
                .build();
    }

    @Bean(name = "chatEntityManagerFactoryTest")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("h2DataSource") DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("chatTest");
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties());
        localContainerEntityManagerFactoryBean.setPackagesToScan("io.chat.java.api.entity");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "chatTransactionManagerTest")
    public PlatformTransactionManager transactionManager(@Qualifier("chatEntityManagerFactoryTest") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory.getObject());
        txManager.afterPropertiesSet();
        return txManager;
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(DIALECT, "org.hibernate.dialect.H2Dialect");
        properties.setProperty(PHYSICAL_NAMING_STRATEGY, "io.chat.java.api.config.db.LowerUnderScoreNamingStrategy");
        properties.setProperty(FORMAT_SQL, "true");

        return properties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        return hibernateJpaVendorAdapter;
    }

    private EmbeddedDatabaseBuilder getEmbeddedDatabaseBuilder() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        databaseBuilder.setDataSourceFactory(new DataSourceFactory() {
            private final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

            @NonNull
            @Override
            public ConnectionProperties getConnectionProperties() {
                return new ConnectionProperties() {
                    @Override
                    public void setDriverClass(@NonNull Class<? extends Driver> driverClass) {
                        dataSource.setDriverClass(org.h2.Driver.class);
                    }
                    @Override
                    public void setUrl(@NonNull String url) {
                        dataSource.setUrl("jdbc:h2:mem:chatdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false");
                    }
                    @Override
                    public void setUsername(@NonNull String username) {
                        dataSource.setUsername("SA");
                    }
                    @Override
                    public void setPassword(@NonNull String password) {
                        dataSource.setPassword("");
                    }
                };
            }
            @NonNull
            @Override
            public DataSource getDataSource() {
                return dataSource;
            }
        });
        return databaseBuilder;
    }
}

