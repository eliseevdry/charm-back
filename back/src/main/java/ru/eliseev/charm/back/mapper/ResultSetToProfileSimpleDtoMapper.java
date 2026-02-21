package ru.eliseev.charm.back.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;

import java.sql.Date;
import java.sql.ResultSet;

import static ru.eliseev.charm.back.utils.UrlUtils.getProfilePhotoPath;
import static ru.eliseev.charm.utils.DateTimeUtils.getAge;
import static ru.eliseev.charm.utils.StringUtils.isBlank;

@Component
public class ResultSetToProfileSimpleDtoMapper implements Mapper<ResultSet, ProfileSimpleDto> {

	@Override
	public ProfileSimpleDto map(ResultSet rs) {
		return map(rs, new ProfileSimpleDto());
	}

	@Override
	@SneakyThrows
	public ProfileSimpleDto map(ResultSet rs, ProfileSimpleDto dto) {
		dto.setId(rs.getLong("id"));
		dto.setName(rs.getString("name"));
		dto.setSurname(rs.getString("surname"));
		Date birthDate = rs.getDate("birth_date");
		if (birthDate != null) {
			dto.setAge(getAge(birthDate.toLocalDate()));
		}
		dto.setAbout(rs.getString("about"));
		String photo = rs.getString("photo");
		if (!isBlank(photo)) {
			dto.setPhoto(getProfilePhotoPath(dto.getId(), photo));
		}
		return dto;
	}
}
