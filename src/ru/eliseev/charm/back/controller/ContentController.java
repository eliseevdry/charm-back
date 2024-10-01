package ru.eliseev.charm.back.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.service.ContentService;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet("/content/*")
public class ContentController extends HttpServlet {

    private final ContentService contentService = ContentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentPath = req.getRequestURI().replaceFirst("/content", "");
        resp.setContentType("application/octet-stream");
        try {
            contentService.download(contentPath, resp.getOutputStream());
        } catch (IOException e) {
            resp.sendError(SC_NOT_FOUND);
        }
    }
}
