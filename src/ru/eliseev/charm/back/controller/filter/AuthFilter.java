package ru.eliseev.charm.back.controller.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.dto.UserDetails;
import ru.eliseev.charm.back.model.Role;

import java.io.IOException;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@WebFilter(value = "/*")
public class AuthFilter implements Filter {

    private static final Set<String> PRIVATE_PATHS = Set.of("/profile", "/credentials");

    private static final Set<String> ADMIN_PATHS = Set.of("/profile");

    private static final Set<String> ENTRY_PATHS = Set.of("/login", "/registration");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String requestURI = req.getRequestURI();
        UserDetails userDetails = (UserDetails) req.getSession().getAttribute("userDetails");
        if (PRIVATE_PATHS.stream().anyMatch(requestURI::startsWith)) {
            if (userDetails == null) {
                res.sendError(SC_UNAUTHORIZED);
            } else if (
                    userDetails.getId().toString().equals(req.getParameter("id")) ||
                    (userDetails.getRole() == Role.ADMIN && ADMIN_PATHS.contains(requestURI))
            ) {
                filterChain.doFilter(req, res);
            } else {
                res.sendError(SC_FORBIDDEN);
            }
        } else if (userDetails != null && ENTRY_PATHS.contains(requestURI)) {
            res.sendRedirect("/profile?id=" + userDetails.getId());
        } else {
            filterChain.doFilter(req, res);
        }

    }
}
