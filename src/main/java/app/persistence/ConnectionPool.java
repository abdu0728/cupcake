package app.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static HikariDataSource dataSource;

    static {
        try (InputStream in = ConnectionPool.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            Properties p = new Properties();
            if (in == null) {
                throw new RuntimeException("config.properties ikke fundet");
            }
            p.load(in);

            String url = p.getProperty("db.url");
            String username = p.getProperty("db.user");
            String password = p.getProperty("db.password");
            String driver = p.getProperty("db.driver", "org.postgresql.Driver");

            HikariConfig cfg = new HikariConfig(p);
            cfg.setJdbcUrl(url);
            cfg.setUsername(username);
            cfg.setPassword(password);
            cfg.setDriverClassName(driver);
            cfg.setMaximumPoolSize(5);
            cfg.setPoolName("CupcakePool");

            dataSource = new HikariDataSource(cfg);
        } catch (Exception e) {
            throw new RuntimeException("Fejl ved oprettelse af ConnectionPool", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) dataSource.close();
    }
}