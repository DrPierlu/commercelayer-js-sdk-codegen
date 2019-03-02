const commercelayer = require('../index')
const permissions = require('./support/permissions')
const config = require('./support/config')
const data = require('./support/data')
const utils = require('./support/utils')


const SPEC_NAME = "@RESOURCE_CAMEL_CAP_PLURAL@";


describe(SPEC_NAME, function() {

    beforeAll(function() {
        commercelayer.initialize(config);
        commercelayer.settings.response_type = 'normalized'
    });
  

    @[create.tpl]@


    @[retrieve.tpl]@


    @[update.tpl]@


    @[list.tpl]@

  });
  