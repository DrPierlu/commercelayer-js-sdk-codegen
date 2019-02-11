package io.commercelayer.api.js.sdk;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.codegen.schema.parser.SchemaParserExecutor;
import io.commercelayer.api.js.sdk.api.CLJSApiGenerator;
import io.commercelayer.api.js.sdk.model.CLJSModelGenerator;
import io.commercelayer.api.util.LogUtils;

public class CLJSGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(CLJSGenerator.class);
	
	
	public void generate() throws CodegenException {
		
		ApiSchema schema = parseSchema();
		
		logger.info("Generating Commerce Layer Javascript SDK files ...");
		
		CLJSFileWriter jsFileWriter = new CLJSFileWriter();
		
		
		boolean error = true;
		
		try {
		
			// API functions
			CLJSFile apiFile = new CLJSApiGenerator().generate(schema);
			jsFileWriter.write(apiFile);
						
			
			// API Model
			CLJSFile modelFile = new CLJSModelGenerator().generate(schema);
			jsFileWriter.write(modelFile);
			
			
			error = false;
			
		}
		catch (CodegenException ce) {
			logger.error(ce.getMessage());
		}
		catch (Exception e) {
			logger.error(LogUtils.printStackTrace(e));
		}
		
		
		if (error) logger.info("Commerce Layer Javascript SDK files generation terminated with errors.");
		else logger.info("Commerce Layer Javascript SDK files successfully generated.");
		
	}
	
	
	private ApiSchema parseSchema() throws CodegenException {
		
		try {
			logger.info("Parsing API schema ...");
			ApiSchema schema = new SchemaParserExecutor().execute();
			logger.info("API schema successfully parsed.");
			return schema;
		} catch (CodegenException ce) {
			logger.error("Error parsing API Schema");
			throw ce;
		}
		
	}
	
	
	@SuppressWarnings("unused")
	private void printLines(List<String> lines) {
		lines.forEach((line) -> logger.debug(line));
	}
	
	
	public static void main(String[] args) throws Exception {
		new CLJSGenerator().generate();
	}

}
