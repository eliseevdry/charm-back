package ru.eliseev.charm.back.controller.rest;

import com.fasterxml.jackson.databind.DatabindException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.eliseev.charm.back.dto.ProfileFilter;
import ru.eliseev.charm.back.mapper.JsonMapper;
import ru.eliseev.charm.back.mapper.RequestToProfileFilterMapper;
import ru.eliseev.charm.back.service.ProfileService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@Setter
@RestController
public class ProfilesRestController extends HttpServlet {
    @Autowired
    private ProfileService service;
    @Autowired
    private JsonMapper jsonMapper;
    @Autowired
    private RequestToProfileFilterMapper requestToProfileFilterMapper;

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
