update@RESOURCE_CAMEL_CAP_SINGULAR@(id, @RESOURCE_SNAKE_SINGULAR@) {
	return request(client.newRequest(`/api@RESOURCE_PATH@/${id}`, 'patch').setBody(@RESOURCE_SNAKE_SINGULAR@));
}