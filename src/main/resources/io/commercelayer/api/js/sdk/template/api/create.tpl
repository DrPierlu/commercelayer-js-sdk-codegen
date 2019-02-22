create@RESOURCE_CAMEL_CAP_SINGULAR@(@RESOURCE_SNAKE_SINGULAR@, filter) {
    return request(client.newRequest('/api@RESOURCE_PATH@', 'post').setBody(@RESOURCE_SNAKE_SINGULAR@).setParams(filter));
}