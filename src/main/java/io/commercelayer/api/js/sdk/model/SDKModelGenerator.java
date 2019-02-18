package io.commercelayer.api.js.sdk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.model.generator.ModelGeneratorUtils;
import io.commercelayer.api.codegen.schema.ApiAttribute;
import io.commercelayer.api.codegen.schema.ApiOperation;
import io.commercelayer.api.codegen.schema.ApiPath;
import io.commercelayer.api.codegen.schema.ApiRelationship;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.domain.OperationType;
import io.commercelayer.api.js.sdk.common.SDKFileGenerator;
import io.commercelayer.api.js.sdk.loader.ConfigLoader;
import io.commercelayer.api.js.sdk.loader.TemplateLoader;
import io.commercelayer.api.js.sdk.loader.TemplateLoader.Type;
import io.commercelayer.api.js.sdk.src.JSCodeBlock;
import io.commercelayer.api.js.sdk.src.JSCodeFile;

public class SDKModelGenerator extends SDKFileGenerator {

	public SDKModelGenerator() {
		this(null);
	}
	
	public SDKModelGenerator(Params params) {
		if (params == null) {
			this.params = new Params()
				.setJsSourceFile(ConfigLoader.getProperty("model.input.file.js"))
				.setOverwiteOutput(Boolean.valueOf(ConfigLoader.getProperty("model.output.file.overwrite")));
		}
		else this.params = params;
	}
	
	@Override
	public JSCodeFile generate(ApiSchema schema) throws CodegenException {
		
		logger.info("Javascript API models generation ...");
		
		List<ResourceModel> resources = defineClassModels(schema);
		
		List<String> exportLines = new LinkedList<>();
		
		exportLines.add(StringUtils.EMPTY);
		exportLines.add("module.exports = {");
		
		List<String> newApiLines = new LinkedList<>();
		for (ResourceModel res : resources) {
			newApiLines.add(StringUtils.EMPTY);
			newApiLines.addAll(buildResourceModel(res));
			newApiLines.add(StringUtils.EMPTY);
			newApiLines.add(StringUtils.EMPTY);
			exportLines.add("\t" + res.getResourceCamelSingular(true) + ",");
		}
		
		exportLines.add("}");
		
		newApiLines.addAll(exportLines);

		final String apiInputFile = this.params.getJsSourceFile();
		final String apiOutputFile = this.params.isOverwiteOutput()? apiInputFile : apiInputFile.replace(".js", ".gen.js");

		List<String> newApiFileLines = replaceApiModels(apiInputFile, newApiLines);

		logger.info("Javascript API models generated.");
		
		
		return new JSCodeFile(apiOutputFile, newApiFileLines);

	}
	
	
	private List<ResourceModel> defineClassModels(ApiSchema schema) {
		
		List<ResourceModel> resources = new LinkedList<>();

		List<String> mainPaths = ModelGeneratorUtils.getMainResourcePaths(schema);
		Collections.sort(mainPaths);

		logger.info("Analizing main paths ...");
		for (String mainRes : mainPaths) {
			
			ResourceModel resource = new ResourceModel(mainRes.substring(1));

			for (ApiPath path : schema.getPaths()) {

				final String res = path.getResource();

				if (res.startsWith(mainRes)) {

					List<ApiOperation> ops = new ArrayList<>();

					if (res.equals(mainRes)) {
						ops.add(path.getOperations().get(OperationType.POST));
					} else if (res.endsWith("Id}")) {
						ops.add(path.getOperations().get(OperationType.PATCH));
//						ops.add(path.getOperations().get(OperationType.GET));
					}

					for (ApiOperation op : ops) {

						if (op.getRequestBody() != null) {
							// Request Fields
							for (ApiAttribute attr : op.getRequestBody().getAttributes()) {
								if (ArrayUtils.contains(ModelGeneratorUtils.MODEL_IGNORED_FIELDS, attr.getName()))
									continue;
								if (!resource.hasAttribute(attr.getName()))
									resource.addAttribute(attr.getName());
							}
							// Request Relationships
							for (ApiRelationship rel : op.getRequestBody().getRelationships()) {
								// Exclude 1:N relationships
//								if (rel.getResourceName().endsWith("s") && !rel.getResourceName().endsWith("ss")) continue;
								if (!resource.hasRelationship(rel.getResourceName()))
									resource.addRelationship(rel.getResourceName());
							}
						}

						// Response Fields
//						for (Map.Entry<String, ApiResponse> r : op.getResponses().entrySet()) {
//							for (ApiAttribute attr : r.getValue().getAttributes()) {
//								if (ArrayUtils.contains(ModelGeneratorUtils.MODEL_IGNORED_FIELDS, attr.getName()))
//									continue;
//								if (!resource.hasAttribute(attr.getName()))
//								resource.addAttribute(attr.getName());
//							}
//						}
						// Response Relationships
//						for (Map.Entry<String, ApiResponse> r : op.getResponses().entrySet()) {
//							for (ApiRelationship rel : r.getValue().getRelationships()) {
//								// Exclude 1:N relationships
//								if (rel.getResourceName().endsWith("s") && !rel.getResourceName().endsWith("ss")) continue;
//								if (!resource.hasRelationship(rel.getResourceName()))
//									resource.addRelationship(rel.getResourceName());
//							}
//						}

					}

				}

			}
			
			resources.add(resource);

		}
		
		return resources;
		
	}
	
	
	private List<String> buildResourceModel(ResourceModel resModel) {

		List<String> lines = new ArrayList<>();
				
		List<String> tplLines = TemplateLoader.getTemplate(Type.model, "resource");
		
		
		for (String line : tplLines) {
			
			if (TemplateLoader.isRepeatableBlock(line)) {
				String repId = TemplateLoader.getRepeatableBlockId(line);
				String repTpl = TemplateLoader.getRepeatableBlockTemplate(line);
				if ("ATTRIBUTES".equals(repId)) {
					for (String a : resModel.getAttributes())
						for (String l : TemplateLoader.getTemplate(Type.model, repTpl))
							lines.add("\t\t" + TemplateLoader.replacePlaceholder(l, "ATTRIBUTE_SNAKE", a));
				}
				else
				if ("RELATIONSHIPS".equals(repId)) {
					lines.add("\t// Relationships");
					for (String r : resModel.getRelationships())
						for (String l : TemplateLoader.getTemplate(Type.model, repTpl))
							lines.add("\t" + TemplateLoader.replacePlaceholder(l, "RELATIONSHIP_SNAKE", r));
				}
			}
			else {
				String filled = line;
				if (!line.isEmpty()) {
					filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_TITLE_SINGULAR", resModel.getResourceTitle());
					filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_TYPE", resModel.getType());
					filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_CAMEL_CAP_SINGULAR", resModel.getResourceCamelSingular(true));
					filled = TemplateLoader.replacePlaceholder(filled, "RESOURCE_SNAKE_SINGULAR", resModel.getResourceSnakeSingular(false));
				}
				lines.add(filled);
			}
				
		}
		
		
		return lines;

	}
	
	
	private List<String> replaceApiModels(String modelFilePath, List<String> newLines) throws CodegenException {

		List<String> jsModel = readLines(modelFilePath);

		List<String> resLines = findBlock("class Resource", jsModel).getLines();

		List<String> newApiLines = new LinkedList<>();
		
		JSCodeBlock expBlock = findBlock("module.exports", jsModel);

		newApiLines.add("// Basic Abstract Resource");
		newApiLines.addAll(resLines);
		newApiLines.add(StringUtils.EMPTY);
		newApiLines.add(StringUtils.EMPTY);
		newApiLines.addAll(newLines);
		newApiLines.addAll(jsModel.subList(expBlock.getLineEnd(), jsModel.size()));
		

		return newApiLines;

	}

}
