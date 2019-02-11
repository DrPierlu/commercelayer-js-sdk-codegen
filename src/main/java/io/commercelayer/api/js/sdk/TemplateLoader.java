package io.commercelayer.api.js.sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateLoader {
	
	private static final String REPEATABLE_BLOCK_INI = "@-->";
	private static final String PLACEHOLDER_DELIMITER = "@";
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateLoader.class);
	
	private static final Map<String, List<String>> TEMPLATE_MAP = new HashMap<>();
	
	
	public static enum Type { api, model }
	
	
	private static String templateKey(Type type, String name) {
		return String.format("%s/%s", type.name(), name);
	}
	
	
	public static List<String> getTemplate(Type type, String name) {
		
		final String key = templateKey(type, name);
		
		List<String> tpl = TEMPLATE_MAP.get(key);
		if (tpl == null) {
			tpl = loadTemplate(type, name);
			TEMPLATE_MAP.put(key, tpl);
		}
		
		return tpl;
		
	}
	
	
	private static List<String> loadTemplate(Type type, String name) {
		
		String template = String.format("template/%s/%s", type.name(), name);
		if (!template.endsWith(".tpl")) template = template.concat(".tpl");
		
		logger.info("Loading template {} ...", template);
		
		InputStream is = TemplateLoader.class.getClassLoader().getResourceAsStream(Config.ROOT_PACKAGE + "/" + template);
		
		List<String> tpl = new LinkedList<>();
		
		try (BufferedReader bt = new BufferedReader(new InputStreamReader(is))) {
			String line = null;
			while ((line = bt.readLine()) != null) tpl.add(line);
			logger.info("Template {} loaded.", template);
		}
		catch (IOException ioe) {
			logger.error("Error reading template {}", template);
		}

		
		return tpl;
		
	}
	
	
	public static String replacePlaceholder(String line, String ph, String value) {
		if (line.contains(ph)) return line.replaceAll(String.format("%1$s%2$s%1$s", PLACEHOLDER_DELIMITER, ph), value);
		else return line;
	}
	
	
	
	public static boolean isRepeatableBlock(String line) {
		return (line != null) && line.trim().startsWith(REPEATABLE_BLOCK_INI);
	}
	
	public static String getRepeatableBlockId(String line) {
		return line.substring(line.indexOf(REPEATABLE_BLOCK_INI) + REPEATABLE_BLOCK_INI.length(), line.indexOf('[')).trim();
	}
	
	public static String getRepeatableBlockTemplate(String line) {
		return line.substring(line.indexOf('[')+1, line.indexOf(']'));
	}

}
