// @RESOURCE_CAMEL_CAP_PLURAL@.update
if (permissions.@RESOURCE_CAMEL_CAP_PLURAL@ && permissions.@RESOURCE_CAMEL_CAP_PLURAL@.includes('update'))
if (data.@RESOURCE_CAMEL_CAP_PLURAL@ && data.@RESOURCE_CAMEL_CAP_PLURAL@.update)
    it("update", function() {
    	let qf = utils.buildQueryFilter(data.@RESOURCE_CAMEL_CAP_PLURAL@.update);
		let @RESOURCE_SNAKE_PLURAL@ = new commercelayer.model.@RESOURCE_CAMEL_CAP_SINGULAR@().setFields(data.@RESOURCE_CAMEL_CAP_PLURAL@.update);
        return commercelayer.update@RESOURCE_CAMEL_CAP_SINGULAR@(data.@RESOURCE_CAMEL_CAP_PLURAL@.update.id, @RESOURCE_SNAKE_PLURAL@, qf)
            .then(response => {
                Object.keys(data.@RESOURCE_CAMEL_CAP_PLURAL@.update).forEach(field => {
                	if (commercelayer.model.helper.isApiResource(data.@RESOURCE_CAMEL_CAP_PLURAL@.update[field]))
						expect(response.get([field+'.id'])[field].id).toBe(data.@RESOURCE_CAMEL_CAP_PLURAL@.update[field].id.toString())
					else
						expect(utils.toString(response.get(field))).toBe(data.@RESOURCE_CAMEL_CAP_PLURAL@.update[field].toString())
                })
            })
    });
else utils.missingRequiredData(SPEC_NAME, 'update')
else utils.missingRequiredPermission(SPEC_NAME, 'update')