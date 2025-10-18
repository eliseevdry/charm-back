package ru.eliseev.charm.back.controller;

import ru.eliseev.charm.back.dto.CharmDto;
import ru.eliseev.charm.back.dto.ProfileSimpleDto;
import ru.eliseev.charm.back.mapper.RequestToCharmDtoMapper;
import ru.eliseev.charm.back.service.CharmService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static ru.eliseev.charm.back.utils.UrlUtils.CHARM_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

public class CharmController extends HttpServlet {

	private final CharmService service = CharmService.getInstance();
	private final RequestToCharmDtoMapper mapper = RequestToCharmDtoMapper.getInstance();

	private static final CharmController INSTANCE = new CharmController();

	public static CharmController getInstance() {
		return INSTANCE;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		setNext(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		setNext(req, res);
	}

	private void setNext(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		CharmDto charmDto = mapper.map(req);
		Optional<ProfileSimpleDto> next = service.next(charmDto);
		if (next.isPresent()) {
			req.setAttribute("next", next.get());
			req.getRequestDispatcher(getJspPath(CHARM_URL)).forward(req, res);
		} else {
			req.getRequestDispatcher(getJspPath("/end")).forward(req, res);
		}
	}
}
