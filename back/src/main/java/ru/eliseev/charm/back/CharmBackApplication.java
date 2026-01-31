package ru.eliseev.charm.back;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import ru.eliseev.charm.back.controller.CustomDispatcherServlet;
import ru.eliseev.charm.back.controller.filter.AuthFilter;
import ru.eliseev.charm.back.controller.filter.ErrorFilter;
import ru.eliseev.charm.back.controller.filter.HiddenHttpMethodFilter;
import ru.eliseev.charm.back.controller.filter.LanguageFilter;
import ru.eliseev.charm.back.controller.listener.AppContextListener;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
import static ru.eliseev.charm.back.utils.UrlUtils.getJspPath;

@Slf4j
public class CharmBackApplication {

    public static void main(String[] args) {
        try {
            new CharmBackApplication().start();
        } catch (Exception e) {
            log.error("Failed to start application", e);
            System.exit(1);
        }
    }

    private void start() throws Exception {
        String webContentFolder = getWebappDir();
        log.info("WebApp folder: {}", webContentFolder);

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(Files.createTempDirectory("tomcat-base-dir").toString());
        tomcat.setPort(8080);

        tomcat.getConnector();
        Context ctx = tomcat.addWebapp("", webContentFolder);

        ctx.addWelcomeFile(getJspPath(LOGIN_URL));
        registerErrorPages(ctx);
        ctx.addApplicationListener(AppContextListener.class.getName());
        registerServlets(ctx);
        registerFilters(ctx);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Received shutdown signal");
            try {
                if (tomcat.getServer() != null && tomcat.getServer().getState() == LifecycleState.STARTED) {
                    log.info("Starting graceful shutdown...");
                    for (Service service : tomcat.getServer().findServices()) {
                        for (Connector connector : service.findConnectors()) {
                            connector.pause();
                        }
                    }
                    Thread.sleep(3000);
                    tomcat.stop();
                    tomcat.destroy();
                    log.info("Tomcat stopped gracefully");
                }
            } catch (Exception e) {
                log.error("Error during shutdown: " + e.getMessage(), e);
            }
        }));

        tomcat.start();
        tomcat.getServer().await();
    }

    private void registerServlets(Context ctx) {
        String servletName = "CustomDispatcherServlet";
        Tomcat.addServlet(ctx, servletName, new CustomDispatcherServlet());

        ctx.addServletMappingDecoded(LOGIN_URL, servletName);
        ctx.addServletMappingDecoded(LANG_URL, servletName);
        ctx.addServletMappingDecoded(LOGOUT_URL, servletName);
        ctx.addServletMappingDecoded(MATCHES_URL, servletName);
        ctx.addServletMappingDecoded(PROFILE_URL + ANY_URL, servletName);
        ctx.addServletMappingDecoded(PROFILES_URL, servletName);
        ctx.addServletMappingDecoded(REGISTRATION_URL, servletName);
        ctx.addServletMappingDecoded(CREDENTIALS_URL, servletName);
        ctx.addServletMappingDecoded(CONTENT_URL + ANY_URL, servletName);
        ctx.addServletMappingDecoded(CHARM_URL, servletName);
        ctx.addServletMappingDecoded(REST_URL + PROFILES_URL, servletName);
        ctx.addServletMappingDecoded(REST_URL + PROFILE_URL, servletName);
        ctx.addServletMappingDecoded(REST_URL + MATCHES_URL, servletName);
        ctx.addServletMappingDecoded(LOGIN_REST_URL, servletName);
        ctx.addServletMappingDecoded(REST_URL + CHARM_URL, servletName);
    }

    private void registerFilters(Context ctx) {
        registerFilter(ctx, new LanguageFilter(), Collections.emptyList());
        registerFilter(ctx, new HiddenHttpMethodFilter(), List.of(DispatcherType.FORWARD, DispatcherType.REQUEST));
        registerFilter(ctx, new AuthFilter(), Collections.emptyList());
        registerFilter(ctx, new ErrorFilter(), List.of(DispatcherType.ERROR));
    }

    private void registerFilter(Context context, Filter filter, List<DispatcherType> dispatcherTypes) {
        Class<? extends Filter> filterClass = filter.getClass();
        String filterName = filterClass.getSimpleName();

        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName(filterName);
        filterDef.setFilterClass(filterClass.getName());
        filterDef.setFilter(filter);
        context.addFilterDef(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName(filterName);
        filterMap.addURLPattern(ANY_URL);
        for (DispatcherType dispatcherType : dispatcherTypes) {
            filterMap.setDispatcher(dispatcherType.name());
        }
        context.addFilterMap(filterMap);
    }

    private void registerErrorPages(Context context) {
        for (Integer errorCode : List.of(400, 401, 403, 404, 405, 500)) {
            ErrorPage errorPage = getErrorPage(errorCode);
            context.addErrorPage(errorPage);
        }
    }

    private ErrorPage getErrorPage(int errorCode) {
        ErrorPage errorPage = new ErrorPage();
        if (errorCode != 500) {
            errorPage.setErrorCode(errorCode);
        } else {
            errorPage.setExceptionType("java.lang.Exception");
        }
        errorPage.setLocation(getJspPath("/error/" + errorCode));
        return errorPage;
    }

    private String getWebappDir() throws IOException {
        Path tempDir = Files.createTempDirectory("tomcat-webapp");
        extractWebappResources(tempDir);
        return tempDir.toAbsolutePath().toString();
    }

    private void extractWebappResources(Path tempDir) throws IOException {
        // Получаем URL ресурса webapp
        URL webappResource = getClass().getClassLoader().getResource("webapp");
        if (webappResource == null) {
            log.warn("No webapp resources found in classpath");
            return;
        }

        // Если это JAR файл
        if (webappResource.getProtocol().equals("jar")) {
            String path = webappResource.getPath();
            log.info("JAR: {}", path);
            String jarPath = path.substring(5, path.indexOf("!"));
            try (JarFile jarFile = new JarFile(jarPath)) {
                jarFile.stream()
                    .filter(entry -> entry.getName().startsWith("webapp/"))
                    .forEach(entry -> extractJarEntry(jarFile, entry, tempDir));
            }
        } else {
            // Если это обычная директория (режим разработки)
            File webappDir = new File(webappResource.getFile());
            Path path = webappDir.toPath();
            log.info("webapp resources: {}", path);
            copyDirectory(path, tempDir);
        }
    }

    private void extractJarEntry(JarFile jarFile, JarEntry entry, Path tempDir) {
        try {
            Path targetPath = tempDir.resolve(entry.getName().substring("webapp/".length()));

            if (entry.isDirectory()) {
                Files.createDirectories(targetPath);
            } else {
                Files.createDirectories(targetPath.getParent());
                try (InputStream inputStream = jarFile.getInputStream(entry)) {
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            log.error("Failed to extract jar entry: {}", entry.getName(), e);
        }
    }

    private void copyDirectory(Path source, Path target) throws IOException {
        Files.walk(source)
            .forEach(sourcePath -> {
                try {
                    Path targetPath = target.resolve(source.relativize(sourcePath));
                    if (Files.isDirectory(sourcePath)) {
                        Files.createDirectories(targetPath);
                    } else {
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    log.error("Failed to copy file: {}", sourcePath, e);
                }
            });
    }
}