package ru.eliseev.charm.back;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.SneakyThrows;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import ru.eliseev.charm.back.config.CharmServletContainerInitializer;

import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public class CharmBackApplication {
    public static void main(String[] args) {
        new CharmBackApplication().start();
    }

    @SneakyThrows
    private void start() {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("tomcat-base").toString());
        tomcat.setPort(8080);

        tomcat.getConnector();

        Context context = tomcat.addWebapp("", getClass().getClassLoader().getResource("webapp/").getPath());

        context.addServletContainerInitializer(new CharmServletContainerInitializer(), getWebClasses());

        tomcat.start();
        tomcat.getServer().await();
    }

    @SneakyThrows
    private Set<Class<?>> getWebClasses() {
        Set<Class<?>> classes = new HashSet<>();
        try (ScanResult scanResult = new ClassGraph().acceptPackages("ru.eliseev.charm.back.controller").scan()) {
            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                Class<?> clazz = Class.forName(classInfo.getName());
                classes.add(clazz);
            }
        }
        return classes;
    }
}
