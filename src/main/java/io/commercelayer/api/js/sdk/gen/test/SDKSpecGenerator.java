package io.commercelayer.api.js.sdk.gen.test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.js.sdk.gen.common.SDKFileGenerator;
import io.commercelayer.api.js.sdk.loader.ConfigLoader;
import io.commercelayer.api.js.sdk.loader.TemplateLoader;
import io.commercelayer.api.js.sdk.loader.TemplateLoader.Type;
import io.commercelayer.api.js.sdk.src.JSCodeFile;

public class SDKSpecGenerator extends SDKFileGenerator {
	
	private ResourceTestSpec resSpec;

	public SDKSpecGenerator(ResourceTestSpec resSpec) {
		this(resSpec, null);
	}

	public SDKSpecGenerator(ResourceTestSpec resSpec, Params params) {
		super((params != null)? params : new Params()
			.setJsSourceDir(ConfigLoader.getProperty("test.input.dir"))
			.setOverwiteOutput(Boolean.valueOf(ConfigLoader.getProperty("test.output.file.overwrite")))
		);
		this.resSpec = resSpec;
	}
	
	public JSCodeFile generate() throws CodegenException {
		return generate(null);
	}

	@Override
	public JSCodeFile generate(ApiSchema schema) throws CodegenException {

		logger.info("Javascript API test spec generation [{}] ...", this.resSpec.getResource());
		
		String jsSpecFilePath = this.params.getJsSourceDir();
		if (!jsSpecFilePath.endsWith("/")) jsSpecFilePath = jsSpecFilePath.concat("/");
		
		if (!this.params.isOverwiteOutput()) jsSpecFilePath = jsSpecFilePath.concat("gen/");
		new File(jsSpecFilePath).mkdirs();
		
		jsSpecFilePath = jsSpecFilePath.concat(this.resSpec.getResource() + ".spec.js");
		
		JSCodeFile jsSpecFile = new JSCodeFile(jsSpecFilePath);
		jsSpecFile.addSourceLines(buildTestSpec(this.resSpec));

		logger.info("Javascript API test spec generated [{}].", this.resSpec.getResource());

		return jsSpecFile;

	}
	
	
	private List<String> buildTestSpec(ResourceTestSpec spec) {
		
		List<String> lines = new LinkedList<>();
		
		List<String> specTpl = TemplateLoader.getTemplate(Type.test, "spec.tpl");
		
		for (String line : specTpl) {
			if (TemplateLoader.isInnerTemplateBlock(line)) {
				
				List<String> subTpl = TemplateLoader.getTemplate(Type.test, TemplateLoader.getSubTemplateName(line));
				
				for (String l : subTpl) {
					String filled = l;
					if (!l.isEmpty()) {
						filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_CAMEL_CAP_PLURAL", spec.getResourceCamelPlural(true));
						filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_CAMEL_CAP_SINGULAR", spec.getResourceCamelSingular(true));
						filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_SNAKE_PLURAL", spec.getResourceSnakeSingular());
					}
					lines.add("\t".concat(filled));
				}
				
			}
			else
			if (TemplateLoader.containsPlaceholder(line, "RESOURCE_CAMEL_CAP_PLURAL"))
				lines.add(TemplateLoader.replacePlaceholder(line, "RESOURCE_CAMEL_CAP_PLURAL", spec.getResourceCamelPlural(true)));
			else lines.add(line);
		}
		
		List<String> newLines = new LinkedList<>();
		
		for (String line : lines) {
			newLines.add(line);
		}
		
		return newLines;
		
	}

}
