package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.dto.ProfileUpdateDto;
import ru.eliseev.charm.back.mapper.RequestToProfileUpdateDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    private final ProfileService service = ProfileService.getInstance();

    private final RequestToProfileUpdateDtoMapper requestToProfileUpdateDtoMapper = RequestToProfileUpdateDtoMapper.getInstance();

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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProfileUpdateDto dto = requestToProfileUpdateDtoMapper.map(req);
        service.update(dto);
        String referer = req.getHeader("referer");
        resp.sendRedirect(referer);
    }
}
