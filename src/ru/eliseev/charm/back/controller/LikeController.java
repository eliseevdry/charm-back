package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.service.LikeService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/like")
public class LikeController extends HttpServlet {

    private final LikeService service = LikeService.getInstance();

    @Override
    public void init(ServletConfig config) {
        System.out.println("Servlet started, name: " + config.getServletName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        try (PrintWriter writer = resp.getWriter()) {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            writer.write("<h2>");
            writer.write("<p> RequestURI: " + req.getRequestURI() + "</p>");
            writer.write("<p> User agent header: " + req.getHeader("User-Agent") + "</p>");
            writer.write("<p> Likes count: " + service.getLikesByProfileId(id) + "</p>");
            writer.write("</h2>");
        }
    }

    @Override
    public void destroy() {
        System.out.println("Servlet destroyed");
    }
}
