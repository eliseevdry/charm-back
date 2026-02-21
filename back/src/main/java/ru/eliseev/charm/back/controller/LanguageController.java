package ru.eliseev.charm.back.controller;

import lombok.Setter;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Setter
@Controller
public class LanguageController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String lang = "ru".equals(req.getParameter("lang")) ? "ru" : "en";

        Cookie cookie = new Cookie("lang", lang);

        res.addCookie(cookie);
        String referer = req.getHeader("referer");

        res.sendRedirect(referer);
    }
}
