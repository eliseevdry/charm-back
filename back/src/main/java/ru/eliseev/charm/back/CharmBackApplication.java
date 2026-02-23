package ru.eliseev.charm.back;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import ru.eliseev.charm.back.config.CharmServletContainerInitializer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class CharmBackApplication {

    public static void main(String[] args) {
        new CharmBackApplication().start();
    }

    @SneakyThrows
    private void start() {
        Path webappDir = Paths.get(getClass().getClassLoader().getResource("webapp").toURI());
        Path tmpBaseDir = Files.createTempDirectory("tomcat-base-dir");

        Tomcat tomcat = new Tomcat();

        tomcat.setBaseDir(tmpBaseDir.toString());
        tomcat.setPort(8080);

        tomcat.getConnector();
        Context ctx = tomcat.addWebapp("", webappDir.toString());
        ctx.addServletContainerInitializer(new CharmServletContainerInitializer(), getWebClasses());

        tomcat.start();
        tomcat.getServer().await();
    }

    @SneakyThrows
    private Set<Class<?>> getWebClasses() {
        Set<Class<?>> classes = new HashSet<>();
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("ru.eliseev.charm.back.controller").scan()) {
            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                Class<?> clazz = Class.forName(classInfo.getName());
                classes.add(clazz);
            }
        }
        return classes;
    }
}