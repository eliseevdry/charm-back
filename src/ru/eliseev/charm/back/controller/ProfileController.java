package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.dto.ProfileSaveDto;
import ru.eliseev.charm.back.mapper.ProfileSaveDtoMapper;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.back.service.ProfileService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    private final ProfileService service = ProfileService.getInstance();

    private final ProfileSaveDtoMapper profileSaveDtoMapper = ProfileSaveDtoMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sId = req.getParameter("id");
        String forwardUri = "/notFound";
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
        req.getRequestDispatcher(forwardUri).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProfileSaveDto dto = profileSaveDtoMapper.map(req);
        Long id = service.save(dto);
        resp.sendRedirect(String.format("/profile?id=%s", id));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProfileSaveDto dto = profileSaveDtoMapper.map(req);
        service.update(dto);
        resp.sendRedirect(String.format("/profile?id=%s", dto.getId()));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sId = req.getParameter("id");
        if (!sId.isBlank()) {
            service.delete(Long.parseLong(sId));
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        resp.sendRedirect("/registration");
    }
}
