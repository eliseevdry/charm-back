package ru.eliseev.charm.back.dao;

import static ru.eliseev.charm.back.utils.ConnectionManager.DEFAULT_PAGE;
import static ru.eliseev.charm.back.utils.ConnectionManager.DEFAULT_PAGE_SIZE;
import static ru.eliseev.charm.back.utils.ConnectionManager.FETCH_SIZE;
import static ru.eliseev.charm.back.utils.ConnectionManager.MAX_ROWS;
import static ru.eliseev.charm.back.utils.ConnectionManager.QUERY_TIMEOUT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.ProfileFilter;
import ru.eliseev.charm.back.dto.ProfileSelectQueryBuilder;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;
import ru.eliseev.charm.back.dto.ProfileUpdateQueryBuilder;
import ru.eliseev.charm.back.dto.Query;
import ru.eliseev.charm.back.mapper.ResultSetToProfileMapper;
import ru.eliseev.charm.back.mapper.ResultSetToProfileSimpleDtoMapper;
import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.back.utils.ConnectionManager;

@Slf4j
public class ProfileDao {

	//language=POSTGRES-PSQL
	public static final String INSERT = "INSERT INTO profile (email, password) VALUES (?, ?)";
	//language=POSTGRES-PSQL
	public static final String DELETE = "DELETE FROM profile WHERE id = ?";
	//language=POSTGRES-PSQL
	public static final String SUITABLE = """
			SELECT * FROM (
				WITH current_user_profile AS (
					SELECT id, gender, birth_date
					FROM profile
					WHERE id = ?
				)
				SELECT DISTINCT p.id, p.name, p.surname, p.birth_date, p.about, p.photo
				FROM profile p
					CROSS JOIN current_user_profile cup
					LEFT JOIN "like" l on p.id = l.to_profile
				WHERE (l.from_profile IS NULL OR l.from_profile != cup.id)
					AND p.id != cup.id
					AND p.gender = CASE WHEN cup.gender = 'MALE' THEN 'FEMALE' ELSE 'MALE' END
					AND p.birth_date BETWEEN (cup.birth_date - INTERVAL '5 years') AND (cup.birth_date + INTERVAL '5 years')
					AND p.status = 'ACTIVE'
			) ORDER BY RANDOM() LIMIT ?
			""";
	//language=POSTGRES-PSQL
	public static final String MATCH = """
			SELECT p.*
			FROM profile p
			JOIN "like" l on p.id = l.to_profile
			WHERE l.from_profile = ? AND l."match" = true
			ORDER BY l.created_date DESC
			OFFSET ? LIMIT ?
			""";

	private static final ProfileDao INSTANCE = new ProfileDao();

	private final ResultSetToProfileMapper profileMapper = ResultSetToProfileMapper.getInstance();
	private final ResultSetToProfileSimpleDtoMapper profileSimpleDtoMapper =
			ResultSetToProfileSimpleDtoMapper.getInstance();

	private List<String> sortableColumns;

	@SneakyThrows
	public static ProfileDao getInstance() {
		return INSTANCE;
	}

	public Long save(String email, String password) {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, email);
			stmt.setString(2, password);

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			return rs.getLong("id");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<Profile> findById(Long id) {
		Query query = new ProfileSelectQueryBuilder().addIdFilter(id).build();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = ConnectionManager.getPreparedStmt(conn, query)) {
			ResultSet rs = stmt.executeQuery();

			Profile profile = null;
			if (rs.next()) {
				profile = profileMapper.map(rs);
			}
			return Optional.ofNullable(profile);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<Profile> findByEmailAndPassword(String email, String password) {
		Query query = new ProfileSelectQueryBuilder()
							  .addEmailFilter(email)
							  .addPasswordFilter(password)
							  .build();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = ConnectionManager.getPreparedStmt(conn, query)) {
			ResultSet rs = stmt.executeQuery();
			Profile profile = null;
			if (rs.next()) {
				profile = profileMapper.map(rs);
			}
			return Optional.ofNullable(profile);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean existByEmail(String email) {
		Query query = new ProfileSelectQueryBuilder().addEmailFilter(email).build();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = ConnectionManager.getPreparedStmt(conn, query)) {
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Profile> findAll(ProfileFilter filter) {
		Query query = new ProfileSelectQueryBuilder()
							  .addEmailStartWithFilter(filter.getEmailStartWith())
							  .addNameStartWith(filter.getNameStartWith())
							  .addSurnameStartWith(filter.getSurnameStartWith())
							  .addStatus(filter.getStatus())
							  .addLTAge(filter.getLtAge())
							  .addGTEAge(filter.getGteAge())
							  .build(getSortColumn(filter.getSort()), filter.getPage(), filter.getPageSize());
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = ConnectionManager.getPreparedStmt(conn, query)) {
			stmt.setFetchSize(FETCH_SIZE);
			stmt.setMaxRows(MAX_ROWS);
			stmt.setQueryTimeout(QUERY_TIMEOUT);
			ResultSet rs = stmt.executeQuery();

			List<Profile> profiles = new ArrayList<>();
			while (rs.next()) {
				profiles.add(profileMapper.map(rs));
			}
			return profiles;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(Profile profile) {
		Query query = new ProfileUpdateQueryBuilder()
							  .addEmail(profile.getEmail())
							  .addPassword(profile.getPassword())
							  .addName(profile.getName())
							  .addSurname(profile.getSurname())
							  .addBirthDate(profile.getBirthDate())
							  .addAbout(profile.getAbout())
							  .addGender(profile.getGender())
							  .addStatus(profile.getStatus())
							  .addPhoto(profile.getPhoto())
							  .build(profile.getId());
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = ConnectionManager.getPreparedStmt(conn, query)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean delete(Long id) {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(DELETE)) {
			stmt.setLong(1, id);

			int deleteCount = stmt.executeUpdate();
			log.debug("Delete count: {}", deleteCount);
			return deleteCount > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ProfileSimpleDto> findSuitableForUser(Long userId, int limit) {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(SUITABLE)) {
			stmt.setObject(1, userId);
			stmt.setInt(2, Math.min(limit, 1));
			ResultSet rs = stmt.executeQuery();

			List<ProfileSimpleDto> profiles = new ArrayList<>();
			while (rs.next()) {
				profiles.add(profileSimpleDtoMapper.map(rs));
			}
			return profiles;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Profile> findMatches(Long userId, ProfileFilter filter) {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(MATCH)) {

			Integer pageSize = filter.getPageSize();
			int limit = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
			Integer page = filter.getPage();
			int offset = page == null ? limit * (DEFAULT_PAGE - 1) : limit * (page - 1);

			stmt.setObject(1, userId);
			stmt.setInt(2, offset);
			stmt.setInt(3, limit);
			
			ResultSet rs = stmt.executeQuery();

			List<Profile> profiles = new ArrayList<>();
			while (rs.next()) {
				profiles.add(profileMapper.map(rs));
			}
			return profiles;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private String getSortColumn(String sort) {
		if (sortableColumns == null) {
			try (Connection conn = ConnectionManager.getConnection()) {
				ResultSet rs = conn.getMetaData().getColumns(null, null, "profile", null);
				sortableColumns = new ArrayList<>();
				while (rs.next()) {
					if ("sortable".equals(rs.getString("REMARKS"))) {
						sortableColumns.add(rs.getString("COLUMN_NAME"));
					}
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		if (sort != null && sortableColumns.contains(sort)) {
			return sort;
		}
		return "id";
	}
}
