package ru.eliseev.charm.back.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_URL;

public class LogoutController extends HttpServlet {

	private static final LogoutController INSTANCE = new LogoutController();

	public static LogoutController getInstance() {
		return INSTANCE;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.getSession().invalidate();
		res.sendRedirect(LOGIN_URL);
	}
}
