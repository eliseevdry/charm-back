package ru.eliseev.charm.back.controller.rest;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILES_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REST_URL;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.dto.ProfileFilter;
import ru.eliseev.charm.back.mapper.JsonMapper;
import ru.eliseev.charm.back.mapper.RequestToProfileFilterMapper;
import ru.eliseev.charm.back.service.ProfileService;

@WebServlet(REST_URL + PROFILES_URL)
@Slf4j
public class ProfilesController extends HttpServlet {
	private final ProfileService service = ProfileService.getInstance();
	private final JsonMapper jsonMapper = JsonMapper.getInstance();
	private final RequestToProfileFilterMapper requestToProfileFilterMapper = RequestToProfileFilterMapper.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try (PrintWriter writer = res.getWriter()) {
			ProfileFilter filter = requestToProfileFilterMapper.map(req);
			jsonMapper.writeValue(writer, service.findAll(filter));
		} catch (DatabindException ex) {
			req.setAttribute("errors", List.of(ex.getMessage()));
			res.sendError(SC_BAD_REQUEST);
		}
	}
}
