// @RESOURCE_CAMEL_CAP_PLURAL@.create
if (permissions.@RESOURCE_CAMEL_CAP_PLURAL@ && permissions.@RESOURCE_CAMEL_CAP_PLURAL@.includes('create'))
if (data.@RESOURCE_CAMEL_CAP_PLURAL@ && data.@RESOURCE_CAMEL_CAP_PLURAL@.create)
    it("create", function() {
        return commercelayer.create@RESOURCE_CAMEL_CAP_SINGULAR@(new commercelayer.model.@RESOURCE_CAMEL_CAP_SINGULAR@().setFields(data.@RESOURCE_CAMEL_CAP_PLURAL@.create))
            .then(response => {
                const id = response.get('id');
				console.log('Created @RESOURCE_CAMEL_CAP_SINGULAR@ with id ' + id)
				expect(id).not.toBeNull();
            })
    });
else utils.missingRequiredData(SPEC_NAME, 'create')
else utils.missingRequiredPermission(SPEC_NAME, 'create')