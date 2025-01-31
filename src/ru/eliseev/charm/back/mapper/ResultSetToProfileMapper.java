package ru.eliseev.charm.back.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.back.model.Role;
import ru.eliseev.charm.back.model.Status;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultSetToProfileMapper implements Mapper<ResultSet, Profile> {

	private static final ResultSetToProfileMapper INSTANCE = new ResultSetToProfileMapper();

	public static ResultSetToProfileMapper getInstance() {
		return INSTANCE;
	}

	@Override
	public Profile map(ResultSet rs) {
		return map(rs, new Profile());
	}

	@Override
	@SneakyThrows
	public Profile map(ResultSet rs, Profile profile) {
		profile.setId(rs.getLong("id"));
		profile.setEmail(rs.getString("email"));
		profile.setPassword(rs.getString("password"));
		profile.setName(rs.getString("name"));
		profile.setSurname(rs.getString("surname"));
		Date birthDate = rs.getDate("birth_date");
		if (birthDate != null) {
			profile.setBirthDate(birthDate.toLocalDate());
		}
		profile.setAbout(rs.getString("about"));
		String gender = rs.getString("gender");
		if (gender != null) {
			profile.setGender(Gender.valueOf(gender));
		}
		profile.setPhoto(rs.getString("photo"));
		String status = rs.getString("status");
		if (status != null) {
			profile.setStatus(Status.valueOf(status));
		}
		String role = rs.getString("role");
		if (role != null) {
			profile.setRole(Role.valueOf(role));
		}
		return profile;
	}
}
