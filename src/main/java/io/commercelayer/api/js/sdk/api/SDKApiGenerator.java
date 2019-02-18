package io.commercelayer.api.js.sdk.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.model.generator.ModelGeneratorUtils;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.js.sdk.common.SDKFileGenerator;
import io.commercelayer.api.js.sdk.loader.ConfigLoader;
import io.commercelayer.api.js.sdk.loader.TemplateLoader;
import io.commercelayer.api.js.sdk.loader.TemplateLoader.Type;
import io.commercelayer.api.js.sdk.src.JSCodeBlock;
import io.commercelayer.api.js.sdk.src.JSCodeFile;

public class SDKApiGenerator extends SDKFileGenerator {
	
	public SDKApiGenerator() {
		this(null);
	}
	
	public SDKApiGenerator(Params params) {
		if (params == null) {
			this.params = new Params()
				.setJsSourceFile(ConfigLoader.getProperty("api.input.file.js"))
				.setOverwiteOutput(Boolean.valueOf(ConfigLoader.getProperty("api.output.file.overwrite")));
		}
		else this.params = params;
	}

	@Override
	public JSCodeFile generate(ApiSchema schema) throws CodegenException {

		logger.info("Javascript API functions generation ...");

		List<ResourceApiFunctions> apiFunctions = defineApiFunctions(schema);

		List<String> newApiLines = new LinkedList<>();
		for (ResourceApiFunctions apiFun : apiFunctions) {
			newApiLines.add(StringUtils.EMPTY);
			newApiLines.addAll(buildResourceFunctions(apiFun));
			newApiLines.add(StringUtils.EMPTY);
		}

		final String apiInputFile = this.params.getJsSourceFile();
		final String apiOutputFile = this.params.isOverwiteOutput()? apiInputFile : apiInputFile.replace(".js", ".gen.js");

		List<String> newApiFileLines = replaceApiFunctions(apiInputFile, newApiLines);

		logger.info("Javascript API functions generated.");
		
		return new JSCodeFile(apiOutputFile, newApiFileLines);

	}

	
	private List<String> buildResourceFunctions(ResourceApiFunctions resFunctions) {

		List<String> lines = new ArrayList<>();

		lines.add(String.format("\t// %s", resFunctions.getResourceTitle()));

		for (String fun : resFunctions.getFunctions()) {

			List<String> tplLines = TemplateLoader.getTemplate(Type.api, fun);

			for (String line : tplLines) {
				String filled = line;
				if (!line.isEmpty()) {
					filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_CAMEL_CAP_PLURAL", resFunctions.getResourceCamelPlural(true));
					filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_CAMEL_CAP_SINGULAR", resFunctions.getResourceCamelSingular(true));
					filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_SNAKE_SINGULAR", resFunctions.getResourceSnakeSingular(false));
					filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_PATH", resFunctions.getPath());
				}
				lines.add("\t" + filled);
			}

			lines.add(StringUtils.EMPTY);

		}

		return lines;

	}

	
	private List<String> replaceApiFunctions(String apiFilePath, List<String> newLines) throws CodegenException {

		List<String> jsApi = readLines(apiFilePath);

		JSCodeBlock apiBlock = findBlock("class CLApi", jsApi);
		
		List<String> preLines = jsApi.subList(0, apiBlock.getLineIni() - 1);
		List<String> apiLines = apiBlock.getLines();
		List<String> postLines = jsApi.subList(apiBlock.getLineEnd(), jsApi.size());

		List<String> newApiLines = new LinkedList<>();

		newApiLines.addAll(preLines);
		newApiLines.add(apiLines.get(0));
		newApiLines.addAll(newLines);
		newApiLines.add(apiLines.get(apiLines.size() - 1));
		newApiLines.addAll(postLines);

		return newApiLines;

	}

	
	private List<ResourceApiFunctions> defineApiFunctions(ApiSchema schema) {

		List<String> paths = ModelGeneratorUtils.getMainResourcePaths(schema);
		Collections.sort(paths);

		List<ResourceApiFunctions> functions = new ArrayList<>();

		for (String path : paths)
			functions.add(new ResourceApiFunctions(path));

		return functions;

	}

}
