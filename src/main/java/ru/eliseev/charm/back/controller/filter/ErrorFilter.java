package ru.eliseev.charm.back.controller.filter;

import com.fasterxml.jackson.databind.DatabindException;
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
import ru.eliseev.charm.back.mapper.JsonMapper;
import ru.eliseev.charm.back.service.bundle.WordBundle;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static jakarta.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static jakarta.servlet.RequestDispatcher.ERROR_REQUEST_URI;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static ru.eliseev.charm.back.utils.UrlUtils.REST_URL;

@WebFilter(value = "/*", dispatcherTypes = DispatcherType.ERROR)
@Slf4j
public class ErrorFilter implements Filter {

    private final JsonMapper jsonMapper = JsonMapper.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

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

        if (((String) req.getAttribute(ERROR_REQUEST_URI)).startsWith(REST_URL)) {
            if (!errorMap.isEmpty()) {
                try (PrintWriter writer = res.getWriter()) {
                    jsonMapper.writeValue(writer, errorMap);
                } catch (DatabindException ex) {
                    throw new IOException(ex);
                }
            }
        } else {
            filterChain.doFilter(req, res);
        }
    }
}
