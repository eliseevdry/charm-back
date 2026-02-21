package ru.eliseev.charm.back.controller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.eliseev.charm.back.dto.RegistrationDto;
import ru.eliseev.charm.back.mapper.RequestToRegistrationDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;
import ru.eliseev.charm.back.validator.RegistrationValidator;
import ru.eliseev.charm.back.validator.ValidationResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REGISTRATION_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

@Slf4j
@Setter
@Controller
public class RegistrationController extends HttpServlet {

    @Autowired
    private ProfileService service;

    @Autowired
    private RequestToRegistrationDtoMapper requestToRegistrationDtoMapper;

    @Autowired
    private RegistrationValidator registrationValidator;

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
