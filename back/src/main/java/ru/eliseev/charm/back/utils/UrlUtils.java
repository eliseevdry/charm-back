package ru.eliseev.charm.back.utils;

import lombok.experimental.UtilityClass;
import ru.eliseev.charm.utils.ConfigFileUtils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class UrlUtils {

    public static final String PROFILE_URL = "/profile";
    public static final String PROFILES_URL = "/profiles";
	public static final String CHARM_URL = "/charm";
    public static final String MATCHES_URL = "/matches";
    public static final String CREDENTIALS_URL = "/credentials";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String REGISTRATION_URL = "/registration";
    public static final String LANG_URL = "/lang";
    public static final String CONTENT_URL = "/content";
    public static final String PDF_URL = "/pdf";
    public static final String REST_URL = "/api/v1";
    public static final String LOGIN_REST_URL = REST_URL + LOGIN_URL;
    public static final String BASE_CONTENT_PATH = ConfigFileUtils.get("app.base-content-path");

    public static final Set<String> PRIVATE_PATHS = Set.of(PROFILE_URL, PROFILES_URL, CREDENTIALS_URL, REST_URL, CHARM_URL, MATCHES_URL);

    public static final Set<String> ENTRY_PATHS = Set.of(LOGIN_URL, REGISTRATION_URL);

    public static String getJspPath(String url) {
        return "/WEB-INF/jsp" + url + ".jsp";
    }

    public static String getProfilePhotoPath(Long id, String fileName) {
        return "/profiles/" + id + "/" + fileName;
    }

    public static String extractFirstPart(String url) {
        if (url == null || url.isEmpty()) {
            return "/";
        }

        String apiPrefix = "";
        if (url.startsWith(REST_URL)) {
            apiPrefix = REST_URL;
            url = url.substring(7);
        }

        String path = url.split("\\?")[0];

        Pattern pattern = Pattern.compile("^(/[^/?]+)");
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            return apiPrefix + matcher.group(1);
        }

        return apiPrefix.isEmpty() ? "/" : apiPrefix;
    }
}
