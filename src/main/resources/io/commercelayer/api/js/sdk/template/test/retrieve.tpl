// @RESOURCE_CAMEL_CAP_PLURAL@.retrieve
if (permissions.@RESOURCE_CAMEL_CAP_PLURAL@ && permissions.@RESOURCE_CAMEL_CAP_PLURAL@.includes('retrieve'))
if (data.@RESOURCE_CAMEL_CAP_PLURAL@ && data.@RESOURCE_CAMEL_CAP_PLURAL@.retrieve)
    it("retrieve", function() {
        return commercelayer.retrieve@RESOURCE_CAMEL_CAP_SINGULAR@(data.@RESOURCE_CAMEL_CAP_PLURAL@.retrieve.id)
            .then(response => {
                expect(response.get('id')).toBe(data.@RESOURCE_CAMEL_CAP_PLURAL@.retrieve.id.toString())
            })
    });
else utils.missingRequiredData(SPEC_NAME, 'retrieve')
else utils.missingRequiredPermission(SPEC_NAME, 'retrieve')