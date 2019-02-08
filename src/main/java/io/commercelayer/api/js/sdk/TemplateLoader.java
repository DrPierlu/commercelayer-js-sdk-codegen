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
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateLoader.class);
	
	private static final Map<String, List<String>> TEMPLATE_MAP = new HashMap<>();
	
	public static List<String> getTemplate(String name) {
		
		List<String> tpl = TEMPLATE_MAP.get(name);
		if (tpl == null) {
			tpl = loadTemplate(name);
			TEMPLATE_MAP.put(name, tpl);
		}
		
		return tpl;
		
	}
	
	
	
	private static List<String> loadTemplate(String name) {
		
		final String template = String.format("template/%s.tpl", name);
		
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
	
	static String replacePlaceholder(String line, String ph, String value) {
		if (line.contains(ph)) return line.replaceAll(ph, value);
		else return line;
	}

}
