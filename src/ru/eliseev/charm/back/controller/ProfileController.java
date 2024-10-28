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
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.ProfileGetDtoToPdfMapper;
import ru.eliseev.charm.back.mapper.RequestToProfileUpdateDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;
import ru.eliseev.charm.back.validator.ProfileUpdateValidator;
import ru.eliseev.charm.back.validator.ValidationResult;

import java.io.IOException;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILE_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REGISTRATION_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

@WebServlet(PROFILE_URL)
@MultipartConfig
@Slf4j
public class ProfileController extends HttpServlet {
    private final ProfileService service = ProfileService.getInstance();

    private final RequestToProfileUpdateDtoMapper requestToProfileUpdateDtoMapper = RequestToProfileUpdateDtoMapper.getInstance();

    private final ProfileUpdateValidator profileUpdateValidator = ProfileUpdateValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sId = req.getParameter("id");
        if (sId != null) {
            Optional<ProfileGetDto> optProfileGetDto = service.findById(Long.parseLong(sId));
            if (optProfileGetDto.isPresent()) {
                ProfileGetDto profileGetDto = optProfileGetDto.get();
                req.setAttribute("profile", profileGetDto);
                req.getRequestDispatcher(getJspPath(PROFILE_URL)).forward(req, resp);
            } else {
                resp.sendError(SC_NOT_FOUND);
            }
        } else {
            req.setAttribute("profiles", service.findAll());
            req.getRequestDispatcher(getJspPath("/profiles")).forward(req, resp);
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
        if (!isBlank(sId) && service.delete(Long.parseLong(sId))) {
            log.info("Profile with id {} has been deleted", sId);
            UserDetails userDetails = (UserDetails) req.getSession().getAttribute("userDetails");
            if (sId.equals(userDetails.getId().toString())) {
                req.getSession().invalidate();
            }
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            resp.sendRedirect(REGISTRATION_URL);
        } else {
            resp.sendError(SC_NOT_FOUND);
        }
    }
}
