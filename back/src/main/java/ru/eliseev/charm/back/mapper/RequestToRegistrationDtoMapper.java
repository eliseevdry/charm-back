package ru.eliseev.charm.back.mapper;

import org.springframework.stereotype.Component;
import ru.eliseev.charm.back.dto.RegistrationDto;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class RequestToRegistrationDtoMapper implements Mapper<HttpServletRequest, RegistrationDto> {

    @Override
    public RegistrationDto map(HttpServletRequest req) {
        return map(req, new RegistrationDto());
    }

    @Override
    public RegistrationDto map(HttpServletRequest req, RegistrationDto dto) {
        dto.setEmail(req.getParameter("email"));
        dto.setPassword(req.getParameter("password"));
        dto.setConfirm(req.getParameter("confirm"));
        return dto;
    }
}
