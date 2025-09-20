package ru.eliseev.charm.back.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ru.eliseev.charm.back.dto.Query;
import ru.eliseev.charm.pool.CustomDataSource;
import ru.eliseev.charm.pool.dto.CustomDataSourceConfig;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@UtilityClass
public class ConnectionManager {
    private static final String URL = ConfigFileUtils.get("app.datasource.url");
    private static final String USER = ConfigFileUtils.get("app.datasource.username");
    private static final String PASSWORD = ConfigFileUtils.get("app.datasource.password");
    private static final String DRIVER = ConfigFileUtils.get("app.datasource.driver");
    private static final String FETCH_SIZE_STR = ConfigFileUtils.get("app.datasource.fetch-size");
    public static final int FETCH_SIZE = Integer.parseInt(FETCH_SIZE_STR != null ? FETCH_SIZE_STR : "0");
    private static final String MAX_ROWS_STR = ConfigFileUtils.get("app.datasource.max-rows");
    public static final int MAX_ROWS = Integer.parseInt(MAX_ROWS_STR != null ? MAX_ROWS_STR : "1000");
    private static final String QUERY_TIMEOUT_STR = ConfigFileUtils.get("app.datasource.query-timeout");
    public static final int QUERY_TIMEOUT = Integer.parseInt(QUERY_TIMEOUT_STR != null ? QUERY_TIMEOUT_STR : "10");
    private static final String POOL_SIZE_STR = ConfigFileUtils.get("app.datasource.pool.size");
    public static final int POOL_SIZE = Integer.parseInt(POOL_SIZE_STR != null ? POOL_SIZE_STR : "10");
    private static final String POOL_IMPL = ConfigFileUtils.get("app.datasource.pool.impl");
    public static final String DEFAULT_SORTED_COLUMN = "id";
    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final List<Integer> AVAILABLE_PAGE_SIZES = List.of(10, 20, 50, 100);
    private static DataSource dataSource;

    static {
        init();
    }

    @SneakyThrows
    private static void init() {
        Class.forName(DRIVER);
        if (!"hikari".equals(POOL_IMPL)) {
            CustomDataSourceConfig config = new CustomDataSourceConfig(URL, USER, PASSWORD, POOL_SIZE)
                .setFetchSize(FETCH_SIZE)
                .setMaxRows(MAX_ROWS)
                .setQueryTimeout(QUERY_TIMEOUT);

            dataSource = new CustomDataSource(config);
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USER);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(POOL_SIZE);
            config.setMinimumIdle(5);
            config.setConnectionTimeout(10000);
            config.setIdleTimeout(60000);
            config.setMaxLifetime(1800000);
            config.setConnectionTestQuery("SELECT 1");

            dataSource = new HikariDataSource(config);
        }
    }

    @SneakyThrows
    public static Connection getConnection() {
        return dataSource.getConnection();
    }

    public static PreparedStatement getPreparedStmt(Connection conn, Query query) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query.sql());
        List<Object> args = query.args();
        for (int i = 0; i < args.size(); i++) {
            stmt.setObject(i + 1, args.get(i));
        }
        return stmt;
    }

    @SneakyThrows
    public static void closeDataSource() {
        ((Closeable) dataSource).close();
    }
}
