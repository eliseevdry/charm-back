package ru.eliseev.charm.back.controller;

import static ru.eliseev.charm.back.utils.UrlUtils.MATCHES_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.eliseev.charm.back.dto.ProfileFilter;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.RequestToProfileFilterMapper;
import ru.eliseev.charm.back.service.ProfileService;

@WebServlet(MATCHES_URL)
public class MatchesController extends HttpServlet {

	private final ProfileService service = ProfileService.getInstance();
	private final RequestToProfileFilterMapper requestToProfileFilterMapper = RequestToProfileFilterMapper.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		UserDetails userDetails = (UserDetails) req.getSession().getAttribute("userDetails");
		ProfileFilter filter = requestToProfileFilterMapper.map(req);
		req.setAttribute("matches", service.findMatches(userDetails.getId(), filter));
		req.setAttribute("filter", filter);
		req.getRequestDispatcher(getJspPath(MATCHES_URL)).forward(req, res);
	}
}
