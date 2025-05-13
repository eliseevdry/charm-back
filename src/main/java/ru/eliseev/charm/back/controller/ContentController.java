package ru.eliseev.charm.back.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.service.ContentService;

import java.io.FileNotFoundException;
import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static ru.eliseev.charm.back.utils.UrlUtils.CONTENT_URL;

@WebServlet(CONTENT_URL + "/*")
public class ContentController extends HttpServlet {

    private final ContentService contentService = ContentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String contentPath = req.getRequestURI().replaceFirst(CONTENT_URL, "");
        res.setContentType("application/octet-stream");
        try {
            contentService.download(contentPath, res.getOutputStream());
        } catch (FileNotFoundException e) {
            res.sendError(SC_NOT_FOUND);
        }
    }
}
