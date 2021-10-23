package io.chat.java.api.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ClosableLazyConnectionDataSourceProxy extends LazyConnectionDataSourceProxy {

    public ClosableLazyConnectionDataSourceProxy(DataSource targetDataSource) {
        super(targetDataSource);
    }

    public void close() throws SQLException {
        HikariDataSource target = (HikariDataSource) super.getTargetDataSource();
        target.close();
    }

}
