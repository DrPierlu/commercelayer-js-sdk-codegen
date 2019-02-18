package io.commercelayer.api.js.sdk.model;

import java.util.LinkedList;
import java.util.List;

import io.commercelayer.api.js.sdk.common.ResourceAware;
import io.commercelayer.api.util.CLInflector;

public class ResourceModel extends ResourceAware {
	
	private String type;
	private List<String> attributes = new LinkedList<>();
	private List<String> relationships = new LinkedList<>();

	
	public ResourceModel(String type) {
		super(CLInflector.getInstance().singularize(type));
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public List<String> getAttributes() {
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
	
}
