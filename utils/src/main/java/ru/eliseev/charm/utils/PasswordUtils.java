package ru.eliseev.charm.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.mindrot.jbcrypt.BCrypt;

import java.security.DrbgParameters;
import java.security.SecureRandom;
import java.util.Objects;

import static java.security.DrbgParameters.Capability.RESEED_ONLY;
import static ru.eliseev.charm.utils.StringUtils.isBlank;

@UtilityClass
public class PasswordUtils {

    @SneakyThrows
    public static String hashPassword(String plainPassword) {
        Objects.requireNonNull(plainPassword, "Must not be null");
        SecureRandom random = SecureRandom.getInstance("DRBG",
            DrbgParameters.instantiation(128, RESEED_ONLY, null));
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(random.nextInt(3, 12), random));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public static boolean isValidPassword(String password) {
        return !isBlank(password);
    }
}
