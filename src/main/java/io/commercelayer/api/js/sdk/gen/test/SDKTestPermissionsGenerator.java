package io.commercelayer.api.js.sdk.gen.test;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.model.generator.ModelGeneratorUtils;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.codegen.service.generator.ServiceOperation;
import io.commercelayer.api.js.sdk.gen.common.SDKFileGenerator;
import io.commercelayer.api.js.sdk.loader.ConfigLoader;
import io.commercelayer.api.js.sdk.src.JSCodeFile;

public class SDKTestPermissionsGenerator extends SDKFileGenerator {

	public SDKTestPermissionsGenerator() {
		this(null);
	}
	
	public SDKTestPermissionsGenerator(Params params) {
		super((params != null)? params : new Params()
			.setJsSourceDir(ConfigLoader.getProperty("test.input.dir") + "/support")
			.setOverwiteOutput(Boolean.valueOf(ConfigLoader.getProperty("test.output.file.permissions.overwrite")))
		);
	}
	
	@Override
	public JSCodeFile generate(ApiSchema schema) throws CodegenException {
		
		logger.info("Javascript API test permissions file generation ...");

		List<ResourceTestPermissions> permissions = defineTestPermissions(schema);
		
		List<String> lines = new LinkedList<>();
		
		lines.add("module.exports = {");
		
		logger.info("Creating resource permissions ...");
		for (ResourceTestPermissions rtp : permissions) {
			lines.add(StringUtils.EMPTY);
			lines.add(buildTestPermissions(rtp));
		}
		
		lines.add(StringUtils.EMPTY);
		lines.add("}");
		lines.add(StringUtils.EMPTY);
		
		
		String permissionsFilePath = this.params.getJsSourceDir();
		if (!permissionsFilePath.endsWith("/")) permissionsFilePath = permissionsFilePath.concat("/");
		
		final String permissionsFileName = "permissions" + (this.params.isOverwiteOutput()? "" : ".gen") + ".js";
		
		return new JSCodeFile(permissionsFilePath + permissionsFileName, lines);

	}
	
	
	private String buildTestPermissions(ResourceTestPermissions rtp) {
		
		StringBuilder lineSb = new StringBuilder();
		
		lineSb.append('\t');
		lineSb.append(rtp.getResourceCamelPlural(true));
		lineSb.append(" = [");
		
		Iterator<ServiceOperation> soIter = rtp.getPermissions().iterator();
		do {
			ServiceOperation so = soIter.next();
			lineSb.append(String.format("'%s'", so.name()));
			if (soIter.hasNext()) lineSb.append(", ");
		}
		while (soIter.hasNext());
		
		lineSb.append("],");
		
		String line = lineSb.toString();
		logger.info(line);
		
		return line;
				
	}
	
	
	private List<ResourceTestPermissions> defineTestPermissions(ApiSchema schema) {
		
		List<String> mainPaths = ModelGeneratorUtils.getMainResourcePaths(schema);
		Collections.sort(mainPaths);
		
		List<ResourceTestPermissions> permissions = new LinkedList<>();

		logger.info("Analizing main paths ...");
		for (String mainRes : mainPaths) {
			
			ResourceTestPermissions rtp = new ResourceTestPermissions(mainRes.substring(1));
			
			rtp.addPermission(ServiceOperation.create);
			rtp.addPermission(ServiceOperation.retrieve);
			rtp.addPermission(ServiceOperation.update);
//			rtp.addPermission(ServiceOperation.delete);
			rtp.addPermission(ServiceOperation.list);
			
			permissions.add(rtp);

		}
		
		return permissions;
		
	}

}
