package io.commercelayer.api.js.sdk.loader;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.commercelayer.api.util.LogUtils;

public class ConfigLoader {
	
	public static final String ROOT_PACKAGE = "io/commercelayer/api/js/sdk";
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
	
	private static final Properties SETTINGS;
	private static final String[] RESERVED_WORDS;
	
	static {
		SETTINGS = loadSettings();
		String reservedWords = SETTINGS.getProperty("reserved.words");
		RESERVED_WORDS = StringUtils.isNotBlank(reservedWords)? reservedWords.split("[,;|]") : new String[0];
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
	
	public static String[] getReservedWords() {
		return RESERVED_WORDS;
	}

}
