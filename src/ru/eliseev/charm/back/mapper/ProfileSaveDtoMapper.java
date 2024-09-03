package ru.eliseev.charm.back.mapper;

import jakarta.servlet.http.HttpServletRequest;
import ru.eliseev.charm.back.dto.ProfileSaveDto;
import ru.eliseev.charm.back.model.Gender;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ProfileSaveDtoMapper implements Mapper<HttpServletRequest, ProfileSaveDto> {

    private static final ProfileSaveDtoMapper INSTANCE = new ProfileSaveDtoMapper();

    private ProfileSaveDtoMapper() {
    }

    public static ProfileSaveDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ProfileSaveDto map(HttpServletRequest req) {
        ProfileSaveDto dto = new ProfileSaveDto();
        String sId = req.getParameter("id");
        if (sId != null && !sId.isBlank()) {
            dto.setId(Long.parseLong(sId));
        }
        dto.setEmail(req.getParameter("email"));
        dto.setName(req.getParameter("name"));
        dto.setSurname(req.getParameter("surname"));
        String sBirthDate = req.getParameter("birthDate");
        if (sBirthDate != null && !sBirthDate.isBlank()) {
            dto.setBirthDate(LocalDate.parse(sBirthDate));
        }
        dto.setAbout(req.getParameter("about"));
        dto.setGender(Gender.valueOf(req.getParameter("gender")));
        return dto;
    }
}
