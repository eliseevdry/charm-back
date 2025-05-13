package ru.eliseev.charm.back.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.utils.ConnectionManager;

@Slf4j
public class LikeDao {
	//language=POSTGRES-PSQL
	private static final String LIKE = """
			WITH has_match AS (
				UPDATE "like"
				SET match = true
				WHERE ? = true AND from_profile = ? AND to_profile = ? AND "like" = true
				RETURNING 1
			)
			INSERT INTO "like" (from_profile, to_profile, "like", match)
			SELECT ?, ?, ?, EXISTS (SELECT 1 FROM has_match)
			""";
	private static final LikeDao INSTANCE = new LikeDao();

	@SneakyThrows
	public static LikeDao getInstance() {
		return INSTANCE;
	}

	public void like(Long fromProfileId, Long toProfileId, boolean isLike) {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement updateStmt = conn.prepareStatement(LIKE)) {
			updateStmt.setBoolean(1, isLike);
			updateStmt.setLong(2, toProfileId);
			updateStmt.setLong(3, fromProfileId);
			updateStmt.setLong(4, fromProfileId);
			updateStmt.setLong(5, toProfileId);
			updateStmt.setBoolean(6, isLike);

			updateStmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}