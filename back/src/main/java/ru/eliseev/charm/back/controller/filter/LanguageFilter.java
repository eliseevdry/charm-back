package ru.eliseev.charm.back.controller.filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.eliseev.charm.back.service.bundle.WordBundle;
import ru.eliseev.charm.back.service.bundle.WordBundleEn;
import ru.eliseev.charm.back.service.bundle.WordBundleRu;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class LanguageFilter implements Filter {

    private WordBundleRu wordBundleRu;
    private WordBundleEn wordBundleEn;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (wordBundleEn == null || wordBundleRu == null) {
            WebApplicationContext webApplicationContext =
                WebApplicationContextUtils.findWebApplicationContext(request.getServletContext());
            wordBundleRu = (WordBundleRu) webApplicationContext.getBean("wordBundleRu");
            wordBundleEn = (WordBundleEn) webApplicationContext.getBean("wordBundleEn");
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[]{};

        String lang = Arrays.stream(cookies)
            .filter(cookie -> "lang".equals(cookie.getName()))
            .map(Cookie::getValue)
            .findFirst()
            .orElse("en");

        WordBundle wordBundle = "ru".equals(lang) ? wordBundleRu : wordBundleEn;

        req.setAttribute("wordBundle", wordBundle);

        filterChain.doFilter(req, res);
    }
}
