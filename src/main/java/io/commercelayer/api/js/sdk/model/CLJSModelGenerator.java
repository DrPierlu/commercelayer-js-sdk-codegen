package io.commercelayer.api.js.sdk.model;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.js.sdk.CLJSFile;
import io.commercelayer.api.js.sdk.CLJSFileGenerator;

public class CLJSModelGenerator implements CLJSFileGenerator {

	@Override
	public CLJSFile generate(ApiSchema schema) throws CodegenException {
		throw new CodegenException("CLJSModelGenerator not implemented");
	}

}
