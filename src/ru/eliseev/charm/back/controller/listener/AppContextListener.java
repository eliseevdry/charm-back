package ru.eliseev.charm.back.controller.listener;

import static ru.eliseev.charm.back.utils.ConnectionManager.AVAILABLE_PAGE_SIZES;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;
import ru.eliseev.charm.back.utils.ConnectionManager;

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
	}
}
