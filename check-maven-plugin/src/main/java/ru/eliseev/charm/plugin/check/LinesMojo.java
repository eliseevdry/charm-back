package ru.eliseev.charm.plugin.check;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import ru.eliseev.charm.plugin.check.model.FileAnalysisResult;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * Maven plugin to check Java class line count
 */
@Mojo(name = "lines")
public class LinesMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "maxLines", defaultValue = "350")
    private int maxLines;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Checking Java class line counts...");
        getLog().info("Maximum allowed lines: " + maxLines);
        List<FileAnalysisResult> results = analyzeJavaFiles(project.getBasedir().toPath());
        printResults(results);

        if (results.stream().anyMatch(FileAnalysisResult::exceedsLimit)) {
            throw new MojoFailureException("One or more Java classes exceed the maximum line limit of " + maxLines);
        }
    }

    private List<FileAnalysisResult> analyzeJavaFiles(Path baseDir) throws MojoExecutionException {
        try (Stream<Path> walkStream = Files.walk(baseDir)) {
            return walkStream
                .filter(p -> p.toString().endsWith(".java"))
                .map(javaFile -> {
                    try (Stream<String> lines = Files.lines(javaFile)) {
                        long lineCount = lines.count();
                        return new FileAnalysisResult(
                            baseDir.relativize(javaFile).toString(),
                            lineCount,
                            lineCount > maxLines
                        );
                    } catch (IOException e) {
                        throw new UncheckedIOException("Error reading file: " + javaFile, e);
                    }
                })
                .toList();
        } catch (IOException | UncheckedIOException e) {
            throw new MojoExecutionException("Error analyzing Java files", e);
        }
    }

    private void printResults(List<FileAnalysisResult> results) {
        getLog().info("=== Java Class Line Count Analysis ===");

        int exceedingCount = 0;
        for (FileAnalysisResult result : results) {
            if (result.exceedsLimit()) {
                getLog().warn(result.toString());
                exceedingCount++;
            } else {
                getLog().info(result.toString());
            }
        }

        getLog().info("======================================");
        getLog().info("Total files analyzed: " + results.size());
        getLog().info("Files exceeding limit: " + exceedingCount);
        getLog().info("======================================");
    }
}