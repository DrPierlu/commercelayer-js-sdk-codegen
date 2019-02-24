const commercelayer = require('../index')
const permissions = require('./support/permissions')
const config = require('./support/config')
const data = require('./support/data')


describe("@RESOURCE_CAMEL_CAP_PLURAL@", function() {

    beforeAll(function() {
        commercelayer.initialize(config);
        commercelayer.settings.response_type = 'normalized'
    });
  

    @[create.tpl]@


    @[retrieve.tpl]@


    @[update.tpl]@


    @[list.tpl]@

  });
  