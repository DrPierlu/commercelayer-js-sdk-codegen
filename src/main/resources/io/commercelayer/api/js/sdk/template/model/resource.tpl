// @RESOURCE_TITLE_SINGULAR@ resource
class @RESOURCE_CAMEL_CAP_SINGULAR@ extends Resource {
    static get TYPE() { return '@RESOURCE_TYPE@' }
    constructor(id, fields = {}) {
        super(id, @RESOURCE_CAMEL_CAP_SINGULAR@.TYPE);
        @--> ATTRIBUTES [attribute.tpl]
    }
    @--> RELATIONSHIPS [relationship.tpl]
}