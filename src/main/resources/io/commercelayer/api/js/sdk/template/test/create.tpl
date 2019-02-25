// @RESOURCE_CAMEL_CAP_PLURAL@.create
if (permissions.@RESOURCE_CAMEL_CAP_PLURAL@ && permissions.@RESOURCE_CAMEL_CAP_PLURAL@.includes('create'))
    it("create", function() {
        return commercelayer.create@RESOURCE_CAMEL_CAP_SINGULAR@(new commercelayer.model.@RESOURCE_CAMEL_CAP_SINGULAR@())
            .then(response => {
                expect(response.get('id')).not.toBeNull();                
            })
    });
else console.log('Test @RESOURCE_CAMEL_CAP_PLURAL@.create skipped due to lack of required permissions on the resource')