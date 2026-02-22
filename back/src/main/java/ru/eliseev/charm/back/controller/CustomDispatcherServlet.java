package ru.eliseev.charm.back.controller;

import org.springframework.web.context.WebApplicationContext;
import ru.eliseev.charm.back.controller.rest.CharmRestController;
import ru.eliseev.charm.back.controller.rest.LoginRestController;
import ru.eliseev.charm.back.controller.rest.MatchesRestController;
import ru.eliseev.charm.back.controller.rest.ProfileRestController;
import ru.eliseev.charm.back.controller.rest.ProfilesRestController;
import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.eliseev.charm.back.utils.ConnectionUtils.AVAILABLE_PAGE_SIZES;
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
import static ru.eliseev.charm.back.utils.UrlUtils.extractFirstPart;

public class CustomDispatcherServlet extends HttpServlet {
    private LoginController loginController;
    private LanguageController languageController;
    private LogoutController logoutController;
    private MatchesController matchesController;
    private ProfileController profileController;
    private ProfilesController profilesController;
    private RegistrationController registrationController;
    private CredentialsController credentialsController;
    private ContentController contentController;
    private CharmController charmController;
    private ProfilesRestController profilesRestController;
    private ProfileRestController profileRestController;
    private MatchesRestController matchesRestController;
    private LoginRestController loginRestController;
    private CharmRestController charmRestController;
    private final WebApplicationContext webApplicationContext;

    public CustomDispatcherServlet(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        if (servletContext.getAttribute("genders") == null) {
            servletContext.setAttribute("genders", Gender.values());
        }
        if (servletContext.getAttribute("statuses") == null) {
            servletContext.setAttribute("statuses", Status.values());
        }
        if (servletContext.getAttribute("availablePageSizes") == null) {
            servletContext.setAttribute("availablePageSizes", AVAILABLE_PAGE_SIZES);
        }
        loginController = (LoginController) webApplicationContext.getBean("loginController");
        languageController = (LanguageController) webApplicationContext.getBean("languageController");
        logoutController = (LogoutController) webApplicationContext.getBean("logoutController");
        matchesController = (MatchesController) webApplicationContext.getBean("matchesController");
        profileController = (ProfileController) webApplicationContext.getBean("profileController");
        profilesController = (ProfilesController) webApplicationContext.getBean("profilesController");
        registrationController = (RegistrationController) webApplicationContext.getBean("registrationController");
        credentialsController = (CredentialsController) webApplicationContext.getBean("credentialsController");
        contentController = (ContentController) webApplicationContext.getBean("contentController");
        charmController = (CharmController) webApplicationContext.getBean("charmController");
        profilesRestController = (ProfilesRestController) webApplicationContext.getBean("profilesRestController");
        profileRestController = (ProfileRestController) webApplicationContext.getBean("profileRestController");
        matchesRestController = (MatchesRestController) webApplicationContext.getBean("matchesRestController");
        loginRestController = (LoginRestController) webApplicationContext.getBean("loginRestController");
        charmRestController = (CharmRestController) webApplicationContext.getBean("charmRestController");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstUrlPart = extractFirstPart(req.getRequestURI());
        HttpServlet controller = switch (firstUrlPart) {
            case LOGIN_URL -> loginController;
            case LANG_URL -> languageController;
            case LOGOUT_URL -> logoutController;
            case MATCHES_URL -> matchesController;
            case PROFILE_URL -> profileController;
            case PROFILES_URL -> profilesController;
            case REGISTRATION_URL -> registrationController;
            case CREDENTIALS_URL -> credentialsController;
            case CONTENT_URL -> contentController;
            case CHARM_URL -> charmController;
            case REST_URL + PROFILES_URL -> profilesRestController;
            case REST_URL + PROFILE_URL -> profileRestController;
            case REST_URL + MATCHES_URL -> matchesRestController;
            case LOGIN_REST_URL -> loginRestController;
            case REST_URL + CHARM_URL -> charmRestController;
            case null, default -> null;
        };
        if (controller == null) {
            super.service(req, resp);
        } else {
            controller.service(req, resp);
        }
    }
}
