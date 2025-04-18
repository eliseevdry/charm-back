package ru.eliseev.charm.back.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.utils.ConnectionManager;

@Slf4j
public class LikeDao {
	//language=POSTGRES-PSQL
	private static final String SELECT = """
						SELECT l.*
						FROM "like" l
						WHERE l.from_profile = ? AND l.to_profile = ?
			""";
	//language=POSTGRES-PSQL
	private static final String INSERT = """
						INSERT INTO "like" (from_profile, to_profile, "like", match)
						VALUES (?, ?, ?, ?)
						ON CONFLICT (from_profile, to_profile)
						DO UPDATE SET "like" = ?, match = ?
			""";
	private static final LikeDao INSTANCE = new LikeDao();

	@SneakyThrows
	public static LikeDao getInstance() {
		return INSTANCE;
	}

	@SneakyThrows
	public void like(Long fromProfileId, Long toProfileId, boolean isLike) {
		Connection conn = null;
		PreparedStatement selectStmt = null;
		PreparedStatement insertStmt = null;
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);

			selectStmt = conn.prepareStatement(SELECT);
			insertStmt = conn.prepareStatement(INSERT);

			boolean isMatch = false;
			if (isLike) {
				selectStmt.setLong(1, toProfileId);
				selectStmt.setLong(2, fromProfileId);
				ResultSet rs = selectStmt.executeQuery();
				if (rs.next()) {
					isMatch = rs.getBoolean("like");
				}
			}
			if (isMatch) {
				fillInsert(insertStmt, fromProfileId, toProfileId, true, true);
				insertStmt.addBatch();
				fillInsert(insertStmt, toProfileId, fromProfileId, true, true);
				insertStmt.addBatch();
				insertStmt.executeBatch();
			} else {
				fillInsert(insertStmt, fromProfileId, toProfileId, isLike, false);
				insertStmt.executeUpdate();
			}

			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				conn.rollback();
			}
			throw e;
		} finally {
			// rs.close()
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		}
	}

	private void fillInsert(PreparedStatement insertStmt,
							Long fromProfileId,
							Long toProfileId,
							boolean isLike,
							boolean isMatch) throws SQLException {
		insertStmt.setLong(1, fromProfileId);
		insertStmt.setLong(2, toProfileId);
		insertStmt.setBoolean(3, isLike);
		insertStmt.setBoolean(4, isMatch);
		insertStmt.setBoolean(5, isLike);
		insertStmt.setBoolean(6, isMatch);
	}
}
