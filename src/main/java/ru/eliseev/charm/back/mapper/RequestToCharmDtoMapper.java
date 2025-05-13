package ru.eliseev.charm.back.mapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dto.Action;
import ru.eliseev.charm.back.dto.CharmDto;
import ru.eliseev.charm.back.dto.UserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestToCharmDtoMapper implements Mapper<HttpServletRequest, CharmDto> {

	private static final RequestToCharmDtoMapper INSTANCE = new RequestToCharmDtoMapper();

	public static RequestToCharmDtoMapper getInstance() {
		return INSTANCE;
	}

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
