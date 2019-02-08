delete@RESOURCE_CAMEL_CAP_SINGULAR@(id) {
	return request(client.newRequest(`/api@RESOURCE_PATH@/${id}`, 'delete'));
}