package io.commercelayer.api.js.sdk.api;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.commercelayer.api.codegen.model.generator.ModelGeneratorUtils;
import io.commercelayer.api.util.CLInflector;

public class ApiResourceFunctions {

	private static final String[] ALL_FUNCTIONS = { "list", "all", "retrieve", "create", "update", "delete" };

	private String path;
	private List<String> functions = new LinkedList<>();
	
	private String resourceCamelSingular;
	private String resourceCamelPlural;
	private String resourceSnakeSingular;

	public ApiResourceFunctions(String path) {
		this(path, ALL_FUNCTIONS);
	}

	public ApiResourceFunctions(String path, String... functions) {
		this.path = path;
		this.functions.addAll(Arrays.asList(functions));
		this.builtinNames(path);
	}

	public String getPath() {
		return path;
	}

	public List<String> getFunctions() {
		return functions;
	}
	
	private void builtinNames(String path) {
		String res = path.substring(1);
		this.resourceCamelPlural = ModelGeneratorUtils.toCamelCase(res);
		this.resourceCamelSingular = CLInflector.getInstance().singularize(this.resourceCamelPlural);
		this.resourceSnakeSingular = CLInflector.getInstance().singularize(res);
	}

	public String getResourceCamelSingular(boolean cap) {
		return cap ? StringUtils.capitalize(resourceCamelSingular) : resourceCamelSingular;
	}

	public String getResourceCamelPlural(boolean cap) {
		return cap? StringUtils.capitalize(resourceCamelPlural) : resourceCamelPlural;
	}

	public String getResourceSnakeSingular(boolean cap) {
		return cap ? StringUtils.capitalize(resourceSnakeSingular) : resourceSnakeSingular;
	}
	
	

}