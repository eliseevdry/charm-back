package ru.eliseev.charm.back.mapper;

import ru.eliseev.charm.back.dto.Action;
import ru.eliseev.charm.back.dto.CharmDto;
import ru.eliseev.charm.back.dto.UserDetails;

import jakarta.servlet.http.HttpServletRequest;

public class RequestToCharmDtoMapper implements Mapper<HttpServletRequest, CharmDto> {

	@Override
	public CharmDto map(HttpServletRequest req) {
		return map(req, new CharmDto());
	}

	@Override
	public CharmDto map(HttpServletRequest req, CharmDto dto) {
		UserDetails userDetails = (UserDetails) req.getSession().getAttribute("userDetails");
		dto.setFromProfile(userDetails.getId());
		String toProfile = req.getParameter("toProfile");
		if (toProfile != null) {
			dto.setToProfile(Long.parseLong(toProfile));
		}
		String action = req.getParameter("action");
		if (action != null) {
			dto.setAction(Action.valueOf(action));
		} else {
			dto.setAction(Action.SKIP);
		}
		return dto;
	}
}
