package io.commercelayer.api.js.sdk.gen.common;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import io.commercelayer.api.codegen.model.generator.ModelGeneratorUtils;
import io.commercelayer.api.js.sdk.loader.ConfigLoader;
import io.commercelayer.api.util.CLInflector;

public abstract class ResourceAware {

	private String name;

	private String resourceCamelSingular;
	private String resourceCamelPlural;
	private String resourceSnakeSingular;
	private String resourceSnakePlural;
	private String resourceTitle;

	public ResourceAware(String name) {
		this.name = name;
		builtinNames(this.name);
	}

	private void builtinNames(String name) {
		
		this.resourceCamelPlural = ModelGeneratorUtils.toCamelCase(name);
		this.resourceCamelSingular = CLInflector.getInstance().singularize(this.resourceCamelPlural);
		this.resourceSnakeSingular = CLInflector.getInstance().singularize(name);
		this.resourceSnakePlural = name;
		this.resourceTitle = WordUtils.capitalize(getResourceSnakeSingular().replaceAll("_", " "));
		
		this.resourceSnakeSingular = checkReservedWords(this.resourceSnakeSingular);
		
	}
	
	private String checkReservedWords(String s) {
		return ArrayUtils.contains(ConfigLoader.getReservedWords(), s)? s.concat("_") : s;
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

	public String getResourceSnakeSingular() {
		return this.resourceSnakeSingular;
	}
	
	public String getResourceSnakePlural() {
		return this.resourceSnakePlural;
	}

	public String getResourceTitle() {
		return resourceTitle;
	}

}
