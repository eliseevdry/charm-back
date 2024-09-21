package ru.eliseev.charm.back.mapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dto.RegistrationDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestToRegistrationDtoMapper implements Mapper<HttpServletRequest, RegistrationDto> {

    private static final RequestToRegistrationDtoMapper INSTANCE = new RequestToRegistrationDtoMapper();

    public static RequestToRegistrationDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public RegistrationDto map(HttpServletRequest req) {
        return map(req, new RegistrationDto());
    }

    @Override
    public RegistrationDto map(HttpServletRequest req, RegistrationDto dto) {
        dto.setEmail(req.getParameter("email"));
        dto.setPassword(req.getParameter("password"));
        return dto;
    }
}
