package ru.eliseev.charm.back.controller.rest;

import com.fasterxml.jackson.databind.DatabindException;
import ru.eliseev.charm.back.dto.CharmDto;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.mapper.JsonMapper;
import ru.eliseev.charm.back.service.CharmService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class CharmRestController extends HttpServlet {

	private final CharmService service = CharmService.getInstance();

	private final JsonMapper jsonMapper = JsonMapper.getInstance();

	private static final CharmRestController INSTANCE = new CharmRestController();

	public static CharmRestController getInstance() {
		return INSTANCE;
	}

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
