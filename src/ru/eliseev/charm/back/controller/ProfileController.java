package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.dto.ProfileUpdateDto;
import ru.eliseev.charm.back.mapper.RequestToProfileUpdateDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;
import ru.eliseev.charm.back.validator.ProfileUpdateValidator;
import ru.eliseev.charm.back.validator.ValidationResult;

import java.io.IOException;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet("/profile")
@MultipartConfig
@Slf4j
public class ProfileController extends HttpServlet {
    private final ProfileService service = ProfileService.getInstance();

    private final RequestToProfileUpdateDtoMapper requestToProfileUpdateDtoMapper = RequestToProfileUpdateDtoMapper.getInstance();

    private final ProfileUpdateValidator profileUpdateValidator = ProfileUpdateValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sId = req.getParameter("id");
        String forwardUri = null;
        if (sId != null) {
            Optional<ProfileGetDto> optProfileDto = service.findById(Long.parseLong(sId));
            if (optProfileDto.isPresent()) {
                req.setAttribute("profile", optProfileDto.get());
                forwardUri = "/WEB-INF/jsp/profile.jsp";
            }
        } else {
            req.setAttribute("profiles", service.findAll());
            forwardUri = "/WEB-INF/jsp/profiles.jsp";
        }
        if (forwardUri == null) {
            resp.sendError(SC_NOT_FOUND);
        } else {
            req.getRequestDispatcher(forwardUri).forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProfileUpdateDto dto = requestToProfileUpdateDtoMapper.map(req);
        ValidationResult validationResult = profileUpdateValidator.validate(dto);
        if (validationResult.isValid()) {
            service.update(dto);
            String referer = req.getHeader("referer");
            resp.sendRedirect(referer);
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
