package ru.eliseev.charm.back.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.LOGOUT_URL;

@WebServlet(LOGOUT_URL)
@Slf4j
public class LogoutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(LOGIN_URL);
    }
}
