package ru.eliseev.charm.back.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.eliseev.charm.back.dto.ProfileUpdateStatusDto;
import ru.eliseev.charm.back.model.Status;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestToProfileUpdateStatusDtoMapper implements Mapper<HttpServletRequest, List<ProfileUpdateStatusDto>> {

	private static final RequestToProfileUpdateStatusDtoMapper INSTANCE = new RequestToProfileUpdateStatusDtoMapper();

	public static RequestToProfileUpdateStatusDtoMapper getInstance() {
		return INSTANCE;
	}

	@Override
	public List<ProfileUpdateStatusDto> map(HttpServletRequest req) {
		return map(req, new ArrayList<>());
	}

	@Override
	@SneakyThrows
	public List<ProfileUpdateStatusDto> map(HttpServletRequest req, List<ProfileUpdateStatusDto> dtos) {
		String[] statusesWithIds = req.getParameterValues("statusesWithIds");
		if (statusesWithIds == null) {
			return dtos;
		}
		for (String statusWithId : statusesWithIds) {
			if ("skip".equals(statusWithId)) {
				continue;
			}
			String[] split = statusWithId.split("_");
			try {
				ProfileUpdateStatusDto dto = new ProfileUpdateStatusDto();
				dto.setId(Long.parseLong(split[1]));
				dto.setStatus(Status.valueOf(split[0]));
				dto.setVersion(Integer.parseInt(split[2]));
				dtos.add(dto);
			} catch (Exception ignored) {
			}
		}
		return dtos;
	}
}
