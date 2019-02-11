package io.commercelayer.api.js.sdk.api;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
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
import io.commercelayer.api.js.sdk.CLJSFile;
import io.commercelayer.api.js.sdk.CLJSFileGenerator;
import io.commercelayer.api.js.sdk.Config;
import io.commercelayer.api.js.sdk.TemplateLoader;
import io.commercelayer.api.js.sdk.TemplateLoader.Type;

public class CLJSApiGenerator implements CLJSFileGenerator {

	private static final Logger logger = LoggerFactory.getLogger(CLJSApiGenerator.class);

	@Override
	public CLJSFile generate(ApiSchema schema) throws CodegenException {

		logger.info("Javascript API functions generation ...");

		List<ApiResourceFunctions> apiFunctions = defineApiFunctions(schema);

		List<String> newApiLines = new LinkedList<>();
		for (ApiResourceFunctions apiFun : apiFunctions) {
			newApiLines.add(StringUtils.EMPTY);
			newApiLines.addAll(buildResourceFunctions(apiFun));
			newApiLines.add(StringUtils.EMPTY);
		}

		String apiInputFile = Config.getProperty("api.input.file.js");
		String apiOutputFile = Boolean.valueOf(Config.getProperty("api.output.file.overwrite"))?
				apiInputFile : apiInputFile.replace(".js", ".gen.js");

		List<String> newApiFileLines = replaceApiFunctions(apiInputFile, newApiLines);

		logger.info("Javascript API functions generated.");
		
		return new CLJSFile(apiOutputFile, newApiFileLines);

	}

	
	private List<String> buildResourceFunctions(ApiResourceFunctions resFunctions) {

		List<String> lines = new ArrayList<>();

		lines.add(String.format("\t// %s",
				WordUtils.capitalize(resFunctions.getResourceSnakeSingular(true).replaceAll("_", " "))));

		for (String fun : resFunctions.getFunctions()) {

			List<String> tplLines = TemplateLoader.getTemplate(Type.api, fun);

			for (String line : tplLines) {
				String filled = line;
				if (!line.isEmpty()) {
					filled = TemplateLoader.replacePlaceholder(filled, "@RESOURCE_CAMEL_CAP_PLURAL@",
							resFunctions.getResourceCamelPlural(true));
					filled = TemplateLoader.replacePlaceholder(filled, "@RESOURCE_CAMEL_CAP_SINGULAR@",
							resFunctions.getResourceCamelSingular(true));
					filled = TemplateLoader.replacePlaceholder(filled, "@RESOURCE_SNAKE_SINGULAR@",
							resFunctions.getResourceSnakeSingular(false));
					filled = TemplateLoader.replacePlaceholder(filled, "@RESOURCE_PATH@", resFunctions.getPath());
				}
				lines.add("\t" + filled);
			}

			lines.add(StringUtils.EMPTY);

		}

		return lines;

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

			if (line.contains("class CLApi"))
				apiIni = index;

			for (char c : line.toCharArray()) {
				if (c == '{')
					brackets++;
				else if (c == '}')
					brackets--;
			}

			if ((apiIni > 0) && (index != apiIni) && (brackets == 0)) {
				apiEnd = index;
				break;
			}

		}

		List<String> preLines = jsApi.subList(0, apiIni - 1);
		List<String> apiLines = jsApi.subList(apiIni - 1, apiEnd);
		List<String> postLines = jsApi.subList(apiEnd, jsApi.size());

		List<String> newApiLines = new LinkedList<>();

		newApiLines.addAll(preLines);
		newApiLines.add(apiLines.get(0));
		newApiLines.addAll(newLines);
		newApiLines.add(apiLines.get(apiLines.size() - 1));
		newApiLines.addAll(postLines);

		return newApiLines;

	}

	
	private List<ApiResourceFunctions> defineApiFunctions(ApiSchema schema) {

		List<String> paths = ModelGeneratorUtils.getMainResourcePaths(schema);
		Collections.sort(paths);

		List<ApiResourceFunctions> functions = new ArrayList<>();

		for (String path : paths)
			functions.add(new ApiResourceFunctions(path));

		return functions;

	}

}
