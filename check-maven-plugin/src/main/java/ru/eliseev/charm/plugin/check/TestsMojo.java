package ru.eliseev.charm.plugin.check;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Testing Java using JUnit Platform
 */
@Mojo(name = "tests", defaultPhase = LifecyclePhase.TEST, requiresDependencyResolution = ResolutionScope.TEST)
public class TestsMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.testClasspathElements}", readonly = true)
    private List<String> testClasspathElements;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Start Java tests...");

        URL[] urls = testClasspathElements.stream()
            .map(element -> {
                try {
                    return Path.of(element).toUri().toURL();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to convert classpath element: " + element, e);
                }
            })
            .toArray(URL[]::new);

        ClassLoader projectClassLoader = new URLClassLoader(urls, getClass().getClassLoader());
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();

        try {
            Thread.currentThread().setContextClassLoader(projectClassLoader);

            Launcher launcher = LauncherFactory.create();
            SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();

            Set<Path> classpathRoots = testClasspathElements.stream()
                .map(Path::of)
                .collect(Collectors.toSet());

            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClasspathRoots(classpathRoots))
                // TODO добавить поддержку фильтрации по тегам
                .filters(TagFilter.includeTags("fast"))
                .build();

            launcher.execute(request, summaryGeneratingListener);

            TestExecutionSummary summary = summaryGeneratingListener.getSummary();
            getLog().info("Tests found: " + summary.getTestsStartedCount());
            long testsFailedCount = summary.getTestsFailedCount();
            if (testsFailedCount > 0) {
                getLog().error("Tests failed: " +
                    summary.getFailures().stream().map(it -> it.getTestIdentifier().getDisplayName()).collect(Collectors.joining(", ")));
                throw new MojoFailureException(testsFailedCount + " tests failed!");
            }
            Duration duration = Duration.ofMillis(summary.getTimeFinished() - summary.getTimeStarted());
            getLog().info("Total time: " + duration.toMillis() + " ms");

            getLog().info("End Java tests...");
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }
}