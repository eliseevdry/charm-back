package ru.eliseev.charm.back.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ru.eliseev.charm.back.utils.UrlUtils.LANG_URL;

@WebServlet(LANG_URL)
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
