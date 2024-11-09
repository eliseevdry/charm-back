package ru.eliseev.charm.back.controller.rest;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.ProfileFullUpdateDto;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.dto.RegistrationDto;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.JsonMapper;
import ru.eliseev.charm.back.service.ProfileService;
import ru.eliseev.charm.back.validator.ProfileFullUpdateValidator;
import ru.eliseev.charm.back.validator.RegistrationValidator;
import ru.eliseev.charm.back.validator.ValidationResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILE_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REST_URL;

@WebServlet(REST_URL + PROFILE_URL)
@MultipartConfig
@Slf4j
public class ProfileController extends HttpServlet {
    private final ProfileService service = ProfileService.getInstance();
    private final JsonMapper jsonMapper = JsonMapper.getInstance();
    private final ProfileFullUpdateValidator profileFullUpdateValidator = ProfileFullUpdateValidator.getInstance();
    private final RegistrationValidator registrationValidator = RegistrationValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            String sId = req.getParameter("id");
            if (sId != null) {
                Optional<ProfileGetDto> optProfileDto = service.findById(Long.parseLong(sId));
                if (optProfileDto.isPresent()) {
                    jsonMapper.writeValue(writer, optProfileDto.get());
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                jsonMapper.writeValue(writer, service.findAll());
            }
        } catch (DatabindException ex) {
            req.setAttribute("errors", List.of(ex.getMessage()));
            resp.sendError(SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            RegistrationDto dto = jsonMapper.readValue(reader, RegistrationDto.class);
            ValidationResult validationResult = registrationValidator.validate(dto);
            if (validationResult.isValid()) {
                Long id = service.save(dto);
                log.info("Profile with the email address {} has been registered with id {}", dto.getEmail(), id);
            } else {
                req.setAttribute("errors", validationResult.getErrors());
                resp.sendError(SC_BAD_REQUEST);
            }
        } catch (DatabindException ex) {
            req.setAttribute("errors", List.of(ex.getLocalizedMessage(), ex.getOriginalMessage()));
            resp.sendError(SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part json = req.getPart("json");
        if (json != null) {
            Part photo = req.getPart("photo");
            try (InputStream inputStream = json.getInputStream()) {
                ProfileFullUpdateDto dto = jsonMapper.readValue(inputStream, ProfileFullUpdateDto.class);
                if (photo != null && !isBlank(photo.getSubmittedFileName())) {
                    dto.setPhoto(photo);
                } else {
                    dto.setPhoto(null);
                }
                ValidationResult validationResult = profileFullUpdateValidator.validate(dto);
                if (validationResult.isValid()) {
                    service.update(dto);
                } else {
                    req.setAttribute("errors", validationResult.getErrors());
                    resp.sendError(SC_BAD_REQUEST);
                }
            } catch (DatabindException ex) {
                req.setAttribute("errors", List.of(ex.getLocalizedMessage(), ex.getOriginalMessage()));
                resp.sendError(SC_BAD_REQUEST);
            }
        } else {
            resp.sendError(SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sId = req.getParameter("id");
        if (!isBlank(sId) && service.delete(Long.parseLong(sId))) {
            UserDetails userDetails = (UserDetails) req.getSession().getAttribute("userDetails");
            if (sId.equals(userDetails.getId().toString())) {
                req.getSession().invalidate();
            }
            resp.setStatus(SC_NO_CONTENT);
        } else {
            resp.sendError(SC_NOT_FOUND);
        }

    }
}
