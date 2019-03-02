// @RESOURCE_CAMEL_CAP_PLURAL@.list
if (permissions.@RESOURCE_CAMEL_CAP_PLURAL@ && permissions.@RESOURCE_CAMEL_CAP_PLURAL@.includes('list'))
    it("list", function() {
        return commercelayer.list@RESOURCE_CAMEL_CAP_PLURAL@()
            .then(response => {
                expect(response.get(['id']).length).toBeGreaterThan(0)
            })
    });
else utils.missingRequiredPermission(SPEC_NAME, 'list')