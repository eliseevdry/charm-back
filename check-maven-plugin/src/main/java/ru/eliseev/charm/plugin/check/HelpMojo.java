package ru.eliseev.charm.plugin.check;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "help")
public class HelpMojo extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("This plugin is for validate project. Main goal - check.");
        getLog().info("Available goals:");
        getLog().info("  - lines: Check Java class line counts (Validate phase)");
        getLog().info("  - tests: Run Java tests using JUnit Platform (Test phase)");
    }
}
