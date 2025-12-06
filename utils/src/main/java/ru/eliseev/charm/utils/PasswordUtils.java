package ru.eliseev.charm.utils;

import lombok.experimental.UtilityClass;
import org.mindrot.jbcrypt.BCrypt;

import static ru.eliseev.charm.utils.StringUtils.isBlank;

@UtilityClass
public class PasswordUtils {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public static boolean isValidPassword(String password) {
        return !isBlank(password);
    }
}
