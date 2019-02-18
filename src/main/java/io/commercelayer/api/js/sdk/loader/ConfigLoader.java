package io.commercelayer.api.js.sdk.loader;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.commercelayer.api.util.LogUtils;

public class ConfigLoader {
	
	public static final String ROOT_PACKAGE = "io/commercelayer/api/js/sdk";
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
	
	private static final Properties SETTINGS;
	
	static {
		SETTINGS = loadSettings();
	}
	
	
	private static Properties loadSettings() {		
		
		try {
			Properties props = new Properties();
			props.load(ConfigLoader.class.getClassLoader().getResourceAsStream(ROOT_PACKAGE + "/settings.properties"));
			return props;
		} catch (IOException ioe) {
			logger.error(LogUtils.printStackTrace(ioe));
			throw new RuntimeException("Error reading configuration file");
		}
		
	}
	
	public static String getProperty(String key) {
		return SETTINGS.getProperty(key);
	}

}
