package ru.eliseev.charm.back.mapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dto.LoginDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestToLoginDtoMapper implements Mapper<HttpServletRequest, LoginDto> {

    private static final RequestToLoginDtoMapper INSTANCE = new RequestToLoginDtoMapper();

    public static RequestToLoginDtoMapper getInstance() {
        return INSTANCE;
    }

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
