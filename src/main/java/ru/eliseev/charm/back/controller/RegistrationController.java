package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.RegistrationDto;
import ru.eliseev.charm.back.mapper.RequestToRegistrationDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;
import ru.eliseev.charm.back.validator.RegistrationValidator;
import ru.eliseev.charm.back.validator.ValidationResult;

import java.io.IOException;

import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REGISTRATION_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

@WebServlet(REGISTRATION_URL)
@Slf4j
public class RegistrationController extends HttpServlet {

    private final ProfileService service = ProfileService.getInstance();

    private final RequestToRegistrationDtoMapper requestToRegistrationDtoMapper = RequestToRegistrationDtoMapper.getInstance();

    private final RegistrationValidator registrationValidator = RegistrationValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPath(REGISTRATION_URL)).forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RegistrationDto dto = requestToRegistrationDtoMapper.map(req);
        ValidationResult validationResult = registrationValidator.validate(dto);
        if (validationResult.isValid()) {
            Long id = service.save(dto);
            log.info("Profile with the email address {} has been registered with id {}", dto.getEmail(), id);
            res.sendRedirect(LOGIN_URL);
        } else {
            req.setAttribute("errors", validationResult.getErrors());
            doGet(req, res);
        }
    }
}
