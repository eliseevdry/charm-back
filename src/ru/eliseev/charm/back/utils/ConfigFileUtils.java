package ru.eliseev.charm.back.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConfigFileUtils {
	private static final Properties PROPERTIES = new Properties();

	static {
		loadProperties();
	}

	public static String get(String key) {
		return PROPERTIES.getProperty(key);
	}

	private static void loadProperties() {
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
			PROPERTIES.load(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
