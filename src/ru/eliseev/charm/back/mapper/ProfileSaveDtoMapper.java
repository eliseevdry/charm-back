package ru.eliseev.charm.back.mapper;

import jakarta.servlet.http.HttpServletRequest;
import ru.eliseev.charm.back.dto.ProfileSaveDto;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;

import java.time.LocalDate;

public class ProfileSaveDtoMapper implements Mapper<HttpServletRequest, ProfileSaveDto> {

    private static final ProfileSaveDtoMapper INSTANCE = new ProfileSaveDtoMapper();

    private ProfileSaveDtoMapper() {
    }

    public static ProfileSaveDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ProfileSaveDto map(HttpServletRequest req, ProfileSaveDto dto) {
        ProfileSaveDto result = dto;
        if (result==null) {
            result = new ProfileSaveDto();
        }
        String id = req.getParameter("id");
        if (id != null) {
            result.setId(Long.parseLong(id));
        }
        result.setEmail(req.getParameter("email"));
        result.setPassword(req.getParameter("password"));
        result.setName(req.getParameter("name"));
        result.setSurname(req.getParameter("surname"));
        String sBirthDate = req.getParameter("birthDate");
        if (sBirthDate != null) {
            result.setBirthDate(LocalDate.parse(sBirthDate));
        }
        result.setAbout(req.getParameter("about"));
        String gender = req.getParameter("gender");
        if (gender != null) {
            result.setGender(Gender.valueOf(gender));
        }
        String status = req.getParameter("status");
        if (status != null) {
            result.setStatus(Status.valueOf(status));
        }
        return result;
    }
}
