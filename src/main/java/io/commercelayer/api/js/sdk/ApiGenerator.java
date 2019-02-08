package io.commercelayer.api.js.sdk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.model.generator.ModelGeneratorUtils;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.codegen.schema.parser.SchemaParserExecutor;
import io.commercelayer.api.util.LogUtils;

public class ApiGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiGenerator.class);
	
	
	public void generate() throws CodegenException {
		
		ApiSchema schema;
		try {
			logger.info("Parsing API schema ...");
			schema = new SchemaParserExecutor().execute();
			logger.info("API schema successfully parsed.");
		} catch (CodegenException ce) {
			logger.error("Error parsing API Schema");
			throw ce;
		}
		
		// API functions
		generateApiFunctions(schema);
		
		// API Model
		generateApiModel(schema);
		
	}
	
	
	private void generateApiFunctions(ApiSchema schema) throws CodegenException {
		
		logger.info("Javascript API functions file generation ...");
		
		List<ApiResourceFunctions> apiFunctions = getApiFunctions(schema);
		
		List<String> newApiLines = new LinkedList<>();
		for (ApiResourceFunctions apiFun : apiFunctions) {
			newApiLines.add(StringUtils.EMPTY);
			newApiLines.addAll(getResourceFunctions(apiFun));
			newApiLines.add(StringUtils.EMPTY);
		}
		
		String apiInputFile = Config.getProperty("api.input.file.js");
		String apiOutputFile = Boolean.valueOf(Config.getProperty("api.output.file.overwrite"))? apiInputFile : apiInputFile.replace(".js", ".gen.js");
		
		List<String> newApiFileLines = replaceApiFunctions(apiInputFile, newApiLines);
		
		writeJsFile(apiOutputFile, newApiFileLines);
		
		logger.info("Javascript API functions file generated.");
		
	}
	
	
	private void generateApiModel(ApiSchema schema) {
		
		// logger.info("Javascript API model file generation ...");
		
		// logger.info("Javascript API model file generated.");
		
	}
	
	
	private void writeJsFile(String apiFilePath, List<String> apiLines) throws CodegenException {
		
		Writer wr = null;
		
		try {
			wr = Files.newWriter(new File(apiFilePath), Charset.forName("UTF-8"));
		} catch (FileNotFoundException e) {
			throw new CodegenException("Unable to write file " + apiFilePath);
		}
		
		if (wr == null) return;
		
		apiLines.add(0, "// API functions automatically generated at " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		apiLines.add(1, StringUtils.EMPTY);
		
		try (BufferedWriter br = new BufferedWriter(wr)) {
			for (String line : apiLines) {
				br.write(line);
				br.newLine();
			}
		}
		catch (Exception e) {
			logger.error(LogUtils.printStackTrace(e));
			throw new CodegenException("Error writing output file");
		}
		
		logger.info("Successfully written file {}", apiFilePath);
		
	}
	
	
	private List<String> replaceApiFunctions(String apiFilePath, List<String> newLines) throws CodegenException {
		
		List<String> jsApi;
		try {
			jsApi = Files.readLines(new File(apiFilePath), Charset.forName("UTF-8"));
		} catch (IOException ioe) {
			throw new CodegenException("Error reading file " + apiFilePath); 
		}
		
		int apiIni = 0;
		int apiEnd = 0;
		int brackets = 0;
		int index = 0;
		
		for (String line : jsApi) {
			index++;
			if (line.contains("class CLApi")) apiIni = index;
			for (char c : line.toCharArray()) {
				if (c == '{') brackets++;
				else
				if (c == '}') brackets--;
			}
			if ((apiIni > 0) && (index != apiIni) && (brackets == 0)) {
				apiEnd = index;
				break;
			}
		}
		
		
		List<String> preLines = jsApi.subList(0, apiIni-1);
		List<String> apiLines = jsApi.subList(apiIni-1, apiEnd);
		List<String> postLines = jsApi.subList(apiEnd, jsApi.size());
		
		List<String> newApiLines = new LinkedList<>();
		
		newApiLines.addAll(preLines);
		newApiLines.add(apiLines.get(0));
		newApiLines.addAll(newLines);
		newApiLines.add(apiLines.get(apiLines.size()-1));
		newApiLines.addAll(postLines);
		
		return newApiLines;
		
	}
	
	
	private List<ApiResourceFunctions> getApiFunctions(ApiSchema schema) {
		
		List<String> paths = ModelGeneratorUtils.getMainResourcePaths(schema);
		Collections.sort(paths);
		
		List<ApiResourceFunctions> functions = new ArrayList<>();
		
		for (String path : paths) functions.add(new ApiResourceFunctions(path));
		
		return functions;
		
	}
	
	
	private List<String> getResourceFunctions(ApiResourceFunctions resFunctions) {
		
		List<String> lines = new ArrayList<>();
		
		lines.add(String.format("\t// %s", WordUtils.capitalize(resFunctions.getResourceSnakeSingular(true).replaceAll("_", " "))));
		
		for (String fun : resFunctions.getFunctions()) {
			
			List<String> tplLines = TemplateLoader.getTemplate(fun);
			
			for (String line : tplLines) {
				String filled = line;
				if (!line.isEmpty()) {
					filled = TemplateLoader.replacePlaceholder(filled, "@RESOURCE_CAMEL_CAP_PLURAL@", resFunctions.getResourceCamelPlural(true));
					filled = TemplateLoader.replacePlaceholder(filled, "@RESOURCE_CAMEL_CAP_SINGULAR@", resFunctions.getResourceCamelSingular(true));
					filled = TemplateLoader.replacePlaceholder(filled, "@RESOURCE_SNAKE_SINGULAR@", resFunctions.getResourceSnakeSingular(false));
					filled = TemplateLoader.replacePlaceholder(filled, "@RESOURCE_PATH@", resFunctions.getPath());
				}
				lines.add("\t" + filled);
			}
			
			lines.add(StringUtils.EMPTY);
			
		}
		
		return lines;
		
	}
	
	
	@SuppressWarnings("unused")
	private void printLines(List<String> lines) {
		lines.forEach((line) -> logger.debug(line));
	}
	
	
	public static void main(String[] args) throws Exception {
		new ApiGenerator().generate();
	}

}
