package ru.eliseev.charm.back.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/lang")
public class LanguageController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lang = "ru".equals(req.getParameter("lang")) ? "ru" : "en";

        Cookie cookie = new Cookie("lang", lang);

        resp.addCookie(cookie);
        String referer = req.getHeader("referer");

        resp.sendRedirect(referer);
    }
}
