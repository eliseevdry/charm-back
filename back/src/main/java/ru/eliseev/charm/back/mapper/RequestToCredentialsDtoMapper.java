package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.CredentialsDto;

import jakarta.servlet.http.HttpServletRequest;

public class RequestToCredentialsDtoMapper implements Mapper<HttpServletRequest, CredentialsDto> {

    @Override
    public CredentialsDto map(HttpServletRequest req) {
        return map(req, new CredentialsDto());
    }

    @Override
    public CredentialsDto map(HttpServletRequest req, CredentialsDto dto) {
        dto.setId(Long.parseLong(req.getParameter("id")));
        dto.setVersion(Integer.parseInt(req.getParameter("version")));
        dto.setEmail(req.getParameter("email"));
        dto.setNewPassword(req.getParameter("newPassword"));
        dto.setConfirmNewPassword(req.getParameter("confirm"));
        dto.setCurrentPassword(req.getParameter("password"));
        return dto;
    }
}
