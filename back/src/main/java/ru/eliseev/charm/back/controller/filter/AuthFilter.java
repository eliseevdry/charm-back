package ru.eliseev.charm.back.controller.filter;

import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.model.Role;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static ru.eliseev.charm.back.utils.UrlUtils.CHARM_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.ENTRY_PATHS;
import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_REST_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.MATCHES_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.PRIVATE_PATHS;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILES_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILE_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REST_URL;

@WebFilter(value = "/*")
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String requestURI = req.getRequestURI();
		UserDetails userDetails = (UserDetails) req.getSession().getAttribute("userDetails");
		if (!LOGIN_REST_URL.equals(requestURI) && PRIVATE_PATHS.stream().anyMatch(requestURI::startsWith)) {
			if (userDetails == null) {
				res.sendError(SC_UNAUTHORIZED);
			} else if ((!requestURI.startsWith(REST_URL) &&
						userDetails.getId().toString().equals(req.getParameter("id"))) ||
					   userDetails.getRole() == Role.ADMIN ||
					   requestURI.startsWith(CHARM_URL) ||
					   requestURI.startsWith(MATCHES_URL)
			) {
				filterChain.doFilter(req, res);
			} else {
				String message =
						String.format("User with id %s and role %s try to use %s endpoint with query parameter %s",
								userDetails.getId(), userDetails.getRole(), requestURI, req.getParameter("id"));
				req.setAttribute("errors", List.of(message));
				res.sendError(SC_FORBIDDEN);
			}
		} else if (userDetails != null && ENTRY_PATHS.contains(requestURI)) {
			String referer = req.getHeader("referer");
			String profileUrl = PROFILE_URL + "?id=" + userDetails.getId();
			if (userDetails.getRole() == Role.ADMIN) {
				String profilesUrl = PROFILES_URL;
				if (referer != null && referer.endsWith(profilesUrl)) {
					res.sendRedirect(profileUrl);
				} else {
					res.sendRedirect(profilesUrl);
				}
			} else {
				if (referer != null && referer.endsWith(profileUrl)) {
					res.sendRedirect(CHARM_URL);
				} else {
					res.sendRedirect(profileUrl);
				}
			}
		} else {
			filterChain.doFilter(req, res);
		}
	}
}
