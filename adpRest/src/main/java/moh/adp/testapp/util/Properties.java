package moh.adp.testapp.util;

import java.io.IOException;

public class Properties {
	private static java.util.Properties properties;
	
	public static void load() {
		if (properties == null) {
			try {
				properties = new java.util.Properties();
				properties.load(ClassLoader.getSystemResourceAsStream("adprest.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}

}
