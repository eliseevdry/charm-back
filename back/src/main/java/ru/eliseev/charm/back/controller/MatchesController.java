package ru.eliseev.charm.back.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.eliseev.charm.back.dto.ProfileFilter;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.RequestToProfileFilterMapper;
import ru.eliseev.charm.back.service.ProfileService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.eliseev.charm.back.utils.UrlUtils.MATCHES_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

@Setter
@Controller
public class MatchesController extends HttpServlet {

    @Autowired
    private ProfileService service;
    @Autowired
    private RequestToProfileFilterMapper requestToProfileFilterMapper;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		UserDetails userDetails = (UserDetails) req.getSession().getAttribute("userDetails");
		ProfileFilter filter = requestToProfileFilterMapper.map(req);
		req.setAttribute("matches", service.findMatches(userDetails.getId(), filter));
		req.setAttribute("filter", filter);
		req.getRequestDispatcher(getJspPath(MATCHES_URL)).forward(req, res);
	}
}
