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

@WebServlet("/registration")
@Slf4j
public class RegistrationController extends HttpServlet {

    private final ProfileService service = ProfileService.getInstance();

    private final RequestToRegistrationDtoMapper requestToRegistrationDtoMapper = RequestToRegistrationDtoMapper.getInstance();

    private final RegistrationValidator registrationValidator = RegistrationValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RegistrationDto dto = requestToRegistrationDtoMapper.map(req);
        ValidationResult validationResult = registrationValidator.validate(dto);
        if (validationResult.isValid()) {
            Long id = service.save(dto);
            log.info("Profile with the email address {} has been registered with id {}", dto.getEmail(), id);
            resp.sendRedirect(String.format("/profile?id=%s", id));
        } else {
            req.setAttribute("errors", validationResult.getErrors());
            doGet(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sId = req.getParameter("id");
        boolean success = false;
        if (!sId.isBlank()) {
            success = service.delete(Long.parseLong(sId));
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        if (success) {
            log.info("Profile with id {} has been deleted", sId);
        }
        resp.sendRedirect("/registration");
    }
}
