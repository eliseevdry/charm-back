package ru.eliseev.charm.back.utils;

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
import javax.sql.DataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomDataSource implements DataSource, Closeable {

	private final BlockingQueue<Connection> pool;
	private final List<Connection> sourceConnections;


	@SneakyThrows
	public CustomDataSource(CustomDataSourceConfig config) {
		pool = new ArrayBlockingQueue<>(config.poolSize());
		sourceConnections = new ArrayList<>(config.poolSize());
		for (int i = 0; i < config.poolSize(); i++) {
			Connection connection = DriverManager.getConnection(config.jdbcUrl(), config.username(), config.password());
			sourceConnections.add(connection);
			ProxyConnection proxyConnection = new ProxyConnection(connection, pool);
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
