package io.commercelayer.api.js.sdk.gen.test;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import io.commercelayer.api.codegen.service.generator.ServiceOperation;
import io.commercelayer.api.js.sdk.gen.common.ResourceAware;

public class ResourceTestPermissions extends ResourceAware {
	
	public ResourceTestPermissions(String name) {
		super(name);
	}

	private SortedSet<ServiceOperation> permissions = new TreeSet<>();

	
	public ResourceTestPermissions addPermission(ServiceOperation so) {
		this.permissions.add(so);
		return this;
	}
	
	public Set<ServiceOperation> getPermissions() {
		return this.permissions;
	}

}
