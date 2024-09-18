package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.dto.ProfileGetDto;
import ru.eliseev.charm.back.dto.ProfileUpdateDto;
import ru.eliseev.charm.back.mapper.RequestToProfileUpdateDtoMapper;
import ru.eliseev.charm.back.model.exception.DuplicateEmailException;
import ru.eliseev.charm.back.service.ProfileService;

import java.io.IOException;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet("/email")
public class EmailController extends HttpServlet {

    private final ProfileService service = ProfileService.getInstance();

    private final RequestToProfileUpdateDtoMapper requestToProfileUpdateDtoMapper = RequestToProfileUpdateDtoMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sId = req.getParameter("id");
        String forwardUri = null;
        if (sId != null) {
            Optional<ProfileGetDto> optProfileDto = service.findById(Long.parseLong(sId));
            if (optProfileDto.isPresent()) {
                req.setAttribute("profile", optProfileDto.get());
                forwardUri = "/WEB-INF/jsp/email.jsp";
            }
        }
        if (forwardUri == null) {
            resp.sendError(SC_NOT_FOUND);
        } else {
            req.getRequestDispatcher(forwardUri).forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProfileUpdateDto dto = requestToProfileUpdateDtoMapper.map(req, new ProfileUpdateDto());
        try {
            service.update(dto);
            resp.sendRedirect(String.format("/profile?id=%s", dto.getId()));
        } catch (DuplicateEmailException e) {
            resp.sendError(SC_BAD_REQUEST);
        }
    }
}
