package ru.eliseev.charm.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class StringUtils {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    public static boolean isValidEmail(String email) {
        if (isBlank(email)) return false;
        return VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return !isBlank(password);
    }
}
