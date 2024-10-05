package ru.eliseev.charm.back.controller.filter;

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
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;

import java.io.IOException;
import java.util.Locale;

import static jakarta.servlet.DispatcherType.FORWARD;
import static jakarta.servlet.DispatcherType.REQUEST;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;

@WebFilter(value = "/*", dispatcherTypes = {FORWARD, REQUEST})
public class HiddenHttpMethodFilter implements Filter {

    private static final String METHOD_PARAM = "_method";

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        if (servletContext.getAttribute("genders") == null) {
            servletContext.setAttribute("genders", Gender.values());
        }
        if (servletContext.getAttribute("statuses") == null) {
            servletContext.setAttribute("statuses", Status.values());
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getDispatcherType() != REQUEST && request instanceof HttpMethodRequestWrapper wrapper) {
            request = (HttpServletRequest) wrapper.getRequest();
        } else {
            String paramValue = request.getParameter(METHOD_PARAM);

            if ("POST".equals(request.getMethod()) && !isBlank(paramValue)) {
                String method = paramValue.toUpperCase(Locale.ENGLISH);
                request = new HttpMethodRequestWrapper(request, method);
            }
        }

        filterChain.doFilter(request, response);
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
