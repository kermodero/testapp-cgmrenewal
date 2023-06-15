package moh.adp.testapp.util;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Properties {
	private static java.util.Properties properties;
	
	public static void load() {
		if (properties == null) {
			try {
				properties = new java.util.Properties();
				properties.load(Properties.class.getResourceAsStream("/META-INF/adprest.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}

	public static Map<String, String> getAll() {
		return properties.entrySet().stream().collect(Collectors.toMap(Object::toString, Object::toString));
	}
}
