package io.commercelayer.api.js.sdk.gen.test;

import io.commercelayer.api.js.sdk.gen.common.ResourceAware;

public class ResourceTestSpec extends ResourceAware {
	
	public ResourceTestSpec(String spec) {
		super(spec);
	}
	
	public String getResource() {
		return super.getName();
	}

}
