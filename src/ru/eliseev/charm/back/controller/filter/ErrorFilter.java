package ru.eliseev.charm.back.controller.filter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static jakarta.servlet.RequestDispatcher.ERROR_EXCEPTION;

@WebFilter(value = "/*", dispatcherTypes = DispatcherType.ERROR)
public class ErrorFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        Throwable e = (Throwable) req.getAttribute(ERROR_EXCEPTION);
        
        if (res.getStatus() > 500) {
            log.error("Unexpected error: ", e);
        } else {
            log.error("{} error", res.getStatus());
        }
        
        filterChain.doFilter(req, res);
    }
}
