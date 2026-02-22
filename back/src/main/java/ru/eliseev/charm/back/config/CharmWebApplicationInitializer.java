package ru.eliseev.charm.back.config;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.eliseev.charm.back.controller.CustomDispatcherServlet;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import java.util.Set;

import static ru.eliseev.charm.back.utils.UrlUtils.ANY_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.CHARM_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.CONTENT_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.CREDENTIALS_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.LANG_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_REST_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.LOGIN_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.LOGOUT_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.MATCHES_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILES_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.PROFILE_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REGISTRATION_URL;
import static ru.eliseev.charm.back.utils.UrlUtils.REST_URL;

public class CharmWebApplicationInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebAppConfig.class);
        var dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        dispatcher.setLoadOnStartup(0);
        dispatcher.addMapping("/");

        var customDispatcher = servletContext.addServlet("customDispatcher", new CustomDispatcherServlet(ctx));
        customDispatcher.setLoadOnStartup(1);
        customDispatcher.addMapping(LOGIN_URL, LANG_URL, LOGOUT_URL, MATCHES_URL, PROFILE_URL + ANY_URL, PROFILES_URL, REGISTRATION_URL,
            CREDENTIALS_URL, CONTENT_URL + ANY_URL, CHARM_URL, REST_URL + PROFILES_URL, REST_URL + PROFILE_URL, REST_URL + MATCHES_URL,
            LOGIN_REST_URL, REST_URL + CHARM_URL);
    }
}
