package ru.eliseev.charm.pool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.pool.dto.CustomDataSourceConfig;
import ru.eliseev.charm.pool.model.ProxyConnection;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

@Slf4j
public class CustomDataSource implements DataSource, Closeable {

    private final BlockingQueue<Connection> pool;
    private final List<Connection> sourceConnections;

    @SneakyThrows
    public CustomDataSource(CustomDataSourceConfig config) {
        pool = new ArrayBlockingQueue<>(config.getPoolSize());
        sourceConnections = new ArrayList<>(config.getPoolSize());
        for (int i = 0; i < config.getPoolSize(); i++) {
            Connection connection = DriverManager.getConnection(config.getJdbcUrl(), config.getUsername(), config.getPassword());
            sourceConnections.add(connection);
            ProxyConnection proxyConnection = new ProxyConnection(connection, pool)
                .setFetchSize(config.getFetchSize())
                .setMaxRows(config.getMaxRows())
                .setQueryTimeout(config.getQueryTimeout());
            pool.add(proxyConnection);
        }
        log.info("CustomCP init");
    }

    @SneakyThrows
    @Override
    public Connection getConnection() throws SQLException {
        return pool.take();
    }

    @SneakyThrows
    @Override
    public void close() throws IOException {
        for (Connection sourceConnection : sourceConnections) {
            sourceConnection.close();
        }
        log.info("CustomCP was closed");
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        throw new SQLException("Wrapped DataSource is not an instance of " + iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }
}
