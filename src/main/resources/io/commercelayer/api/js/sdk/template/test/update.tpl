// @RESOURCE_CAMEL_CAP_PLURAL@.update
if (permissions.@RESOURCE_CAMEL_CAP_PLURAL@ && permissions.@RESOURCE_CAMEL_CAP_PLURAL@.includes('update'))
if (data.@RESOURCE_CAMEL_CAP_PLURAL@ && data.@RESOURCE_CAMEL_CAP_PLURAL@.update)
    it("update", function() {
        return commercelayer.update@RESOURCE_CAMEL_CAP_SINGULAR@(data.@RESOURCE_CAMEL_CAP_PLURAL@.update.id, new commercelayer.model.@RESOURCE_CAMEL_CAP_SINGULAR@().setFields(data.@RESOURCE_CAMEL_CAP_PLURAL@.update))
            .then(response => {
                Object.keys(data.@RESOURCE_CAMEL_CAP_PLURAL@.update).forEach(field => {
                	if (commercelayer.model.helper.isApiResource(data.@RESOURCE_CAMEL_CAP_PLURAL@.update[field])) {
						console.log('Evaluation of resource object not supported ['  + field + ']')
					}
                    else expect(response.get(field)).toBe(data.@RESOURCE_CAMEL_CAP_PLURAL@.update[field])
                })
            })
    });
else console.log('Test @RESOURCE_CAMEL_CAP_PLURAL@.update skipped: missing required test data')
else console.log('Test @RESOURCE_CAMEL_CAP_PLURAL@.update skipped: missing required resource permission')