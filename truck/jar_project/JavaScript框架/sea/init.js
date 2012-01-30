seajs.config({
    base: './base/',
    alias: {
      'app': 'path/to/app/'
    },
    charset: 'utf-8',
    timeout: 20000,
    debug: false
});

//init.js
define(function(require,exports,module){
	
	  var $ = require('./base/jquery');
	  var m1 = require('./module/module1');
	  
	  exports.initPage = function() {
	       $('.content').html(m1.run());
	  }
});
