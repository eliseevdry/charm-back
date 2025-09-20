package ru.eliseev.charm.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class ConfigFileUtils {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static boolean getFeatureFlag(String ffKey) {
        String ff = PROPERTIES.getProperty("app.ff." + ffKey);
        return Boolean.parseBoolean(ff);
    }

    private static void loadProperties() {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
