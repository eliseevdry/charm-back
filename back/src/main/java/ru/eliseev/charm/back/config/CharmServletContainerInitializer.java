package ru.eliseev.charm.back.config;

import lombok.SneakyThrows;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

public class CharmServletContainerInitializer implements ServletContainerInitializer {
    @SneakyThrows
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext ctx) {
        if (set == null) {
            return;
        }
        for (Class<?> clazz : set) {
            String className = clazz.getName();
            String simpleClassName = clazz.getSimpleName();
            if (clazz.isAnnotationPresent(WebListener.class)) {
                ctx.addListener(className);
            } else if (clazz.isAnnotationPresent(WebFilter.class)) {
                var filter = ctx.addFilter(simpleClassName, (Filter) clazz.getDeclaredConstructor().newInstance());
                Collection<DispatcherType> dispatcherTypes = new ArrayList<>(Arrays.asList(clazz.getAnnotation(WebFilter.class).dispatcherTypes()));
                filter.addMappingForUrlPatterns(EnumSet.copyOf(dispatcherTypes), false, "/*");
            } else if (clazz.isAnnotationPresent(WebServlet.class)) {
                HttpServlet servletInstance = (HttpServlet) clazz.getDeclaredConstructor().newInstance();
                var servlet = ctx.addServlet(simpleClassName, servletInstance);
                servlet.addMapping(clazz.getAnnotation(WebServlet.class).value());
                if (clazz.isAnnotationPresent(MultipartConfig.class)) {
                    servlet.setMultipartConfig(new MultipartConfigElement(""));
                }
            }
        }
    }
}
