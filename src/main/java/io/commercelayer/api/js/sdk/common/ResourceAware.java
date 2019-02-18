package io.commercelayer.api.js.sdk.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import io.commercelayer.api.codegen.model.generator.ModelGeneratorUtils;
import io.commercelayer.api.util.CLInflector;

public abstract class ResourceAware {

	private String name;

	private String resourceCamelSingular;
	private String resourceCamelPlural;
	private String resourceSnakeSingular;
	private String resourceTitle;

	public ResourceAware(String name) {
		this.name = name;
		builtinNames(this.name);
	}

	private void builtinNames(String name) {
		this.resourceCamelPlural = ModelGeneratorUtils.toCamelCase(name);
		this.resourceCamelSingular = CLInflector.getInstance().singularize(this.resourceCamelPlural);
		this.resourceSnakeSingular = CLInflector.getInstance().singularize(name);
		this.resourceTitle = WordUtils.capitalize(getResourceSnakeSingular(true).replaceAll("_", " "));
	}
	
	public String getName() {
		return name;
	}

	public String getResourceCamelSingular(boolean cap) {
		return cap ? StringUtils.capitalize(resourceCamelSingular) : resourceCamelSingular;
	}

	public String getResourceCamelPlural(boolean cap) {
		return cap ? StringUtils.capitalize(resourceCamelPlural) : resourceCamelPlural;
	}

	public String getResourceSnakeSingular(boolean cap) {
		return cap ? StringUtils.capitalize(resourceSnakeSingular) : resourceSnakeSingular;
	}

	public String getResourceTitle() {
		return resourceTitle;
	}

}
