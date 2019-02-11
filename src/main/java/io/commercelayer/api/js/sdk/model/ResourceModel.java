package io.commercelayer.api.js.sdk.model;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import io.commercelayer.api.codegen.model.generator.ModelGeneratorUtils;
import io.commercelayer.api.util.CLInflector;

public class ResourceModel {
	
	private String name;
	private String type;
	private List<String> attributes = new LinkedList<>();
	private List<String> relationships = new LinkedList<>();
	
	private String resourceCamelSingular;
	private String resourceCamelPlural;
	private String resourceSnakeSingular;
	private String resourceTitle;
	
	public ResourceModel(String name, String type) {
		this.type = type;
		setName(name);
	}
	
	public ResourceModel(String type) {
		this(CLInflector.getInstance().singularize(type), type);
	}
	
	private void builtinNames(String res) {
		this.resourceCamelPlural = ModelGeneratorUtils.toCamelCase(res);
		this.resourceCamelSingular = CLInflector.getInstance().singularize(this.resourceCamelPlural);
		this.resourceSnakeSingular = CLInflector.getInstance().singularize(res);
		this.resourceTitle = WordUtils.capitalize(getResourceSnakeSingular(true).replaceAll("_", " "));
	}

	public String getName() {
		return name;
	}

	public ResourceModel setName(String name) {
		this.name = name;
		builtinNames(name);
		return this;
	}

	public String getType() {
		return type;
	}

	public ResourceModel setType(String type) {
		this.type = type;
		return this;
	}

	public List<String> getattributes() {
		return attributes;
	}

	public ResourceModel addAttributes(List<String> attributes) {
		this.attributes.addAll(attributes);
		return this;
	}
	
	public ResourceModel addAttribute(String attribute) {
		this.attributes.add(attribute);
		return this;
	}
	
	public List<String> getRelationships() {
		return relationships;
	}

	public ResourceModel addRelationships(List<String> relationships) {
		this.relationships.addAll(relationships);
		return this;
	}
	
	public ResourceModel addRelationship(String relationship) {
		this.relationships.add(relationship);
		return this;
	}
	
	public boolean hasAttribute(String attribute) {
		return this.attributes.contains(attribute);
	}
	
	public boolean hasRelationship(String relationship) {
		return this.relationships.contains(relationship);
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
	
	public String getResourceTitle() {
		return resourceTitle;
	}

}
