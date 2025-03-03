package ru.eliseev.charm.back.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.experimental.UtilityClass;
import ru.eliseev.charm.back.dto.Query;

@UtilityClass
public class ConnectionManager {
	private static final String URL = ConfigFileUtils.get("app.datasource.url");
	private static final String USER = ConfigFileUtils.get("app.datasource.username");
	private static final String PASSWORD = ConfigFileUtils.get("app.datasource.password");
	private static final String DRIVER = ConfigFileUtils.get("app.datasource.driver");
	private static final String FETCH_SIZE_STR = ConfigFileUtils.get("app.datasource.fetch-size");
	public static final int FETCH_SIZE = Integer.parseInt(FETCH_SIZE_STR != null ? FETCH_SIZE_STR : "100");
	private static final String MAX_ROWS_STR = ConfigFileUtils.get("app.datasource.max-rows");
	public static final int MAX_ROWS = Integer.parseInt(MAX_ROWS_STR != null ? MAX_ROWS_STR : "1000");
	private static final String QUERY_TIMEOUT_STR = ConfigFileUtils.get("app.datasource.query-timeout");
	public static final int QUERY_TIMEOUT = Integer.parseInt(QUERY_TIMEOUT_STR != null ? QUERY_TIMEOUT_STR : "10");
	public static final String DEFAULT_SORTED_COLUMN = "id";
	public static final Integer DEFAULT_PAGE = 1;
	public static final Integer DEFAULT_PAGE_SIZE = 10;
	public static final List<Integer> AVAILABLE_PAGE_SIZES = List.of(10, 20, 50, 100);

	static {
		if (DRIVER != null) {
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static PreparedStatement getPreparedStmt(Connection conn, Query query) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query.sql());
		List<Object> args = query.args();
		for (int i = 0; i < args.size(); i++) {
			stmt.setObject(i + 1, args.get(i));
		}
		return stmt;
	}
}
