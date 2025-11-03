package ru.eliseev.charm.back;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

@Slf4j
public class CharmBackApp {
	public static void main(String[] args) throws LifecycleException {
		String docBase = "/Users/andrey.s.eliseev/IdeaProjects/charm/charm-back/back/src/main/webapp";

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		tomcat.addWebapp("/", new File(docBase).getAbsolutePath());

		tomcat.start();
		tomcat.getServer().await();
	}
}
