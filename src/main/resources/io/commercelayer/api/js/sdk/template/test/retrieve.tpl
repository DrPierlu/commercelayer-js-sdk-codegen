// @RESOURCE_CAMEL_CAP_PLURAL@.retrieve
if (permissions.@RESOURCE_CAMEL_CAP_PLURAL@ && permissions.@RESOURCE_CAMEL_CAP_PLURAL@.includes('retrieve'))
    it("retrieve", function() {
        return commercelayer.retrieve@RESOURCE_CAMEL_CAP_SINGULAR@(data.@RESOURCE_CAMEL_CAP_PLURAL@.retrieve.id)
            .then(response => {
                expect(response.get('id')).toBe(data.@RESOURCE_CAMEL_CAP_PLURAL@.retrieve.id)
            })
    });
else console.log('Test @RESOURCE_CAMEL_CAP_PLURAL@.retrieve skipped due to lack of required permissions on the resource')