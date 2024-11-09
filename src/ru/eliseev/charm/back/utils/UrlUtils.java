package ru.eliseev.charm.back.utils;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class UrlUtils {

    public static final String PROFILE_URL = "/profile";
    public static final String CREDENTIALS_URL = "/credentials";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String REGISTRATION_URL = "/registration";
    public static final String LANG_URL = "/lang";
    public static final String CONTENT_URL = "/content";
    public static final String REST_URL = "/api/v1";
    public static final String LOGIN_REST_URL = REST_URL + LOGIN_URL;
    public static final String BASE_CONTENT_PATH = "/Users/andrey.s.eliseev/Downloads";

    public static final Set<String> PRIVATE_PATHS = Set.of(PROFILE_URL, CREDENTIALS_URL, REST_URL);

    public static final Set<String> ENTRY_PATHS = Set.of(LOGIN_URL, REGISTRATION_URL);

    public static String getJspPath(String url) {
        return "/WEB-INF/jsp" + url + ".jsp";
    }

    public static String getProfilePhotoPath(Long id, String fileName) {
        return "/profiles/" + id + "/" + fileName;
    }
}
