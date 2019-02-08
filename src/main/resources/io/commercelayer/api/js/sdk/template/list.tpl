list@RESOURCE_CAMEL_CAP_PLURAL@(filter, options) {
	return request(client.newRequest('/api@RESOURCE_PATH@').setParams(filter).setOptions(options));
}