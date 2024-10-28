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
import lombok.extern.slf4j.Slf4j;
import ru.eliseev.charm.back.utils.WordBundle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static jakarta.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@WebFilter(value = "/*", dispatcherTypes = DispatcherType.ERROR)
@Slf4j
public class ErrorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HashMap<String, String> errorMap = new HashMap<>();
        Object errors = req.getAttribute("errors");
        if (errors instanceof List<?>) {
            WordBundle wordBundle = (WordBundle) req.getAttribute("wordBundle");
            for (int i = 0; i < ((List<?>) errors).size(); i++) {
                errorMap.put("message" + i, wordBundle.getWord(((List<?>) errors).get(i).toString()));
            }
        }

        if (res.getStatus() >= SC_INTERNAL_SERVER_ERROR) {
            UUID errorUuid = UUID.randomUUID();
            req.setAttribute("errorUuid", errorUuid);
            Throwable e = (Throwable) req.getAttribute(ERROR_EXCEPTION);
            log.error("Unexpected error {}:", errorUuid, e);
        } else {
            log.warn("Code: {}; Errors: {}", res.getStatus(), errorMap);
        }

        filterChain.doFilter(req, res);
    }
}
