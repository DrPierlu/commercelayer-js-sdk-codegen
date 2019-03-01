// @RESOURCE_CAMEL_CAP_PLURAL@.create
if (permissions.@RESOURCE_CAMEL_CAP_PLURAL@ && permissions.@RESOURCE_CAMEL_CAP_PLURAL@.includes('create'))
if (data.@RESOURCE_CAMEL_CAP_PLURAL@ && data.@RESOURCE_CAMEL_CAP_PLURAL@.create)
    it("create", function() {
        return commercelayer.create@RESOURCE_CAMEL_CAP_SINGULAR@(new commercelayer.model.@RESOURCE_CAMEL_CAP_SINGULAR@().setFields(data.@RESOURCE_CAMEL_CAP_PLURAL@.create))
            .then(response => {
                expect(response.get('id')).not.toBeNull();                
            })
    });
else console.log('Test @RESOURCE_CAMEL_CAP_PLURAL@.create skipped: missing required test data')
else console.log('Test @RESOURCE_CAMEL_CAP_PLURAL@.create skipped: missing required resource permission')