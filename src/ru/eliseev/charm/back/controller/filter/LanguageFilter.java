package ru.eliseev.charm.back.controller.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.eliseev.charm.back.service.WordBundle;

import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class LanguageFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[]{};

        String lang = Arrays.stream(cookies)
                .filter(cookie -> "lang".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("en");

        WordBundle wordBundle = new WordBundle(lang);

        req.setAttribute("wordBundle", wordBundle);

        filterChain.doFilter(req, res);
    }
}
