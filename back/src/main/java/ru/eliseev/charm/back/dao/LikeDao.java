package ru.eliseev.charm.back.dao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Setter
@Repository
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

    @Autowired
    private DataSource dataSource;

	public void like(Long fromProfileId, Long toProfileId, boolean isLike) {
        try (Connection conn = dataSource.getConnection();
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