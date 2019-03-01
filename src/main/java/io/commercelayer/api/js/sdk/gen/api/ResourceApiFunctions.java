package io.commercelayer.api.js.sdk.gen.api;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.commercelayer.api.codegen.service.generator.ServiceOperation;
import io.commercelayer.api.js.sdk.gen.common.ResourceAware;

public class ResourceApiFunctions extends ResourceAware {

	private static final String[] ALL_FUNCTIONS = {
		ServiceOperation.list.name(),
		ServiceOperation.retrieve.name(),
		ServiceOperation.create.name(),
		ServiceOperation.update.name(),
		ServiceOperation.delete.name(),
		"all"
	};

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