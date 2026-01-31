package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.LoginDto;

import jakarta.servlet.http.HttpServletRequest;

public class RequestToLoginDtoMapper implements Mapper<HttpServletRequest, LoginDto> {

    @Override
    public LoginDto map(HttpServletRequest req) {
        return map(req, new LoginDto());
    }

    @Override
    public LoginDto map(HttpServletRequest req, LoginDto dto) {
        dto.setEmail(req.getParameter("email"));
        dto.setPassword(req.getParameter("password"));
        return dto;
    }
}
