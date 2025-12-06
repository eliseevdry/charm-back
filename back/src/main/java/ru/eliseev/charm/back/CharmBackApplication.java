package ru.eliseev.charm.back;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Service;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.nio.file.Files;

@Slf4j
public class CharmBackApplication {

	public static void main(String[] args) throws Exception {
		String mainRootFolder = getMainRootFolder().getAbsolutePath();
		log.info("Root folder: {}", mainRootFolder);
		String webContentFolder = new File(mainRootFolder, "src/main/webapp/").getAbsolutePath();
		log.info("WebApp folder: {}", webContentFolder);
		String classesFolder = new File(mainRootFolder, "target/classes").getAbsolutePath();
		log.info("Classes folder: {}", classesFolder);

		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir(Files.createTempDirectory("tomcat-base-dir").toString());
		tomcat.setPort(8080);

		tomcat.getConnector();
		Context ctx = tomcat.addWebapp("", webContentFolder);

		WebResourceRoot webResourceRoot = new StandardRoot(ctx);
		WebResourceSet webResourceSet = new DirResourceSet(webResourceRoot, "/WEB-INF/classes", classesFolder, "/");
		webResourceRoot.addPreResources(webResourceSet);
		ctx.setResources(webResourceRoot);

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

	@SneakyThrows
	private static File getMainRootFolder() {
		String runningJarPath = CharmBackApplication.class
			.getProtectionDomain()
			.getCodeSource()
			.getLocation()
			.toURI()
			.getPath()
			.replaceAll("\\\\", "/");
		log.info("RUNNING JAR PATH: {}", runningJarPath);
		int lastIndexOf = runningJarPath.lastIndexOf("/target/");
		return new File(runningJarPath.substring(0, lastIndexOf));
	}
}