package io.commercelayer.api.js.sdk;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.schema.ApiSchema;

public interface CLJSFileGenerator {

	public CLJSFile generate(ApiSchema schema) throws CodegenException;

}
