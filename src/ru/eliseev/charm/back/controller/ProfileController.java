package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Profile;
import ru.eliseev.charm.back.service.ProfileService;

import javax.naming.NameNotFoundException;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    private final ProfileService service = ProfileService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("genders") == null) {
            req.getSession().setAttribute("genders", Gender.values());
        }
        String sId = req.getParameter("id");
        String forwardUri = "/notFound";
        if (sId != null) {
            Optional<Profile> profile = service.findById(Long.parseLong(sId));
            if (profile.isPresent()) {
                req.setAttribute("profile", profile.get());
                forwardUri = "/WEB-INF/jsp/profile.jsp";
            }
        }
        req.getRequestDispatcher(forwardUri).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sId = req.getParameter("id");
        Profile profile = new Profile();
        profile.setEmail(req.getParameter("email"));
        profile.setName(req.getParameter("name"));
        profile.setSurname(req.getParameter("surname"));
        profile.setAbout(req.getParameter("about"));
        profile.setGender(Gender.valueOf(req.getParameter("gender")));
        
        Long id;
        if (!sId.isBlank()) {
            id = Long.parseLong(sId);
            profile.setId(id);
            service.update(profile);
        } else {
            id = service.save(profile).getId();
        }
        
        resp.sendRedirect(String.format("/profile?id=%s", id));
    }
}
