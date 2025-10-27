package ru.eliseev.charm.back;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

@Slf4j
public class CharmBackApp {
	public static void main(String[] args) throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		tomcat.getConnector();

		Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());

		Tomcat.addServlet(ctx, "Embedded", new HttpServlet() {
			@Override
			protected void service(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {

				Writer w = resp.getWriter();
				w.write("Embedded Tomcat servlet.\n");
				w.flush();
				w.close();
			}
		});

		ctx.addServletMappingDecoded("/*", "Embedded");

		tomcat.start();
		log.info("Tomcat started successfully. Server is running. Try accessing http://localhost:8080/");
		tomcat.getServer().await();
	}
}
