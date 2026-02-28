package ru.eliseev.charm.back;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.codehaus.plexus.archiver.UnArchiver;
import org.codehaus.plexus.archiver.jar.JarUnArchiver;
import ru.eliseev.charm.back.config.CharmServletContainerInitializer;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static ru.eliseev.charm.back.utils.UrlUtils.WEBAPP_DIR;

@Slf4j
public class CharmBackApplication {

    public static void main(String[] args) {
        new CharmBackApplication().start();
    }

    @SneakyThrows
    private void start() {
        Path tmpBaseDir = Files.createTempDirectory("tomcat-base-dir");
        String webappDir = getWebappDir(tmpBaseDir);

        Tomcat tomcat = new Tomcat();

        tomcat.setBaseDir(tmpBaseDir.toString());
        tomcat.setPort(8080);

        tomcat.getConnector();
        Context ctx = tomcat.addWebapp("", webappDir);
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

    @SneakyThrows
    private String getWebappDir(Path tempDir) {
        URL webappResource = getClass().getClassLoader().getResource(WEBAPP_DIR);
        String webappResourcePath = webappResource.getPath();
        log.info("webapp path: {}", webappResourcePath);
        if (webappResource.getProtocol().equals("jar")) {
            UnArchiver unArchiver = new JarUnArchiver();
            unArchiver.setSourceFile(getJarFile(webappResource));
            unArchiver.extract(WEBAPP_DIR, tempDir.toFile());
            return tempDir.resolve(WEBAPP_DIR).toString();
        } else {
            return webappResourcePath;
        }
    }

    private File getJarFile(URL webappResource) {
        try {
            JarURLConnection urlConnection = (JarURLConnection) webappResource.openConnection();
            return new File(urlConnection.getJarFileURL().getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}