package ru.eliseev.charm.back.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.eliseev.charm.back.service.ContentService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static ru.eliseev.charm.back.utils.UrlUtils.CONTENT_URL;

@Setter
@Controller
public class ContentController extends HttpServlet {

    @Autowired
    private ContentService contentService;

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
