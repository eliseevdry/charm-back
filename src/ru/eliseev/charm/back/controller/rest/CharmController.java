package ru.eliseev.charm.back.controller.rest;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static ru.eliseev.charm.back.utils.UrlUtils.CHARM_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REST_URL;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import ru.eliseev.charm.back.dto.CharmDto;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.JsonMapper;
import ru.eliseev.charm.back.service.CharmService;

@WebServlet(REST_URL + CHARM_URL)
public class CharmController extends HttpServlet {

	private final CharmService service = CharmService.getInstance();

	private final JsonMapper jsonMapper = JsonMapper.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try (BufferedReader reader = req.getReader(); PrintWriter writer = res.getWriter()) {
			CharmDto dto = jsonMapper.readValue(reader, CharmDto.class);
			UserDetails userDetails = (UserDetails) req.getSession().getAttribute("userDetails");
			dto.setFromProfile(userDetails.getId());
			Optional<ProfileSimpleDto> profileSimpleDtoOpt = service.next(dto);
			if (profileSimpleDtoOpt.isPresent()) {
				jsonMapper.writeValue(writer, profileSimpleDtoOpt.get());
			} else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (DatabindException ex) {
			req.setAttribute("errors", List.of(ex.getLocalizedMessage(), ex.getOriginalMessage()));
			res.sendError(SC_BAD_REQUEST);
		}
	}
}
