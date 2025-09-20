package ru.eliseev.charm.back.controller;

import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.CredentialsDto;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.mapper.RequestToCredentialsDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;
import ru.eliseev.charm.back.validator.CredentialsValidator;
import ru.eliseev.charm.back.validator.ValidationResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;
import static ru.eliseev.charm.back.utils.UrlUtils.CREDENTIALS_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILE_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

@WebServlet(CREDENTIALS_URL)
@Slf4j
public class CredentialsController extends HttpServlet {

    private final ProfileService service = ProfileService.getInstance();

    private final RequestToCredentialsDtoMapper requestToCredentialsDtoMapper = RequestToCredentialsDtoMapper.getInstance();

    private final CredentialsValidator credentialsValidator = CredentialsValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String sId = req.getParameter("id");
        String forwardUri = null;
        if (sId != null) {
            Optional<ProfileGetDto> optProfileDto = service.findById(Long.parseLong(sId));
            if (optProfileDto.isPresent()) {
                req.setAttribute("profile", optProfileDto.get());
                forwardUri = getJspPath(CREDENTIALS_URL);
            }
        }
        if (forwardUri == null) {
            res.sendError(SC_NOT_FOUND);
        } else {
            req.getRequestDispatcher(forwardUri).forward(req, res);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        CredentialsDto dto = requestToCredentialsDtoMapper.map(req);
        ValidationResult validationResult = credentialsValidator.validate(dto);
        if (validationResult.isValid()) {
            service.update(dto);
            if (!isBlank(dto.getEmail())) {
                log.warn("Profile with id {} changed email to {}", dto.getId(), dto.getEmail());
            }
            res.sendRedirect(String.format(PROFILE_URL + "?id=%s", dto.getId()));
        } else {
            req.setAttribute("errors", validationResult.getErrors());
            doGet(req, res);
        }
    }
}
