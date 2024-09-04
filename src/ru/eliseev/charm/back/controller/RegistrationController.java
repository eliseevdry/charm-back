package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.dto.RegistrationDto;
import ru.eliseev.charm.back.mapper.RequestToRegistrationDtoMapper;
import ru.eliseev.charm.back.service.ProfileService;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {

    private final ProfileService service = ProfileService.getInstance();

    private final RequestToRegistrationDtoMapper requestToRegistrationDtoMapper = RequestToRegistrationDtoMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RegistrationDto dto = requestToRegistrationDtoMapper.map(req);
        Long id = service.save(dto);
        resp.sendRedirect(String.format("/profile?id=%s", id));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sId = req.getParameter("id");
        if (!sId.isBlank()) {
            service.delete(Long.parseLong(sId));
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        resp.sendRedirect("/registration");
    }
}
