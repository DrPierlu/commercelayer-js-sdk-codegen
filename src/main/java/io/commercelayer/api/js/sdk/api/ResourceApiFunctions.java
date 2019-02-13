package io.commercelayer.api.js.sdk.api;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.commercelayer.api.js.sdk.ResourceAware;

public class ResourceApiFunctions extends ResourceAware {

	private static final String[] ALL_FUNCTIONS = { "list", "all", "retrieve", "create", "update", "delete" };

	private String path;
	private List<String> functions = new LinkedList<>();
	
	public ResourceApiFunctions(String path) {
		this(path, ALL_FUNCTIONS);
	}

	public ResourceApiFunctions(String path, String... functions) {
		super(path.substring(1));
		this.path = path;
		this.functions.addAll(Arrays.asList(functions));
	}

	public String getPath() {
		return path;
	}

	public List<String> getFunctions() {
		return functions;
	}

}