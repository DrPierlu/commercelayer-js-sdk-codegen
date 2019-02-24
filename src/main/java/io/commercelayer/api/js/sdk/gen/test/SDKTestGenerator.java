package io.commercelayer.api.js.sdk.gen.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.js.sdk.src.JSCodeFile;

public class SDKTestGenerator {

	private static final Logger logger = LoggerFactory.getLogger(SDKTestGenerator.class);

	public JSCodeFile generate(ApiSchema schema) throws CodegenException {

		logger.info("Javascript API tests generation ...");

		logger.info("Javascript API tests generated.");

		return null;

	}

}
