package ru.eliseev.charm.back.controller;

import lombok.Setter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_URL;

@Setter
public class LogoutController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.getSession().invalidate();
		res.sendRedirect(LOGIN_URL);
	}
}
