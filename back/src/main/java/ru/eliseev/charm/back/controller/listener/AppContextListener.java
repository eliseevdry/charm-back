package ru.eliseev.charm.back.controller.listener;

import ru.eliseev.charm.back.config.ConnectionManager;
import ru.eliseev.charm.back.config.RedisManager;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import static ru.eliseev.charm.back.config.ConnectionManager.AVAILABLE_PAGE_SIZES;

@WebListener
public class AppContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		if (servletContext.getAttribute("genders") == null) {
			servletContext.setAttribute("genders", Gender.values());
		}
		if (servletContext.getAttribute("statuses") == null) {
			servletContext.setAttribute("statuses", Status.values());
		}
		if (servletContext.getAttribute("availablePageSizes") == null) {
			servletContext.setAttribute("availablePageSizes", AVAILABLE_PAGE_SIZES);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConnectionManager.closeDataSource();
		RedisManager.close();
	}
}
