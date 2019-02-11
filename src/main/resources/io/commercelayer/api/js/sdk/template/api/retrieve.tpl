retrieve@RESOURCE_CAMEL_CAP_SINGULAR@(id, filter, options) {
	return request(client.newRequest(`/api@RESOURCE_PATH@/${id}`).setParams(filter).setOptions(options));
}