package io.commercelayer.api.js.sdk.gen.test;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.js.sdk.gen.common.SDKFileGenerator;
import io.commercelayer.api.js.sdk.loader.ConfigLoader;
import io.commercelayer.api.js.sdk.src.JSCodeFile;

public class SDKSpecGenerator extends SDKFileGenerator {

	public SDKSpecGenerator() {
		this(null);
	}

	public SDKSpecGenerator(Params params) {
		if (params == null) {
			this.params = new Params().setJsSourceFile(ConfigLoader.getProperty("test.input.file.js"))
					.setOverwiteOutput(Boolean.valueOf(ConfigLoader.getProperty("test.output.file.overwrite")));
		} else
			this.params = params;
	}

	@Override
	public JSCodeFile generate(ApiSchema schema) throws CodegenException {

		logger.info("Javascript API test spec generation ...");

		logger.info("Javascript API test spec generated.");

		return null;

	}

}
