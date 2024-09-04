package ru.eliseev.charm.back.mapper;

import jakarta.servlet.http.HttpServletRequest;
import ru.eliseev.charm.back.dto.RegistrationDto;

public class RequestToRegistrationDtoMapper implements Mapper<HttpServletRequest, RegistrationDto> {

    private static final RequestToRegistrationDtoMapper INSTANCE = new RequestToRegistrationDtoMapper();

    private RequestToRegistrationDtoMapper() {
    }

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
