package io.commercelayer.api.js.sdk.gen.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.model.generator.ModelGeneratorUtils;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.js.sdk.src.JSCodeFile;

public class SDKTestGenerator {

	private static final Logger logger = LoggerFactory.getLogger(SDKTestGenerator.class);

	public List<JSCodeFile> generate(ApiSchema schema) throws CodegenException {

		logger.info("Javascript API tests generation ...");
		
		List<ResourceTestSpec> specs = defineTestSpecs(schema);
		
		List<JSCodeFile> jsFiles = new ArrayList<>();
		
		for (ResourceTestSpec spec : specs)
			jsFiles.add(new SDKSpecGenerator(spec).generate());
		
		jsFiles.add(new SDKTestPermissionsGenerator().generate(schema));

		logger.info("Javascript API tests generated.");

		return jsFiles;

	}
	
	
	private List<ResourceTestSpec> defineTestSpecs(ApiSchema schema) {

		List<String> paths = ModelGeneratorUtils.getMainResourcePaths(schema);
		Collections.sort(paths);

		List<ResourceTestSpec> specs = new ArrayList<>();
		for (String path : paths)
			specs.add(new ResourceTestSpec(path.substring(1)));
		
		return specs;

	}
	
}
