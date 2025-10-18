package ru.eliseev.charm.back.controller;

import ru.eliseev.charm.back.controller.rest.CharmRestController;
import ru.eliseev.charm.back.controller.rest.LoginRestController;
import ru.eliseev.charm.back.controller.rest.MatchesRestController;
import ru.eliseev.charm.back.controller.rest.ProfileRestController;
import ru.eliseev.charm.back.controller.rest.ProfilesRestController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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

@WebServlet({LOGIN_URL, LANG_URL, LOGOUT_URL, MATCHES_URL, PROFILE_URL + "/*", PROFILES_URL, REGISTRATION_URL, CREDENTIALS_URL, CONTENT_URL + "/*",
	CHARM_URL, REST_URL + PROFILES_URL, REST_URL + PROFILE_URL, REST_URL + MATCHES_URL, LOGIN_REST_URL, REST_URL + CHARM_URL
})
@MultipartConfig
public class DispatcherServlet extends HttpServlet {

	private final LoginController loginController = LoginController.getInstance();
	private final LanguageController languageController = LanguageController.getInstance();
	private final LogoutController logoutController = LogoutController.getInstance();
	private final MatchesController matchesController = MatchesController.getInstance();
	private final ProfileController profileController = ProfileController.getInstance();
	private final ProfilesController profilesController = ProfilesController.getInstance();
	private final RegistrationController registrationController = RegistrationController.getInstance();
	private final CredentialsController credentialsController = CredentialsController.getInstance();
	private final ContentController contentController = ContentController.getInstance();
	private final CharmController charmController = CharmController.getInstance();
	private final ProfilesRestController profilesRestController = ProfilesRestController.getInstance();
	private final ProfileRestController profileRestController = ProfileRestController.getInstance();
	private final MatchesRestController matchesRestController = MatchesRestController.getInstance();
	private final LoginRestController loginRestController = LoginRestController.getInstance();
	private final CharmRestController charmRestController = CharmRestController.getInstance();

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
