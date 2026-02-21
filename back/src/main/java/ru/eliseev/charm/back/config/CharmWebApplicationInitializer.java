package ru.eliseev.charm.back.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import java.util.Set;

public class CharmWebApplicationInitializer implements ServletContainerInitializer, WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        onStartup(Set.of(), servletContext);
    }

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) {
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setConfigLocation("/WEB-INF/applicationContext.xml");

        var servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.setLoadOnStartup(0);
        servlet.addMapping("/");
    }


}
