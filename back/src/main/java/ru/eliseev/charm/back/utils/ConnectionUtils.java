package ru.eliseev.charm.back.utils;

import lombok.experimental.UtilityClass;
import ru.eliseev.charm.back.dto.Query;
import ru.eliseev.charm.utils.ConfigFileUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@UtilityClass
public class ConnectionUtils {
    private static final String FETCH_SIZE_STR = ConfigFileUtils.get("app.datasource.fetch-size");
    private static final int FETCH_SIZE = Integer.parseInt(FETCH_SIZE_STR != null ? FETCH_SIZE_STR : "0");
    private static final String MAX_ROWS_STR = ConfigFileUtils.get("app.datasource.max-rows");
    private static final int MAX_ROWS = Integer.parseInt(MAX_ROWS_STR != null ? MAX_ROWS_STR : "1000");
    private static final String QUERY_TIMEOUT_STR = ConfigFileUtils.get("app.datasource.query-timeout");
    private static final int QUERY_TIMEOUT = Integer.parseInt(QUERY_TIMEOUT_STR != null ? QUERY_TIMEOUT_STR : "10");
    public static final String DEFAULT_SORTED_COLUMN = "id";
    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final List<Integer> AVAILABLE_PAGE_SIZES = List.of(10, 20, 50, 100);

    public static PreparedStatement getPreparedStmt(Connection conn, Query query) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query.sql());
        List<Object> args = query.args();
        for (int i = 0; i < args.size(); i++) {
            stmt.setObject(i + 1, args.get(i));
        }
        stmt.setFetchSize(FETCH_SIZE);
        stmt.setMaxRows(MAX_ROWS);
        stmt.setQueryTimeout(QUERY_TIMEOUT);
        return stmt;
    }
}
