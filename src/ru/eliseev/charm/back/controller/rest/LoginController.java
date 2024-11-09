package ru.eliseev.charm.back.controller.rest;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.LoginDto;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.JsonMapper;
import ru.eliseev.charm.back.service.ProfileService;
import ru.eliseev.charm.back.validator.LoginValidator;
import ru.eliseev.charm.back.validator.ValidationResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_REST_URL;

@WebServlet(LOGIN_REST_URL)
@Slf4j
public class LoginController extends HttpServlet {

    private final ProfileService service = ProfileService.getInstance();

    private final LoginValidator loginValidator = LoginValidator.getInstance();

    private final JsonMapper jsonMapper = JsonMapper.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            LoginDto dto = jsonMapper.readValue(reader, LoginDto.class);
            ValidationResult validationResult = loginValidator.validate(dto);
            if (validationResult.isValid()) {
                Optional<UserDetails> userDetailsOpt = service.getUserDetails(dto.getEmail());
                if (userDetailsOpt.isPresent()) {
                    UserDetails userDetails = userDetailsOpt.get();
                    req.getSession().setAttribute("userDetails", userDetails);
                } else {
                    resp.sendError(SC_NOT_FOUND);
                }
            } else {
                req.setAttribute("errors", validationResult.getErrors());
                resp.sendError(SC_BAD_REQUEST);
            }
        } catch (DatabindException ex) {
            req.setAttribute("errors", List.of(ex.getLocalizedMessage(), ex.getOriginalMessage()));
            resp.sendError(SC_BAD_REQUEST);
        }
    }
}
