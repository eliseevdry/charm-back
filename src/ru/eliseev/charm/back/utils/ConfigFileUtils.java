package ru.eliseev.charm.back.utils;

import static java.util.Collections.singletonMap;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.Yaml;

@UtilityClass
public class ConfigFileUtils {

	private static final Map<String, String> SETTINGS = new HashMap<>();

	static {
		loadProperties();
	}

	public static String get(String key) {
		return SETTINGS.get(key);
	}

	private static void loadProperties() {
		InputStream resource = ConfigFileUtils.class.getClassLoader().getResourceAsStream("application.yml");
		Map<String, Object> loaded = new Yaml().load(resource);
		SETTINGS.putAll(getFlattenedMap(loaded));
	}

	private static Map<String, String> getFlattenedMap(Map<String, Object> source) {
		Map<String, String> result = new LinkedHashMap<>();
		buildFlattenedMap(result, source, null);
		return result;
	}

	@SuppressWarnings("unchecked")
	private static void buildFlattenedMap(Map<String, String> result, Map<String, Object> source, String path) {
		source.forEach((key, value) -> {
			if (!isBlank(path))
				key = path + (key.startsWith("[") ? key : '.' + key);
			if (value instanceof String) {
				result.put(key, value.toString());
			} else if (value instanceof Map) {
				buildFlattenedMap(result, (Map<String, Object>) value, key);
			} else if (value instanceof Collection) {
				int count = 0;
				for (Object object : (Collection<?>) value)
					buildFlattenedMap(result, singletonMap("[" + (count++) + "]", object), key);
			} else {
				result.put(key, value != null ? "" + value : "");
			}
		});
	}
}
