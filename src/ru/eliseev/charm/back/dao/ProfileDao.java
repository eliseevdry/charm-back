package ru.eliseev.charm.back.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.back.model.Role;
import ru.eliseev.charm.back.model.Status;

@Slf4j
public class ProfileDao {

	private static final ProfileDao INSTANCE = new ProfileDao();
	private static final String URL = "jdbc:postgresql://localhost:5432/charm_repository";
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";

	@SneakyThrows
	public static ProfileDao getInstance() {
		Class.forName("org.postgresql.Driver");
		return INSTANCE;
	}

	public Profile save(Profile profile) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			 Statement stmt = conn.createStatement()) {
			//language=POSTGRES-PSQL
			String sql = "INSERT INTO profile (email, password) VALUES ('%s', '%s')";

			int insertCount = stmt.executeUpdate(
					String.format(sql, profile.getEmail(), profile.getPassword()),
					Statement.RETURN_GENERATED_KEYS
			);
			log.debug("Insert count: {}", insertCount);

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();

			profile.setId(rs.getLong("id"));
			return profile;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<Profile> findById(Long id) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			 Statement stmt = conn.createStatement()) {
			//language=POSTGRES-PSQL
			String sql = "SELECT * FROM profile WHERE id = %s";

			ResultSet rs = stmt.executeQuery(String.format(sql, id));

			Profile profile = null;
			if (rs.next()) {
				profile = mapToProfile(rs);
			}
			return Optional.ofNullable(profile);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<Profile> findByEmailAndPassword(String email, String password) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			 Statement stmt = conn.createStatement()) {
			//language=POSTGRES-PSQL
			String sql = "SELECT * FROM profile WHERE email = '%s' AND password = '%s'";

			ResultSet rs = stmt.executeQuery(String.format(sql, email, password));

			Profile profile = null;
			if (rs.next()) {
				profile = mapToProfile(rs);
			}
			return Optional.ofNullable(profile);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean existByEmail(String email) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			 Statement stmt = conn.createStatement()) {
			//language=POSTGRES-PSQL
			String sql = "SELECT * FROM profile WHERE email = '%s'";

			ResultSet rs = stmt.executeQuery(String.format(sql, email));
			return rs.next();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Profile> findAll() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			 Statement stmt = conn.createStatement()) {
			//language=POSTGRES-PSQL
			String sql = "SELECT * FROM profile";

			ResultSet rs = stmt.executeQuery(sql);

			List<Profile> profiles = new ArrayList<>();
			while (rs.next()) {
				profiles.add(mapToProfile(rs));
			}
			return profiles;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(Profile profile) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			 Statement stmt = conn.createStatement()) {
			List<Object> args = new ArrayList<>();
			StringBuilder queryBuilder = new StringBuilder("UPDATE profile SET email = '%s', password = '%s'");
			args.add(profile.getEmail());
			args.add(profile.getPassword());

			if (profile.getName() != null) {
				queryBuilder.append(", name = '%s'");
				args.add(profile.getName());
			}
			if (profile.getSurname() != null) {
				queryBuilder.append(", surname = '%s'");
				args.add(profile.getSurname());
			}
			if (profile.getBirthDate() != null) {
				queryBuilder.append(", birth_date = '%s'");
				args.add(Date.valueOf(profile.getBirthDate()));
			}
			if (profile.getAbout() != null) {
				queryBuilder.append(", about = '%s'");
				args.add(profile.getAbout());
			}
			if (profile.getGender() != null) {
				queryBuilder.append(", gender = '%s'");
				args.add(profile.getGender());
			}
			if (profile.getStatus() != null) {
				queryBuilder.append(", status = '%s'");
				args.add(profile.getStatus());
			}
			if (profile.getPhoto() != null) {
				queryBuilder.append(", photo = '%s'");
				args.add(profile.getPhoto());
			}

			queryBuilder.append(" WHERE id = %s");
			args.add(profile.getId());

			String sql = queryBuilder.toString().formatted(args.toArray());
			log.debug("Final update sql: {}", sql);

			int updateCount = stmt.executeUpdate(sql);
			log.debug("Update count: {}", updateCount);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean delete(Long id) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			 Statement stmt = conn.createStatement()) {
			//language=POSTGRES-PSQL
			String sql = "DELETE FROM profile WHERE id = %s".formatted(id);

			int deleteCount = stmt.executeUpdate(sql);
			log.debug("Delete count: {}", deleteCount);
			return deleteCount > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Profile mapToProfile(ResultSet rs) throws SQLException {
		Profile result = new Profile();
		result.setId(rs.getLong("id"));
		result.setEmail(rs.getString("email"));
		result.setPassword(rs.getString("password"));
		result.setName(rs.getString("name"));
		result.setSurname(rs.getString("surname"));
		Date birthDate = rs.getDate("birth_date");
		if (birthDate != null) {
			result.setBirthDate(birthDate.toLocalDate());
		}
		result.setAbout(rs.getString("about"));
		String gender = rs.getString("gender");
		if (gender != null) {
			result.setGender(Gender.valueOf(gender));
		}
		result.setPhoto(rs.getString("photo"));
		String status = rs.getString("status");
		if (status != null) {
			result.setStatus(Status.valueOf(status));
		}
		String role = rs.getString("role");
		if (role != null) {
			result.setRole(Role.valueOf(role));
		}
		return result;
	}
}
