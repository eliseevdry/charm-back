package ru.eliseev.charm.back.controller.filter;

import static jakarta.servlet.DispatcherType.FORWARD;
import static jakarta.servlet.DispatcherType.REQUEST;
import static ru.eliseev.charm.back.utils.ConnectionManager.AVAILABLE_PAGE_SIZES;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;
import static ru.eliseev.charm.back.utils.UrlUtils.REST_URL;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;

@WebFilter(value = "/*", dispatcherTypes = {FORWARD, REQUEST})
public class HiddenHttpMethodFilter implements Filter {

	private static final String METHOD_PARAM = "_method";
	private final ProfileDao profileDao = ProfileDao.getInstance();

	@Override
	public void init(FilterConfig filterConfig) {
		ServletContext servletContext = filterConfig.getServletContext();
		if (servletContext.getAttribute("genders") == null) {
			servletContext.setAttribute("genders", Gender.values());
		}
		if (servletContext.getAttribute("statuses") == null) {
			servletContext.setAttribute("statuses", Status.values());
		}
		if (servletContext.getAttribute("availablePageSizes") == null) {
			servletContext.setAttribute("availablePageSizes", AVAILABLE_PAGE_SIZES);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (req.getDispatcherType() != REQUEST && req instanceof HttpMethodRequestWrapper wrapper) {
			req = (HttpServletRequest) wrapper.getRequest();
		} else {
			String paramValue = req.getParameter(METHOD_PARAM);

			if ("POST".equals(req.getMethod()) && !isBlank(paramValue)) {
				String method = paramValue.toUpperCase(Locale.ENGLISH);
				req = new HttpMethodRequestWrapper(req, method);
			}
		}
		if (req.getRequestURI().startsWith(REST_URL)) {
			res.setContentType("application/json");
		}
		res.setCharacterEncoding("UTF-8");
		filterChain.doFilter(req, res);
	}

	/**
	 * Simple {@link HttpServletRequest} wrapper that returns the supplied
	 * method for {@link HttpServletRequest#getMethod()}.
	 */
	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

		private final String method;

		public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}
}
