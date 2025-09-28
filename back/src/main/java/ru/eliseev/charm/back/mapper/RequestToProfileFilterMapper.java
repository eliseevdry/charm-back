package ru.eliseev.charm.back.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dto.ProfileFilter;
import ru.eliseev.charm.back.model.Status;

import jakarta.servlet.http.HttpServletRequest;

import static ru.eliseev.charm.back.config.ConnectionManager.DEFAULT_PAGE;
import static ru.eliseev.charm.back.config.ConnectionManager.DEFAULT_PAGE_SIZE;
import static ru.eliseev.charm.back.config.ConnectionManager.DEFAULT_SORTED_COLUMN;
import static ru.eliseev.charm.utils.StringUtils.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestToProfileFilterMapper implements Mapper<HttpServletRequest, ProfileFilter> {

	private static final RequestToProfileFilterMapper INSTANCE = new RequestToProfileFilterMapper();

	public static RequestToProfileFilterMapper getInstance() {
		return INSTANCE;
	}

	@Override
	public ProfileFilter map(HttpServletRequest req) {
		return map(req, new ProfileFilter());
	}

	@Override
	public ProfileFilter map(HttpServletRequest req, ProfileFilter filter) {
		String emailStartWithArg = req.getParameter("emailStartWith");
		String emailStartWith = isBlank(emailStartWithArg) ? null : emailStartWithArg;
		filter.setEmailStartWith(emailStartWith);

		String nameStartWithArg = req.getParameter("nameStartWith");
		String nameStartWith = isBlank(nameStartWithArg) ? null : nameStartWithArg;
		filter.setNameStartWith(nameStartWith);

		String surnameStartWithArg = req.getParameter("surnameStartWith");
		String surnameStartWith = isBlank(surnameStartWithArg) ? null : surnameStartWithArg;
		filter.setSurnameStartWith(surnameStartWith);

		String ltAgeArg = req.getParameter("ltAge");
		Integer ltAge = isBlank(ltAgeArg) ? null : Integer.parseInt(ltAgeArg);
		filter.setLtAge(ltAge);

		String gteAgeArg = req.getParameter("gteAge");
		Integer gteAge = isBlank(gteAgeArg) ? null : Integer.parseInt(gteAgeArg);
		filter.setGteAge(gteAge);

		String statusArg = req.getParameter("status");
		Status status = isBlank(statusArg) ? null : Status.valueOf(statusArg);
		filter.setStatus(status);

		String sortArg = req.getParameter("sort");
		String sort = isBlank(sortArg) ? DEFAULT_SORTED_COLUMN : sortArg;
		filter.setSort(sort);

		String pageArg = req.getParameter("page");
		Integer page = isBlank(pageArg) ? DEFAULT_PAGE : Integer.parseInt(pageArg);
		filter.setPage(page);

		String pageSizeArg = req.getParameter("pageSize");
		Integer pageSize = isBlank(pageSizeArg) ? DEFAULT_PAGE_SIZE : Integer.parseInt(pageSizeArg);
		filter.setPageSize(pageSize);

		return filter;
	}
}
